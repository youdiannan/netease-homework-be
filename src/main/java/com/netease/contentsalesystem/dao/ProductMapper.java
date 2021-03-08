package com.netease.contentsalesystem.dao;

import com.netease.contentsalesystem.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteById(Integer id);

    int insert(Product record);

    Product selectById(Integer id);

    int update(Product record);

    List<Product> findAll();

    List<Product> findAllByProductIdList(@Param("productIdList") List<Integer> productIdList);
}