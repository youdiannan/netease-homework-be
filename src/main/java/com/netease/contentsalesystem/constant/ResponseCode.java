package com.netease.contentsalesystem.constant;

import lombok.Data;

public enum ResponseCode {

    FAILED(-1, "fail"),
    SUCCESS(0, "success");

    private Integer code;
    private String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
