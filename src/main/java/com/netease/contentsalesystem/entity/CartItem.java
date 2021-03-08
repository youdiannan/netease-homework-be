package com.netease.contentsalesystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItem {
    private Integer productId;

    private String name;

    private String productAbstract;

    private String description;

    private String imgUrl;

    private BigDecimal price;

    private Integer count;

    private Integer seller;
}
