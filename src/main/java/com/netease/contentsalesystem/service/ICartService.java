package com.netease.contentsalesystem.service;

import com.netease.contentsalesystem.entity.Cart;
import com.netease.contentsalesystem.entity.CartItem;
import com.netease.contentsalesystem.vo.CommonResponse;

public interface ICartService {

    Cart list(Integer userId);

    CommonResponse edit(Integer userId, CartItem cartItem);

    CommonResponse checkout(Integer userId);

    CommonResponse delete(Integer userId, Integer productId);
}
