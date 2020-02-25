package com.xinghua.elasticsearchservice.common.utils;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xinghua.elasticsearchservice.common.dto.ExceptionDTO;
import com.xinghua.elasticsearchservice.constans.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * @author 姚广星
 * @Description 自定义响应结构
 * @Date 2020/2/24 16:13
 */
@ApiModel(value = "StandardResult", description = "标准自定义响应结构")
public class StandardResult {


    /**
     * 响应业务状态
     */
    @ApiModelProperty(value = "响应业务状态")
    private boolean state;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应消息")
    private String msg;

    /**
     * 响应中的数据
     */
    @ApiModelProperty(value = "响应中的数据")
    private Object data;

    /**
     * 响应状态码    200 响应成功    201缺少参数    0响应失败
     */
    @ApiModelProperty(value = "响应状态码  200 响应成功   500响应失败")
    private int code;

    /**
     * 错误栈信息
     */
    @JsonIgnore
    private String errorStackInfo;

    public StandardResult(boolean state, String msg, Object data, int code, String errorStackInfo) {
        super();
        this.state = state;
        this.msg = msg;
        this.data = data;
        this.code = code;
        this.errorStackInfo = errorStackInfo;
    }

    public StandardResult(ResultCode resultCode, Object data) {
        this.code = resultCode.code();
        this.state = resultCode.state();
        this.msg = resultCode.message();
        this.data = data;
    }

    public StandardResult(ExceptionDTO exceptionDTO) {
        this.code = exceptionDTO.getCode();
        this.state = false;
        this.msg = exceptionDTO.getMessage();
    }


    public StandardResult() {
        super();
    }


    public static StandardResult resultCode(ResultCode resultCode) {
        return new StandardResult(resultCode, null);
    }

    public static StandardResult resultCode(ResultCode resultCode, Object object) {
        return new StandardResult(resultCode, object);
    }

    public static StandardResult exceptionResult(ExceptionDTO exceptionDTO) {
        return new StandardResult(exceptionDTO);
    }

    public static StandardResult ok(String msg, Object data) {
        if (data == null) {
            data = new ArrayList<Object>();
        }

        if (msg == null) {
            msg = "";
        }

        return new StandardResult(true, msg, data, Constants.RESPONSE_CODE_SUCCESS, null);
    }


    public static StandardResult ok(String msg) {

        return StandardResult.ok(msg, null);
    }

    public static StandardResult ok(Object data) {

        return StandardResult.ok(null, data);
    }


    public static StandardResult ok() {
        return StandardResult.ok(null);
    }

    public static StandardResult faild(String msg) {

        if (msg == null) {
            msg = "";
        }

        return new StandardResult(false, msg, new ArrayList<Object>(), Constants.RESPONSE_CODE_ERROR, msg);
    }


    public static StandardResult faild(String msg, Exception ex) {

        if (msg == null) {
            msg = "";
        }
        if (ex == null) {
            return new StandardResult(false, msg, new ArrayList<Object>(), Constants.RESPONSE_CODE_ERROR, msg);
        } else {
            return new StandardResult(false, msg, new ArrayList<Object>(), Constants.RESPONSE_CODE_ERROR, printStackTraceToString(ex));
        }
    }

    public static StandardResult faild(Exception ex) {
        if (ex instanceof CommonException) {
            return faild(ex.getMessage(), ex);
        } else if (ex.getCause() instanceof CommonException) {
            return faild(ex.getCause().getMessage(), ex);
        } else if (ex.getCause() != null && ex.getCause().getCause() instanceof CommonException) {
            return faild(ex.getCause().getCause().getMessage(), ex);
        }
        return faild(Constants.SYSTEM_ERROR_MSG, ex);
    }

    /**
     * 验证不通过
     *
     * @param msg
     * @return
     */
    public static StandardResult faildCheck(String msg) {
        if (msg == null) {
            msg = "";
        }
        return new StandardResult(false, msg, new ArrayList<Object>(), Constants.RESPONSE_CODE_SUCCESS, msg);
    }

    public static StandardResult faildCheck(String msg, Object data) {
        if (msg == null) {
            msg = "";
        }
        if (data == null) {
            data = new ArrayList<Object>();
        }
        return new StandardResult(false, msg, data, Constants.RESPONSE_CODE_SUCCESS, null);
    }


    public static StandardResult loginFailed(String msg) {
        return new StandardResult(false, msg, new ArrayList<Object>(), Constants.RESPONSE_CODE_LOGIN_FAILED, msg);
    }

    public static StandardResult tokenFailure() {
        return new StandardResult(false, "token已过期或不存在", new ArrayList<Object>(), Constants.RESPONSE_CODE_TOKEN_FAILURE, "token已过期或不存在");
    }

    public static StandardResult urlFailure() {
        return new StandardResult(false, "应用无权限访问此接口", new ArrayList<Object>(), Constants.RESPONSE_CODE_URL_FAILURE, "应用无权限访问此接口");
    }

    public static StandardResult requestFailure() {
        return new StandardResult(false, Constants.SYSTEM_ERROR_MSG, new ArrayList<Object>(), Constants.RESPONSE_CODE_ERROR, "请求失败异常");
    }

    public static StandardResult handleFailure() {
        return new StandardResult(false, "该工作项已经被办理，请刷新页面", new ArrayList<Object>(), Constants.RESPONSE_CODE_HANDLE_FAILURE, "该工作项已经被办理，请刷新页面");
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorStackInfo() {
        return errorStackInfo;
    }


    public void setErrorStackInfo(String errorStackInfo) {
        this.errorStackInfo = errorStackInfo;
    }

    public static String printStackTraceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }

    /**
     * 校验后台返回StandardResult是否正常返回数据
     *
     * @param standardResult
     * @return boolean
     */
    public static boolean standardResultBoolean(StandardResult standardResult) {
        boolean flag = false;
        if (standardResult != null && standardResult.getCode() == 200 && standardResult.getState()
                && standardResult.getData() != null) {
            flag = true;
        }
        return flag;
    }

}

