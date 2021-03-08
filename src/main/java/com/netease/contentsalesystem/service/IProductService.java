package com.netease.contentsalesystem.service;

import com.netease.contentsalesystem.entity.Product;
import com.netease.contentsalesystem.vo.CommonResponse;
import com.netease.contentsalesystem.vo.ProductVo;

import java.util.List;

public interface IProductService {

    List<ProductVo> list(Integer userId);

    ProductVo detail(Integer userId, Integer productId);

    CommonResponse edit(Integer userId, Product product);

    List<Product> findAllByProductIdList(List<Integer> productIds);

    CommonResponse delete(Integer productId);
}
