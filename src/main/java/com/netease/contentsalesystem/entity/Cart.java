package com.netease.contentsalesystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cart {

    private Integer userId;
    private List<CartItem> cartItems;
    private BigDecimal totalPrice;

}
