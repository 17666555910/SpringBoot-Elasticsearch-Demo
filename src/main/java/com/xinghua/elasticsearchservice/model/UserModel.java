package com.xinghua.elasticsearchservice.model;

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

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "user", type = "user")
public class UserModel {
    @Id
    private String id;

    /**
     * 名称
     */
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String name;

    /**
     * 手机号
     */
    private Integer phone;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 部门名称
     */
    //@Field(type = FieldType.Keyword)
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String deptName;

    /**
     * 居住地址
     */
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String address;
}
