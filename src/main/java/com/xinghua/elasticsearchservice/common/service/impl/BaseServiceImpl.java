package com.xinghua.elasticsearchservice.common.service.impl;

import com.xinghua.elasticsearchservice.common.model.EntityEsModel;
import com.xinghua.elasticsearchservice.common.utils.CommonException;
import com.xinghua.elasticsearchservice.common.service.IBaseService;
import com.xinghua.elasticsearchservice.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 提供公共的, 基础的功能
 * @Author 姚广星
 * @Date 2020/2/27 22:50
 **/
@Slf4j
@Service
public class BaseServiceImpl<T extends EntityEsModel> implements IBaseService<T> {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public Class<T> getEntityClass() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        log.info("The type of T : " + String.valueOf(tClass));
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
    public void createEntityEsIndex() {
        boolean indexExists = elasticsearchTemplate.indexExists(getEntityClass());
        if (indexExists) {
            boolean deleteIndex = elasticsearchTemplate.deleteIndex(getEntityClass());
            if (!deleteIndex) {
                throw new CommonException(" delete entity elasticsearch index Error");
            }
        }
        this.createProductEsIndexAndMappers();
    }

    @Override
    public void batchInsertOrUpdate(List<T> entityModelList) {
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
        log.info("bulkInsert completed.");
    }


    /**
     * 创建索引和映射
     */
    private void createProductEsIndexAndMappers() {
        boolean index = elasticsearchTemplate.createIndex(getEntityClass());
        if (!index) {
            throw new CommonException(" create entity elasticsearch index error");
        }
        boolean putMapping = elasticsearchTemplate.putMapping(getEntityClass());
        if (!putMapping) {
            throw new CommonException(" create entity elasticsearch mappers error");
        }
    }
}
