package com.netease.contentsalesystem.vo;

import com.netease.contentsalesystem.constant.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductVo {
    private Integer id;

    private String name;

    private String productAbstract;

    private String description;

    private String imgUrl;

    private BigDecimal price;

    private ProductStatus productStatus;

    private boolean traded;

    private Integer tradeNum;

    // 当时交易价格
    private Integer oldPrice;
}
