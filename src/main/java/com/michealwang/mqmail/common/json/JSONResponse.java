package com.michealwang.mqmail.common.json;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class JSONResponse implements Serializable{

    private static final long serialVersionUID = 7498483649536881777L;

    private Integer status;

    private String msg;

    private Object data;

    public JSONResponse() {
    }

    public JSONResponse(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public static JSONResponse success() {
        return new JSONResponse(ResponseCode.SUCCESS.getCode(), null, null);
    }

    public static JSONResponse success(String msg) {
        return new JSONResponse(ResponseCode.SUCCESS.getCode(), msg, null);
    }

    public static JSONResponse success(Object data) {
        return new JSONResponse(ResponseCode.SUCCESS.getCode(), null, data);
    }

    public static JSONResponse success(String msg, Object data) {
        return new JSONResponse(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static JSONResponse error(String msg) {
        return new JSONResponse(ResponseCode.ERROR.getCode(), msg, null);
    }

    public static JSONResponse error(Object data) {
        return new JSONResponse(ResponseCode.ERROR.getCode(), null, data);
    }

    public static JSONResponse error(String msg, Object data) {
        return new JSONResponse(ResponseCode.ERROR.getCode(), msg, data);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}
