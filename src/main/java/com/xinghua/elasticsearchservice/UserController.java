package com.xinghua.elasticsearchservice;

import com.xinghua.elasticsearchservice.common.utils.StandardResult;
import com.xinghua.elasticsearchservice.constans.Constants;
import com.xinghua.elasticsearchservice.model.UserModel;
import com.xinghua.elasticsearchservice.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 用户 控制器
 * @Author 姚广星
 * @Date 2020/2/24 16:55
 **/
@RestController
@Slf4j
@Api(tags = "用户 控制器")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 创建 User 索引
     *
     * @return
     */
    @ApiOperation(value = "创建 User 索引和映射  yaoguangxing", notes = "创建 User 索引和映射  yaoguangxing", response = UserModel.class)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String"),
//    })
    @GetMapping("/user/createUserEsIndex")
    public StandardResult createUserEsIndex() {
        try {
            userService.createUserEsIndex();
            return StandardResult.ok(Constants.SUCCESS_MSG);
        } catch (Exception e) {
            log.error("异常信息:", e);
            return StandardResult.faild("异常信息", e);
        }
    }
}
