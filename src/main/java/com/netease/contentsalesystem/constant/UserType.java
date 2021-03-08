package com.netease.contentsalesystem.constant;

public enum UserType {

    BUYER(0, "买家"),
    SELLER(1, "卖家");

    private int code;
    private String type;

    UserType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
