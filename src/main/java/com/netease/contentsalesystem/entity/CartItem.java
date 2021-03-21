package com.netease.contentsalesystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItem {
    private Integer productId;

    @Size(min = 2, max = 80)
    private String name;

    @Size(min = 2, max = 140)
    private String productAbstract;

    @Size(min = 2, max = 1000)
    private String description;

    private String imgUrl;

    @Min(0)
    private BigDecimal price;

    @Min(1)
    private Integer count;

    private Integer seller;
}
