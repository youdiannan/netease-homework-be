package com.netease.contentsalesystem.controller;

import com.netease.contentsalesystem.constant.ResponseCode;
import com.netease.contentsalesystem.entity.User;
import com.netease.contentsalesystem.exception.LoginException;
import com.netease.contentsalesystem.service.IUserService;
import com.netease.contentsalesystem.vo.CommonResponse;
import com.netease.contentsalesystem.vo.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.netease.contentsalesystem.constant.Const.CURRENT_USER;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/api/user")
    public User currentUser(HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return user;
    }

    @PostMapping("/api/login")
    public User login(HttpSession session, @RequestBody @Valid LoginForm loginForm) throws LoginException {
        User user = userService.login(loginForm);
        if (user != null) {
            session.setAttribute(CURRENT_USER, user);
            user.setPassword("");
        } else {
            throw new LoginException("用户名或密码错误"); // 异常处理器统一处理为返回“用户名或密码错误”
        }
        return user;
    }

    @GetMapping("/api/logout")
    public CommonResponse logout(HttpSession session) {
        session.removeAttribute(CURRENT_USER);
        return new CommonResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }
}
