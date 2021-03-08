package com.netease.contentsalesystem.service;

import com.netease.contentsalesystem.entity.User;
import com.netease.contentsalesystem.vo.LoginForm;

public interface IUserService {

    User login(LoginForm loginForm);

    User getCurrentUser();
}
