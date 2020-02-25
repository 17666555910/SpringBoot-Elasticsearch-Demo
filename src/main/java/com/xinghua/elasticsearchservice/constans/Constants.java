package com.xinghua.elasticsearchservice.constans;


/**
 * @author 姚广星
 * @Description 常量类
 * @Date 2020/2/24 16:13
 */
public class Constants {

    /**
     * 系统错误信息
     */
    public static final String SYSTEM_ERROR_MSG = "系统内部错误,请刷新页面重试或联系管理员解决";

    public static final String SUCCESS_MSG = "操作成功";

    public static final String NO_ROLE_ERROR = "当前登陆人未配置相应权限";

    public static final String PARAM_ERROR = "参数错误";

    /***
     * 系统是否过滤HTML参数
     */
    public static final String SYSTEM_PARAMETERS_FLAG = "system.parameters.flag";

    /**
     * 系统数据状态
     */
    public static final String SYSTEM_DATA_ISDELETE_NO = "0";// 可用
    public static final String SYSTEM_DATA_ISDELETE_YES = "1";//删除
    public static final String SYSTEM_DATA_ISDELETE_DISABLED = "2";//禁用

    //是否禁用
    public static final String SYSTEM_DATA_DISABLED = "0";//否
    public static final String SYSTEM_DATA_ABLE = "1";//是

    public static final String SYSTEM_TOKEN = "accessToken";

    /**
     * 数字0和1常量
     */
    public static final String DATA_ZERO = "0";
    public static final String DATA_ONE = "1";
    public static final String DATA_TWO = "2";

    /**
     * 响应码常量
     */
    public static final String SYSTEM_ENCODING = "UTF-8";// 系统编码
    public static final int RESPONSE_CODE_SUCCESS = 200;// 响应成功
    public static final int RESPONSE_CODE_ERROR = 500;// 响应失败
    public static final int RESPONSE_CODE_LOGIN_FAILED = 401;// 登录失败
    public static final int RESPONSE_CODE_TOKEN_FAILURE = 403;// 登录的 token 已过期 或者 不存在
    public static final int RESPONSE_CODE_HANDLE_FAILURE = 600;//工作项已被别人办理
    public static final int RESPONSE_CODE_URL_FAILURE = 700;// 应用无权限访问此接口
    public static final String RESPONSE_MSG_ERROR = "/error"; //异常跳转地址

}
