package com.xinghua.elasticsearchservice.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @Description 基础实体类
 * @Author 姚广星
 * @Date 2020/2/28 21:18
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityEsModel {
    @Id
    private String id;
}
