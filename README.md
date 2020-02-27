# SpringBoot-Elasticsearch-Demo

### 1、项目文档结构： 
本项目是基于SpringBoot 2.1.3 版本进行开发的

    --- com.xinghua.elasticsearchservice
        | --- common      基础依赖
           | --- dto     展示层
           | --- utils   基础工具包
        | --- constans    常量包
        | --- controller  视图层
        | --- model       实体
        | --- service     业务层
           | --- impl    业务实现层  
        | --- utils     工具包  

### 2、集成的插件
    --- swagger API
    --- lombok
    --- elasticsearch
    --- fastjson
    
### 3、已实现的ES功能
    本实例中以product对象为例，请直接查看 ProductController
    
    1：创建索引和映射     --- /product/createProductEsIndex
    2：新增和更新         --- /product/saveOrUpDate
    3: 批量新增和更新     --- /product/bulkSaveOrUpDate