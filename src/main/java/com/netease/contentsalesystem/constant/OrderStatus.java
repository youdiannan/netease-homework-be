package com.netease.contentsalesystem.constant;

public enum OrderStatus {

    OPEN(0), // 新建、待付款
    CLOSED(1), // 正常完成，订单关闭
    CANCELLED(2), // 订单取消
    TRANSPORTING(3); // 运输中

    private int status;

    OrderStatus(int status) {
        this.status = status;
    }
}
