package com.netease.contentsalesystem.service.impl;

import com.netease.contentsalesystem.constant.ResponseCode;
import com.netease.contentsalesystem.entity.Cart;
import com.netease.contentsalesystem.entity.CartItem;
import com.netease.contentsalesystem.entity.Product;
import com.netease.contentsalesystem.service.IAccountService;
import com.netease.contentsalesystem.service.ICartService;
import com.netease.contentsalesystem.service.IProductService;
import com.netease.contentsalesystem.vo.CommonResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("cartService")
public class CartServiceImpl implements ICartService {

    private static final String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IProductService productService;

    @Autowired
    private IAccountService accountService;

    @Override
    public Cart list(Integer userId) {
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, userId);
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        // 获取用户对应的购物车所有商品数据
        Map<String, String> entries = hashOperations.entries(redisKey);
        if (entries.isEmpty()) return new Cart();

        // 根据productId获取商品数据
        List<Integer> productIds = entries.keySet().stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Product> productList = productService.findAllByProductIdList(productIds);
        List<CartItem> cartItemList = new ArrayList<>();
        BigDecimal totalPrice = new BigDecimal("0.00");
        for (Product product: productList) {
            // 构建CartItem
            CartItem cartItem = new CartItem();
            BeanUtils.copyProperties(product, cartItem);
            cartItem.setProductId(product.getId());
            cartItem.setCount(Integer.parseInt(entries.get(String.valueOf(product.getId()))));
            cartItemList.add(cartItem);
            totalPrice = totalPrice.add(
                    product.getPrice().multiply(
                    BigDecimal.valueOf(cartItem.getCount()
                    )));
        }
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setCartItems(cartItemList);
        cart.setTotalPrice(totalPrice);
        return cart;
    }

    @Override
    public CommonResponse edit(Integer userId, CartItem cartItem) {
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, userId);
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        /**
         * Redis存储形式:
         * {
         *     cart_#{userId}: {
         *         #{productId} : #{productCount}
         *     }
         * }
         */
        hashOperations.put(redisKey, String.valueOf(cartItem.getProductId()), String.valueOf(cartItem.getCount()));
        return new CommonResponse(ResponseCode.SUCCESS.getCode(), "添加成功");
    }

    @Override
    public CommonResponse checkout(Integer userId) {
        CommonResponse response = accountService.add(userId, list(userId).getCartItems());
        // 清空购物车
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, userId);
        stringRedisTemplate.delete(redisKey);
        return response;
    }
}
