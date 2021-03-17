package com.netease.contentsalesystem.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class Product {
    private Integer id;

    @Size(min = 2, max = 80)
    private String name;

    @Size(min = 2, max = 140)
    private String productAbstract;

    @Size(min = 2, max = 1000)
    private String description;

    @NotNull
    private String imgUrl;

    private BigDecimal price;

    private Integer sold;

    // private ProductStatus productStatus;

    private Integer seller;

    public Integer getSeller() {
        return seller;
    }

    public void setSeller(Integer seller) {
        this.seller = seller;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    // public ProductStatus getProductStatus() {
    //     return productStatus;
    // }
    //
    // public void setProductStatus(ProductStatus productStatus) {
    //     this.productStatus = productStatus;
    // }

    public String getProductAbstract() {
        return productAbstract;
    }

    public void setProductAbstract(String productAbstract) {
        this.productAbstract = productAbstract;
    }
}