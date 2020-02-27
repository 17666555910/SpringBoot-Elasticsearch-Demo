package com.xinghua.elasticsearchservice.service.impl;

import com.xinghua.elasticsearchservice.model.ProductEsModel;
import com.xinghua.elasticsearchservice.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 提供公共的, 基础的功能
 * @Author 姚广星
 * @Date 2020/2/27 22:50
 **/
@Slf4j
@Service
public class BaseService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 批量更新
     *
     * @param queries 需要更新的 IndexQuery 集合
     * @param clazz   对应的数据表
     */
    public void bulk(List<IndexQuery> queries, Class clazz) {
        int len = 1000;
        //实际运用中一般是设置为 500 或者 1000
        List<List<IndexQuery>> indexQueryLists = DataUtils.splitList(queries, len);
        indexQueryLists.stream().forEach(indexQueryList -> {
            elasticsearchTemplate.bulkIndex(indexQueryList);
        });

        elasticsearchTemplate.refresh(clazz);
        log.info("bulkIndex completed.");
    }
}
