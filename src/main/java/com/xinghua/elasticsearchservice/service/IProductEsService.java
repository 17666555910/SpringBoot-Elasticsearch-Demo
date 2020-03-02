package com.xinghua.elasticsearchservice.service;

import com.xinghua.elasticsearchservice.common.service.IBaseService;
import com.xinghua.elasticsearchservice.model.ProductEsModel;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

/**
 * @Description product接口层
 * @Author 姚广星
 * @Date 2020/2/24 16:57
 **/
public interface IProductEsService extends IBaseService<ProductEsModel> {

}
