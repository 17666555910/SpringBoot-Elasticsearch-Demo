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
 * @Description 用户 控制器
 * @Author 姚广星
 * @Date 2020/2/24 16:55
 **/
@RestController
@Slf4j
@Api(tags = "用户 控制器")
public class ProductController {

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
    @GetMapping("/product/createProductEsIndex")
    public StandardResult createProductEsIndex() {
        try {
            productService.createProductEsIndex();
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
            productService.bulkSaveOrUpDate(productModelList);
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
    @GetMapping("/product/saveOrUpDate")
    public StandardResult saveOrUpDate(@ModelAttribute ProductEsModel productModel) {
        try {
            productService.saveOrUpdate(productModel);
            return StandardResult.ok(Constants.SUCCESS_MSG);
        } catch (Exception e) {
            log.error("异常信息:", e);
            return StandardResult.faild("异常信息", e);
        }
    }
}
