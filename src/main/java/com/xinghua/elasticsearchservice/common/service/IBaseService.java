package com.xinghua.elasticsearchservice.common.service;

import com.xinghua.elasticsearchservice.common.dto.SortDTO;
import com.xinghua.elasticsearchservice.common.model.EntityEsModel;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;

/**
 * @Description 提供公共的，基础的 ElasticSearch 功能
 * @Author 姚广星
 * @Date 2020/2/24 16:57
 **/
public interface IBaseService<T extends EntityEsModel> {

    /**
     * 获取ES索引名称
     *
     * @return
     */
    String getIndexName();

    /**
     * 获取ES索引类型
     *
     * @return
     */
    String getIndexType();


    /**
     * 返回泛型上的Class对象
     *
     * @return
     */
    Class<T> getEntityClass();

    /**
     * 初始化数据
     * 1: 删除原有的索引
     * 2: 创建索引并且初始化映射
     * 3: bulk 批量初始化数据
     *
     * @param entityList
     * @throws Exception
     */
    void init(List<T> entityList);

    /**
     * 创建索引和映射(若原有索引存在则删除重新创建)
     *
     * @return
     */
    Boolean createEntityEsIndex();

    /**
     * 新增或修改
     *
     * @param entityModel
     * @return
     */
    String saveOrUpdate(T entityModel);

    /**
     * 删除entity
     *
     * @param id
     * @return
     */
    String delete(String id);

    /**
     * 删除索引
     *
     * @return
     */
    Boolean deleteIndex();


    /**
     * 批量新增/更新
     *
     * @param entityModelList
     */
    void batchInsertOrUpdate(List<T> entityModelList);

    /**
     * 分页查询
     *
     * @param nativeSearchQueryBuilder 查询条件
     * @param pageNumber               页数
     * @param pageSize                 页码
     * @return
     */
    Page<T> searchPage(NativeSearchQueryBuilder nativeSearchQueryBuilder, int pageNumber, int pageSize);

    /**
     * 分页查询 按照指定字段排序,多个字段按照先后顺序排序
     *
     * @param sortDTOList              排序条件
     * @param nativeSearchQueryBuilder 查询条件
     * @param pageNumber               页数
     * @param pageSize                 页码
     * @return
     */
    Page<T> searchPageBySort(List<SortDTO> sortDTOList, NativeSearchQueryBuilder nativeSearchQueryBuilder, int pageNumber, int pageSize);

    /**
     * 查询操作
     *
     * @param nativeSearchQueryBuilder 查询条件
     * @return
     */
    List<T> searchList(NativeSearchQueryBuilder nativeSearchQueryBuilder);

    /**
     * 查询操作-按照指定字段排序,多个字段按照先后顺序排序
     *
     * @param sortDTOList              排序条件
     * @param nativeSearchQueryBuilder 查询条件
     * @return
     */
    List<T> searchListBySort(List<SortDTO> sortDTOList, NativeSearchQueryBuilder nativeSearchQueryBuilder);

    /**
     * 获取PageRequest对象
     *
     * @param pageNumber 页数
     * @param pageSize   页码
     * @return
     */
    PageRequest getPageRequest(int pageNumber, int pageSize);

    /**
     * 用于分组查询
     *
     * @param nativeSearchQueryBuilder 查询条件
     * @return Aggregations 即 DSL 中的 aggs
     */
    Aggregations query(NativeSearchQueryBuilder nativeSearchQueryBuilder);
}
