package com.xinghua.elasticsearchservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinghua.elasticsearchservice.common.utils.CommonException;
import com.xinghua.elasticsearchservice.model.ProductEsModel;
import com.xinghua.elasticsearchservice.service.IProductEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description product 实现层
 * @Author 姚广星
 * @Date 2020/2/24 16:57
 **/
@Slf4j
@Service
public class ProductEsServiceImpl extends BaseService implements IProductEsService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public void init(List<ProductEsModel> productList) {
        //判断索引是否存在
        boolean indexExists = elasticsearchTemplate.indexExists(ProductEsModel.class);
        if (!indexExists) {
            throw new CommonException("请先执行创建索引的方法: createProductEsIndex()");
        }
        //bulk 批量初始化数据
    }

    /**
     * 创建 Product 索引和映射(若原有索引存在则删除重新创建)
     */
    @Override
    public void createProductEsIndex() {
        boolean indexExists = elasticsearchTemplate.indexExists(ProductEsModel.class);
        if (indexExists) {
            boolean deleteIndex = elasticsearchTemplate.deleteIndex(ProductEsModel.class);
            if (!deleteIndex) {
                throw new CommonException("删除 product 索引失败");
            }
            this.createProductEsIndexAndMappers();
        } else {
            this.createProductEsIndexAndMappers();
        }
    }

    /**
     * 创建索引和映射
     */
    private void createProductEsIndexAndMappers() {
        boolean index = elasticsearchTemplate.createIndex(ProductEsModel.class);
        if (!index) {
            throw new CommonException("创建 product 索引失败");
        }
        boolean putMapping = elasticsearchTemplate.putMapping(ProductEsModel.class);
        if (!putMapping) {
            throw new CommonException("创建 product es映射失败");
        }
    }

    @Override
    public void bulkSaveOrUpDate(List<ProductEsModel> productModelList) {
        //判断索引是否存在 若不存在则创建索引
        if (!elasticsearchTemplate.indexExists(ProductEsModel.class)) {
            elasticsearchTemplate.createIndex(ProductEsModel.class);
        }

        List<IndexQuery> queries = new ArrayList<>();
        for (ProductEsModel productEsModel : productModelList) {
            IndexQuery indexQuery = new IndexQueryBuilder()
                    .withId(productEsModel.getId())
                    .withObject(productEsModel)
                    .build();
            queries.add(indexQuery);
        }
        //批量更新
        super.bulk(queries, ProductEsModel.class);
    }

    @Override
    public void saveOrUpdate(ProductEsModel productModel) {
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(productModel.getId())
                .withObject(productModel)
                .build();
        elasticsearchTemplate.index(indexQuery);
    }

    @Override
    public void delete() {

    }
}
