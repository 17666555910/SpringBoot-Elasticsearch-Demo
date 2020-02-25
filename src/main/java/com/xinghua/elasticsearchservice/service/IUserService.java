package com.xinghua.elasticsearchservice.service;

import com.xinghua.elasticsearchservice.model.UserModel;

import java.util.List;

/**
 * @Description user接口层
 * @Author 姚广星
 * @Date 2020/2/24 16:57
 **/
public interface IUserService {

    /**
     * 初始化数据
     * 1: 删除原有的索引
     * 2: 创建索引并且初始化映射,指定使用ik分词器
     * 3: bulk 批量初始化数据
     *
     * @param userList
     * @throws Exception
     */
    void init(List<UserModel> userList);

    /**
     * 创建 User 索引和映射(若原有索引存在则删除重新创建)
     *
     * @throws Exception
     */
    void createUserEsIndex();

    /**
     * 新增或修改
     */
    void SaveOrUpdate();

    /**
     * 删除
     */
    void delete();


}
