package com.xinghua.elasticsearchservice.model;

import com.xinghua.elasticsearchservice.common.model.EntityEsModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Document(indexName = "user", type = "user")
public class UserEsModel extends EntityEsModel {

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String name;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
     * 居住地址
     */
    @ApiModelProperty(value = "居住地址")
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String address;

    /**
     * 手机
     */
    @ApiModelProperty(value = "手机")
    @Field(type = FieldType.Keyword)
    private String mobile;

    /**
     * 性别   1:男  2:女
     */
    @ApiModelProperty(value = "性别   1:男  2:女")
    private String sex;
}
