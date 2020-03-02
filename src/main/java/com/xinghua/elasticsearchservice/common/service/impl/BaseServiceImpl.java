package com.xinghua.elasticsearchservice.common.service.impl;

import com.xinghua.elasticsearchservice.common.dto.SortDTO;
import com.xinghua.elasticsearchservice.common.model.EntityEsModel;
import com.xinghua.elasticsearchservice.common.utils.CommonException;
import com.xinghua.elasticsearchservice.common.service.IBaseService;
import com.xinghua.elasticsearchservice.utils.DataUtils;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 提供公共的，基础的 ElasticSearch 功能
 * @Author 姚广星
 * @Date 2020/2/27 22:50
 **/
@Slf4j
@Service
public class BaseServiceImpl<T extends EntityEsModel> implements IBaseService<T> {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public String getIndexName() {
        return elasticsearchTemplate.getPersistentEntityFor(getEntityClass()).getIndexName();
    }

    @Override
    public String getIndexType() {
        return elasticsearchTemplate.getPersistentEntityFor(getEntityClass()).getIndexType();
    }

    @Override
    public Class<T> getEntityClass() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        log.info("The type of T : " + tClass);
        return tClass;
    }

    @Override
    public String delete(String id) {
        return elasticsearchTemplate.delete(getEntityClass(), id);
    }

    @Override
    public Boolean deleteIndex() {
        return elasticsearchTemplate.deleteIndex(getEntityClass());
    }

    @Override
    public void init(List<T> entityModelList) {
        //判断索引是否存在
        boolean indexExists = elasticsearchTemplate.indexExists(getEntityClass());
        if (indexExists) {
            //删除索引
            elasticsearchTemplate.deleteIndex(getEntityClass());
        }
        //创建索引和mapper
        this.createEntityEsIndex();
        //bulk 批量初始化数据
        this.batchInsertOrUpdate(entityModelList);
    }

    @Override
    public Boolean createEntityEsIndex() {
        boolean indexExists = elasticsearchTemplate.indexExists(getEntityClass());
        if (indexExists) {
            boolean deleteIndex = elasticsearchTemplate.deleteIndex(getEntityClass());
            if (!deleteIndex) {
                throw new CommonException(" delete entity elasticsearch index Error");
            }
        }
        return this.createProductEsIndexAndMappers();
    }

    @Override
    public void batchInsertOrUpdate(List<T> entityModelList) {
        if (CollectionUtils.isEmpty(entityModelList)) {
            throw new CommonException("entityModelList Can't be empty");
        }
        //判断索引是否存在 若不存在则创建索引和映射
        if (!elasticsearchTemplate.indexExists(getEntityClass())) {
            this.createEntityEsIndex();
        }

        List<IndexQuery> queries = new ArrayList<>();
        for (T entityEsModel : entityModelList) {
            IndexQuery indexQuery = new IndexQueryBuilder()
                    .withId(entityEsModel.getId())
                    .withObject(entityEsModel)
                    .build();
            queries.add(indexQuery);
        }
        //批量插入
        this.bulkInsert(queries);
    }

    @Override
    public String saveOrUpdate(T entityModel) {
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(entityModel.getId())
                .withObject(entityModel)
                .build();
        return elasticsearchTemplate.index(indexQuery);
    }

    /**
     * 批量插入
     *
     * @param queries 需要更新的 IndexQuery 集合
     */
    private void bulkInsert(List<IndexQuery> queries) {
        //实际运用中一般是设置为 500 或者 1000
        int len = 1000;
        List<List<IndexQuery>> indexQueryLists = DataUtils.splitList(queries, len);
        indexQueryLists.stream().forEach(indexQueryList -> {
            elasticsearchTemplate.bulkIndex(indexQueryList);
        });

        elasticsearchTemplate.refresh(getEntityClass());
        log.info("bulkInsert completed of " + queries.size());
    }


    /**
     * 创建索引和映射
     */
    private Boolean createProductEsIndexAndMappers() {
        boolean index = elasticsearchTemplate.createIndex(getEntityClass());
        if (!index) {
            throw new CommonException(" create entity elasticsearch index error");
        }
        boolean putMapping = elasticsearchTemplate.putMapping(getEntityClass());
        if (!putMapping) {
            throw new CommonException(" create entity elasticsearch mappers error");
        }
        return true;
    }

    @Override
    public Page<T> searchPage(NativeSearchQueryBuilder nativeSearchQueryBuilder, int pageNumber, int pageSize) {
        nativeSearchQueryBuilder.withIndices(getIndexName());
        nativeSearchQueryBuilder.withTypes(getIndexType());
        nativeSearchQueryBuilder.withPageable(this.getPageRequest(pageNumber, pageSize));
        return elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), getEntityClass());
    }

    @Override
    public PageRequest getPageRequest(int pageNumber, int pageSize) {
        if (pageNumber <= 0) {
            throw new CommonException("The page number parameter cannot be less than or equal to 0");
        }
        //因为是从0开始计算的 所以此处需要自减1
        pageNumber--;
        return PageRequest.of(pageNumber, pageSize);
    }

    @Override
    public Page<T> searchPageBySort(List<SortDTO> sortDTOList, NativeSearchQueryBuilder nativeSearchQueryBuilder,
                                    int pageNumber, int pageSize) {
        this.joinSortBuilders(sortDTOList, nativeSearchQueryBuilder);
        return this.searchPage(nativeSearchQueryBuilder, pageNumber, pageSize);
    }

    @Override
    public List<T> searchList(NativeSearchQueryBuilder nativeSearchQueryBuilder) {
        nativeSearchQueryBuilder.withIndices(getIndexName());
        nativeSearchQueryBuilder.withTypes(getIndexType());
        return elasticsearchTemplate.queryForList(nativeSearchQueryBuilder.build(), getEntityClass());
    }

    @Override
    public List<T> searchListBySort(List<SortDTO> sortDTOList, NativeSearchQueryBuilder nativeSearchQueryBuilder) {
        this.joinSortBuilders(sortDTOList, nativeSearchQueryBuilder);
        return this.searchList(nativeSearchQueryBuilder);
    }

    /**
     * 拼接多个SortBuilders排序条件
     *
     * @param sortDTOList              排序条件集合
     * @param nativeSearchQueryBuilder 查询条件
     */
    private void joinSortBuilders(List<SortDTO> sortDTOList, NativeSearchQueryBuilder nativeSearchQueryBuilder) {
        for (SortDTO sortDTO : sortDTOList) {
            nativeSearchQueryBuilder.withSort(
                    SortBuilders.fieldSort(sortDTO.getKey()).order(sortDTO.getIsASC() == true ? SortOrder.ASC : SortOrder.DESC)
            );
        }
    }

    @Override
    public Aggregations query(NativeSearchQueryBuilder nativeSearchQueryBuilder) {
        nativeSearchQueryBuilder.withIndices(getIndexName());
        nativeSearchQueryBuilder.withTypes(getIndexType());
        return elasticsearchTemplate.query(nativeSearchQueryBuilder.build(), searchResponse -> searchResponse.getAggregations());
    }
}

