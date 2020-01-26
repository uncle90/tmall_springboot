package com.finstone.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Restful接口包装类
 */
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class ResponseEntity {

    /**
     * code >=0 成功, <0 失败;
     */
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
}
