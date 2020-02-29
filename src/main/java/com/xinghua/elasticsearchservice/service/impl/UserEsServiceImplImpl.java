package com.xinghua.elasticsearchservice.service.impl;

import com.xinghua.elasticsearchservice.common.service.impl.BaseServiceImpl;
import com.xinghua.elasticsearchservice.model.UserEsModel;
import com.xinghua.elasticsearchservice.service.IUserEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description user 实现层
 * @Author 姚广星
 * @Date 2020/2/24 16:57
 **/
@Slf4j
@Service
public class UserEsServiceImplImpl extends BaseServiceImpl<UserEsModel> implements IUserEsService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


}
