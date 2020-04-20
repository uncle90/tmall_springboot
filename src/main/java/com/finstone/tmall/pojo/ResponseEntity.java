package com.finstone.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Restful接口包装类
 */
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class ResponseEntity {

    public static int SUCCESS_CODE = 0;

    public static int FAIL_CODE = -1;

    int code;

    String msg;

    Object data;

    public ResponseEntity() {
    }

    public ResponseEntity(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseEntity(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    //成功、失败响应体
    public static ResponseEntity success() {
        return new ResponseEntity(SUCCESS_CODE,null,null);
    }
    public static ResponseEntity success(Object data) {
        return new ResponseEntity(SUCCESS_CODE,"",data);
    }
    public static ResponseEntity fail(String message) {
        return new ResponseEntity(FAIL_CODE, message,null);
    }

}
