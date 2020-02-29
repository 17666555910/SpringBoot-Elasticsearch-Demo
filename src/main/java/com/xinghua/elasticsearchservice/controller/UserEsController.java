package com.xinghua.elasticsearchservice.controller;

import com.xinghua.elasticsearchservice.common.utils.StandardResult;
import com.xinghua.elasticsearchservice.constans.Constants;
import com.xinghua.elasticsearchservice.model.UserEsModel;
import com.xinghua.elasticsearchservice.service.IUserEsService;
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
public class UserEsController {

    @Autowired
    private IUserEsService userService;

    /**
     * 初始化数据
     * 1: 删除原有的索引
     * 2: 创建索引并且初始化映射
     * 3: bulk 批量初始化数据
     *
     * @param userModelListl
     */
    @ApiOperation(value = "初始化数据  yaoguangxing", notes = "初始化数据  yaoguangxing", response = UserEsModel.class)
    @PutMapping("/user/init")
    public StandardResult bulkDelete(@RequestBody List<UserEsModel> userModelListl) {
        try {
            userService.init(userModelListl);
            return StandardResult.ok(Constants.SUCCESS_MSG);
        } catch (Exception e) {
            log.error("异常信息:", e);
            return StandardResult.faild("异常信息", e);
        }
    }
}
