package com.xinghua.elasticsearchservice.common.dto;

/**
 * 异常传输对象
 *
 * @author 姚广星
 * @date 2020/2/24 16:13
 */
public class ExceptionDTO {

    private String message;

    private int code;

    private StackTraceElement[] stackTrace;

    private String exceptionClassName;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public StackTraceElement[] getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(StackTraceElement[] stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getExceptionClassName() {
        return exceptionClassName;
    }

    public void setExceptionClassName(String exceptionClassName) {
        this.exceptionClassName = exceptionClassName;
    }
}
