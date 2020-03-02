# SpringBoot-Elasticsearch-Demo

### 题外话：
本实例为博主原创，属于简单易上手并且能够拿来就用的SpringBoot ES 项目，全文使用的是ElasticsearchTemplate进行开发。
本实例涵盖ES中的各类操作，如索引操作、CRUD操作、批处理、结果排序、分页查询、检索查询、关键字查询、高亮显示、逻辑查询、过滤查询、分组查询等等。并且已经过生产环境验证，各位可放心使用。如有不对之处欢迎在博客中留言交流。谢谢！！！

**欢迎各位大神添加微信公众号：“愿为最亮星”，我们将在这一起探讨Java技术。**


### 1、CSDN 专栏:《Elasticsearch 入门和项目实战》 
#### 博客路径： https://blog.csdn.net/a767815662/category_9190277.html
    |--- 第一讲：简介以及安装
    |--- 第二讲：kibana 安装以及ES 的概念名词
    |--- TODO: 持续更新中~~~


### 2、项目介绍： 
本项目是基于SpringBoot 2.1.3 版本进行开发的,采用的是 Spring Data Elasticsearch。

    ElasticsearchTemplate：框架封装的用于便捷操作Elasticsearch的模板类 
    NativeSearchQueryBuilder：用于生成查询条件的构建器，需要去封装各种查询条件 
        QueryBuilder：该接口表示一个查询条件，其对象可以通过QueryBuilders工具类中的方法快速生成各种条件 
        boolQuery()：生成bool条件，相当于 "bool": { } 
        matchQuery()：生成match条件，相当于 "match": { } 
        rangeQuery()：生成range条件，相当于 "range": { } 
        AbstractAggregationBuilder：用于生成分组查询的构建器，其对象通过AggregationBuilders工具类生成 
        Pageable：表示分页参数，对象通过PageRequest.of(页数, 容量)获取 
        SortBuilder：排序构建器，对象通过SortBuilders.fieldSort(字段).order(规则)获取

### 3、项目结构介绍：
    --- com.xinghua.elasticsearchservice
        | --- common       基础依赖
           | --- dto       展示层
           | --- model     基础实体类
           | --- utils     基础工具包
           | --- service   基础业务包（通用CRUD，批量操作接口）
              | --- impl   基础业务实现层（通用CRUD，批量操作实现）
        | --- constans     常量包
        | --- controller   视图层
        | --- model        实体
        | --- service      业务层
           | --- impl      业务实现层  
        | --- utils        工具包  

### 4、项目集成的插件
    --- swagger API
    --- lombok
    --- elasticsearch
    --- fastjson
    
### 5、已实现的ES功能
    --- IBaseService
        | --- getIndexName()：               获取ES索引名称
        | --- getIndexType()：               获取ES索引类型
        | --- getEntityClass()：             返回泛型上的Class对象
        | --- init(List<T> entityList)：     初始化数据
        | --- createEntityEsIndex()：        创建索引和映射
        | --- saveOrUpdate(T entityModel)：  新增或修改
        | --- delete(String id)：            删除entity
        | --- deleteIndex()：                删除索引
        | --- batchInsertOrUpdate(List<T> entityModelList)：批量新增/更新
        
    以下是高级查询相关的接口
        | --- getPageRequest(int pageNumber, int pageSize)：获取PageRequest对象
        | --- searchPage(NativeSearchQueryBuilder nativeSearchQueryBuilder, int pageNumber, int pageSize)：分页查询
        | --- searchPageBySort(List<SortDTO> sortDTOList, NativeSearchQueryBuilder nativeSearchQueryBuilder, int pageNumber, int pageSize)：分页查询 按照指定字段排序,多个字段按照先后顺序排序
        | --- searchList(NativeSearchQueryBuilder nativeSearchQueryBuilder)：查询操作
        | --- searchListBySort(List<SortDTO> sortDTOList, NativeSearchQueryBuilder nativeSearchQueryBuilder)：查询操作-按照指定字段排序,多个字段按照先后顺序排序
        | --- query(NativeSearchQueryBuilder nativeSearchQueryBuilder)：用于分组查询
    
### 6、实例测试类
    --- BaseProductEsTest：基础功能测试类
    --- ProductEsTest：各种高级查询功能测试类
