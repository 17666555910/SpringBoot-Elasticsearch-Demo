package com.xinghua.elasticsearchservice.service.impl;

import com.xinghua.elasticsearchservice.common.utils.CommonException;
import com.xinghua.elasticsearchservice.model.UserModel;
import com.xinghua.elasticsearchservice.service.IUserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description user 实现层
 * @Author 姚广星
 * @Date 2020/2/24 16:57
 **/
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public void init(List<UserModel> userList) {
        //判断索引是否存在
        boolean indexExists = elasticsearchTemplate.indexExists(UserModel.class);
        if (!indexExists) {
            throw new CommonException("请先执行创建索引的方法: createUserEsIndex()");
        }
        //bulk 批量初始化数据
    }

    /**
     * 创建 User 索引和映射(若原有索引存在则删除重新创建)
     */
    @Override
    public void createUserEsIndex() {
        boolean indexExists = elasticsearchTemplate.indexExists(UserModel.class);
        if (indexExists) {
            boolean deleteIndex = elasticsearchTemplate.deleteIndex(UserModel.class);
            if (!deleteIndex) {
                throw new CommonException("删除 user 索引失败");
            }
            this.createUserEsIndexAndMappers();
        } else {
            this.createUserEsIndexAndMappers();
        }
    }

    /**
     * 创建索引和映射
     */
    private void createUserEsIndexAndMappers() {
        boolean index = elasticsearchTemplate.createIndex(UserModel.class);
        if (!index) {
            throw new CommonException("创建 user 索引失败");
        }
        boolean putMapping = elasticsearchTemplate.putMapping(UserModel.class);
        if (!putMapping) {
            throw new CommonException("创建 user es映射失败");
        }
    }

    @Override
    public void SaveOrUpdate() {

    }

    @Override
    public void delete() {

    }
}
