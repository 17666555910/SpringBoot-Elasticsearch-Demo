package com.xinghua.elasticsearchservice.common.utils;

/**
 * 公共的返回码
 *    返回码code：
 *      成功：10000
 *      失败：10001
 *      未登录：10002
 *      未授权：10003
 *      抛出异常：99999
 */
public enum ResultCode {

    SUCCESS(true,200,"操作成功！", true),

    FAIL(false,201,"操作失败", false),

    UNAUTHENTICATED(false,301,"您还未登录", false),

    UNAUTHORISE(false,302,"权限不足", false),

    MOBILEORPASSWORDERROR(false,303,"用户名或密码错误", false),

    ACCOUNT_DISABLED(false,304,"登录失败次数过多，账号被禁用", false),

    VERIFICATION_CODE_ERROR(false, 305, "验证码错误", false),

    SESSION_EXPIRATION(false, 306, "登录过期，请重新登录", false),

    SERVER_ERROR(false,500,"抱歉，系统繁忙，请稍后重试！", false);


    //操作是否成功
    private boolean success;
    //操作代码
    private int code;
    //提示信息
    private String message;
    // 响应业务状态
    private boolean state;

    ResultCode(boolean success, int code, String message, boolean state){
        this.success = success;
        this.code = code;
        this.message = message;
        this.state = state;
    }

    public boolean success() {
        return success;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public boolean state() {
        return state;
    }

}
