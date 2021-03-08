package com.netease.contentsalesystem.constant;

public enum ProductStatus {

    // 库存不足在代码中判断。若库存不足时更改数据库可能压力较大
    ON_SALE(0), // 在售
    OFF_SALE(1); // 下架

    private int status;

    ProductStatus(int status) {
        this.status = status;
    }
}
