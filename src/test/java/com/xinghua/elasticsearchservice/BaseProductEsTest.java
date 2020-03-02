package com.xinghua.elasticsearchservice;

import com.xinghua.elasticsearchservice.model.ProductEsModel;
import com.xinghua.elasticsearchservice.service.IProductEsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 基础功能测试类
 * @Author 姚广星
 * @Date 2020/3/1 12:55
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
class BaseProductEsTest {

    @Autowired
    private IProductEsService productEsService;

    @BeforeEach
    void setUp() {
        //System.out.println("执行初始化");
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * 获取索引名称
     */
    @Test
    void getIndexName() {
        System.out.println(productEsService.getIndexName());
    }

    /**
     * 获取类型
     */
    @Test
    void getIndexType() {
        System.out.println(productEsService.getIndexType());
    }

    /**
     * 删除索引
     */
    @Test
    void deleteIndex() {
        Boolean aBoolean = productEsService.deleteIndex();
        System.out.println("aBoolean = " + aBoolean);
    }

    /**
     * 返回泛型上的Class对象
     */
    @Test
    void getEntityClass() {
        System.out.println(productEsService.getEntityClass());
    }

    /**
     * 创建索引和映射(若原有索引存在则删除重新创建)
     */
    @Test
    void createEntityEsIndex() {
        System.out.println(productEsService.createEntityEsIndex());
    }

    /**
     * 初始化数据
     * 1: 删除原有的索引
     * 2: 创建索引并且初始化映射
     * 3: bulk 批量初始化数据
     */
    @Test
    void init() {
        List<ProductEsModel> productEsModels = new ArrayList<>();
        ProductEsModel p1 = new ProductEsModel("1", "小米2青春版手机", 1799.99,
                "广东省深圳市", "3", "小米", "小米2青春版手机");

        ProductEsModel p2 = new ProductEsModel("2", "小米2经典版手机", 1899.99,
                "广东省深圳市", "3", "小米", "小米2经典版手机");

        ProductEsModel p3 = new ProductEsModel("3", "小米note手机", 2799.99,
                "广东省深圳市", "3", "小米", "小米note手机");

        ProductEsModel p4 = new ProductEsModel("4", "华为手机1", 1999.99,
                "湖南省", "1", "华为", "华为手机1");

        ProductEsModel p5 = new ProductEsModel("5", "华为手机2", 2888.88,
                "湖南省", "1", "华为", "华为手机2");

        ProductEsModel p6 = new ProductEsModel("6", "vivo 10", 999.99,
                "广东省广州市", "2", "vivo", "vivo 10");

        ProductEsModel p7 = new ProductEsModel("7", "vivo 20", 1899.99,
                "广东省广州市", "2", "vivo", "vivo 20");

        productEsModels.add(p1);
        productEsModels.add(p2);
        productEsModels.add(p3);
        productEsModels.add(p4);
        productEsModels.add(p5);
        productEsModels.add(p6);
        productEsModels.add(p7);
        productEsService.init(productEsModels);
    }

    /**
     * 新增或修改
     */
    @Test
    void saveOrUpdate() {
        System.out.println(productEsService.saveOrUpdate(new ProductEsModel("8", "9", 1899.99,
                "广东省广州市", "2", "vivo", "80")));
    }

    /**
     * 删除
     */
    @Test
    void delete() {
        System.out.println(productEsService.delete("8"));
    }

    /**
     * 获取PageRequest对象
     */
    @Test
    void getPageRequest() {
        System.out.println(productEsService.getPageRequest(1, 10));
    }

    /**
     * 批量新增/更新
     */
    @Test
    void batchInsertOrUpdate() {
        List<ProductEsModel> productEsModels = new ArrayList<>();
        ProductEsModel p1 = new ProductEsModel("8", "罗技鼠标", 990.00,
                "广东省湛江市", "4", "罗技", "罗技鼠标");

        ProductEsModel p2 = new ProductEsModel("9", "双飞燕鼠标", 99.99,
                "广东省湛江市", "5", "双飞燕", "双飞燕鼠标");
        productEsModels.add(p1);
        productEsModels.add(p2);
        productEsService.batchInsertOrUpdate(productEsModels);
    }

}