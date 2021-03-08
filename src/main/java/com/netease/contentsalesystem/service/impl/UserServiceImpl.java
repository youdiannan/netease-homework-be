package com.netease.contentsalesystem.service.impl;

import com.netease.contentsalesystem.dao.UserMapper;
import com.netease.contentsalesystem.entity.User;
import com.netease.contentsalesystem.service.IUserService;
import com.netease.contentsalesystem.vo.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(LoginForm loginForm) {
        User user = userMapper.selectByUsername(loginForm.getUsername());
        // 应该加个盐值或者用其他方式再加密一遍
        if (user != null && user.getPassword().equalsIgnoreCase(loginForm.getPassword())) {
            return user;
        }
        // 用户不存在或密码错误
        return null;
    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}
