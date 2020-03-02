package com.xinghua.elasticsearchservice;

import com.xinghua.elasticsearchservice.common.dto.SortDTO;
import com.xinghua.elasticsearchservice.model.ProductEsModel;
import com.xinghua.elasticsearchservice.service.IProductEsService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.DoubleTerms;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.invocation.MatchersBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description 各种高级查询功能测试类
 * @Author 姚广星
 * @Date 2020/3/1 12:55
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
class ProductEsTest {

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
     * 分页查询-按照价格降序排列，显示第2页，每页显示3个   （分页+排序）
     */
    @Test
    void query1() {
        List<SortDTO> sortDTOList = new ArrayList<>();
        sortDTOList.add(new SortDTO("price", false));
        Page<ProductEsModel> productEsModels = productEsService.searchPageBySort(sortDTOList, new NativeSearchQueryBuilder(), 2, 3);
        productEsModels.forEach(System.out::println);
    }

    /**
     * 分页查询-按照品牌升序然后价格降序排列，显示第2页，每页显示3个     （分页+多条件排序）
     */
    @Test
    void query2() {
        List<SortDTO> sortDTOList = new ArrayList<>();
        sortDTOList.add(new SortDTO("brandId", true));
        sortDTOList.add(new SortDTO("price", false));
        Page<ProductEsModel> productEsModels = productEsService.searchPageBySort(sortDTOList, new NativeSearchQueryBuilder(), 2, 3);
        productEsModels.forEach(System.out::println);
    }

    /**
     * 查询商品标题中符合"小米 手机"的字样的商品   （match 查询）
     */
    @Test
    void query3() {
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withQuery(
                QueryBuilders.matchQuery("title", "小米 手机")
        );
        List<ProductEsModel> productEsModels = productEsService.searchList(searchQueryBuilder);
        productEsModels.forEach(System.out::println);
    }

    /**
     * 查询所有商品标题中带有 鼠标 或者 价格为 1899.99,并且按照价格降序排序     （逻辑查询   must / should / must_not，相当于and / or / not）
     */
    @Test
    void query4() {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(
                QueryBuilders.boolQuery()
                        .should(QueryBuilders.termQuery("title", "鼠标"))
                        .should(QueryBuilders.termQuery("price", 1899.99))
        );
        List<SortDTO> sortDTOList = new ArrayList<>();
        sortDTOList.add(new SortDTO("price", false));
        List<ProductEsModel> productEsModels = productEsService.searchListBySort(sortDTOList, nativeSearchQueryBuilder);
        productEsModels.forEach(System.out::println);
    }

    /**
     * 查询商品标题或产地中符合"手机,湛江"的字样的商品    (关键字查询)
     */
    @Test
    void query5() {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(
                QueryBuilders.multiMatchQuery("手机,湛江", "origin", "title")
        );
        List<ProductEsModel> productEsModels = productEsService.searchList(nativeSearchQueryBuilder);
        productEsModels.forEach(System.out::println);
    }

    /**
     * 查询商品标题中符合"手机"的商品,并且价格在 1900~2800之间
     */
    @Test
    void query6() {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("title", "手机"))
                        .must(QueryBuilders.rangeQuery("price").gte(1900).lte(2800))
        );
        List<ProductEsModel> productEsModels = productEsService.searchList(nativeSearchQueryBuilder);
        productEsModels.forEach(System.out::println);
    }

    /**
     * 分组查询（桶聚合）    按照品牌分组，统计各品牌的平均价格
     */
    @Test
    void query7() {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withIndices("product").withTypes("product");
        nativeSearchQueryBuilder.addAggregation(
                AggregationBuilders.terms("groupByBrandId").field("brandId")
                        .subAggregation(
                                AggregationBuilders.avg("avgPrice").field("price")
                        )
        );
        Aggregations group = productEsService.query(nativeSearchQueryBuilder);
        StringTerms groupByBrandId = group.get("groupByBrandId");
        List<StringTerms.Bucket> buckets = groupByBrandId.getBuckets();
        for (StringTerms.Bucket b : buckets) {
            InternalAvg avgPrice = b.getAggregations().get("avgPrice");
            System.out.println(avgPrice.getValue());
        }
    }

    @Test
    void query8() {

    }
}