package com.michealwang.mqmail.config.exception;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/17 11:32
 * @Description 业务逻辑异常处理
 */
public class ServiceException extends RuntimeException{

    private String code;

    private String msg;


    public ServiceException() {
    }

    public ServiceException(String msg) {
        this.msg = msg;
    }

    public ServiceException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
