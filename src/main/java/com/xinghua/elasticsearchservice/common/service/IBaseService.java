package com.xinghua.elasticsearchservice.common.service;

import com.xinghua.elasticsearchservice.common.model.EntityEsModel;

import java.util.List;

/**
 * @Description entity接口层
 * @Author 姚广星
 * @Date 2020/2/24 16:57
 **/
public interface IBaseService<T extends EntityEsModel> {

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
     * @throws Exception
     */
    void createEntityEsIndex();

    /**
     * 新增或修改
     *
     * @param entityModel
     */
    String saveOrUpdate(T entityModel);

    /**
     * 删除
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

}
