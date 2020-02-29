package com.xinghua.elasticsearchservice.controller;

import com.xinghua.elasticsearchservice.common.utils.StandardResult;
import com.xinghua.elasticsearchservice.constans.Constants;
import com.xinghua.elasticsearchservice.model.ProductEsModel;
import com.xinghua.elasticsearchservice.service.IProductEsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 商品 控制器
 * @Author 姚广星
 * @Date 2020/2/24 16:55
 **/
@RestController
@Slf4j
@Api(tags = "商品 控制器")
public class ProductEsController {

    @Autowired
    private IProductEsService productService;

    /**
     * 创建 Product 索引和映
     *
     * @return
     */
    @ApiOperation(value = "创建 Product 索引和映射  yaoguangxing", notes = "创建 Product 索引和映射  yaoguangxing", response = ProductEsModel.class)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String"),
//    })
    @PostMapping("/product/createProductEsIndex")
    public StandardResult createProductEsIndex() {
        try {
            productService.createEntityEsIndex();
            return StandardResult.ok(Constants.SUCCESS_MSG);
        } catch (Exception e) {
            log.error("异常信息:", e);
            return StandardResult.faild("异常信息", e);
        }
    }

    /**
     * 批量新增和更新
     *
     * @return
     */
    @ApiOperation(value = "批量新增和更新  yaoguangxing", notes = "批量新增和更新  yaoguangxing", response = ProductEsModel.class)
    @PutMapping("/product/bulkSaveOrUpDate")
    public StandardResult bulkSaveOrUpDate(@RequestBody List<ProductEsModel> productModelList) {
        try {
            productService.batchInsertOrUpdate(productModelList);
            return StandardResult.ok(Constants.SUCCESS_MSG);
        } catch (Exception e) {
            log.error("异常信息:", e);
            return StandardResult.faild("异常信息", e);
        }
    }

    /**
     * 新增和更新
     *
     * @param productModel
     * @return
     */
    @ApiOperation(value = "新增和更新  yaoguangxing", notes = "新增和更新  yaoguangxing", response = ProductEsModel.class)
    @PostMapping("/product/saveOrUpDate")
    public StandardResult saveOrUpDate(@ModelAttribute ProductEsModel productModel) {
        try {
            return StandardResult.ok(Constants.SUCCESS_MSG, productService.saveOrUpdate(productModel));
        } catch (Exception e) {
            log.error("异常信息:", e);
            return StandardResult.faild("异常信息", e);
        }
    }

    /**
     * 删除商品
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除商品  yaoguangxing", notes = "删除商品  yaoguangxing", response = ProductEsModel.class)
    @PostMapping("/product/delete")
    public StandardResult delete(String id) {
        try {
            return StandardResult.ok(Constants.SUCCESS_MSG, productService.delete(id));
        } catch (Exception e) {
            log.error("异常信息:", e);
            return StandardResult.faild("异常信息", e);
        }
    }

    /**
     * 删除商品索引
     *
     * @return
     */
    @ApiOperation(value = "删除商品索引  yaoguangxing", notes = "删除商品索引  yaoguangxing", response = ProductEsModel.class)
    @PostMapping("/product/deleteProductIndex")
    public StandardResult deleteProductIndex() {
        try {
            return StandardResult.ok(Constants.SUCCESS_MSG, productService.deleteIndex());
        } catch (Exception e) {
            log.error("异常信息:", e);
            return StandardResult.faild("异常信息", e);
        }
    }

    /**
     * 初始化数据
     * 1: 删除原有的索引
     * 2: 创建索引并且初始化映射
     * 3: bulk 批量初始化数据
     *
     * @param productModelListl
     */
    @ApiOperation(value = "初始化数据  yaoguangxing", notes = "初始化数据  yaoguangxing", response = ProductEsModel.class)
    @PutMapping("/product/init")
    public StandardResult bulkDelete(@RequestBody List<ProductEsModel> productModelListl) {
        try {
            productService.init(productModelListl);
            return StandardResult.ok(Constants.SUCCESS_MSG);
        } catch (Exception e) {
            log.error("异常信息:", e);
            return StandardResult.faild("异常信息", e);
        }
    }
}
