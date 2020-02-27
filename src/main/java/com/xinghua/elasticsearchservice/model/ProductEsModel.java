package com.xinghua.elasticsearchservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Description ES 实体类
 * Document：配置操作哪个索引下的哪个类型
 * Id：标记文档ID字段
 * Field：配置映射信息，如：分词器
 * @Author 姚广星
 * @Date 2020/2/24 16:13
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "product", type = "product")
public class ProductEsModel {
    @Id
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String title;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private Double price;

    /**
     * 产地
     */
    @ApiModelProperty(value = "产地")
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String origin;

    /**
     * 品牌ID
     */
    @ApiModelProperty(value = "品牌ID")
    @Field(type = FieldType.Keyword)
    private String brandId;

    /**
     * 品牌名称
     */
    @ApiModelProperty(value = "品牌名称")
    @Field(type = FieldType.Keyword)
    private String brandName;

    /**
     * 关键字
     */
    @ApiModelProperty(value = "关键字")
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String keyword;
}
