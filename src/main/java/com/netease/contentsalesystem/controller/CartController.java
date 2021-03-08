package com.netease.contentsalesystem.controller;

import com.netease.contentsalesystem.entity.Cart;
import com.netease.contentsalesystem.entity.User;
import com.netease.contentsalesystem.service.ICartService;
import com.netease.contentsalesystem.entity.CartItem;
import com.netease.contentsalesystem.vo.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.netease.contentsalesystem.constant.Const.CURRENT_USER;

@RestController
public class CartController {

    @Autowired
    private ICartService cartService;

    @GetMapping("/api/cart")
    public Cart list(HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        // 由拦截器来做登录验证
        return cartService.list(user.getId());
    }

    @PostMapping("/api/cart")
    public CommonResponse edit(HttpSession session, @RequestBody CartItem cartItem) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return cartService.edit(user.getId(), cartItem);
    }

    @GetMapping("/api/cart/checkout")
    public CommonResponse checkout(HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return cartService.checkout(user.getId());
    }

}
