package com.netease.contentsalesystem.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netease.contentsalesystem.entity.User;
import com.netease.contentsalesystem.service.IUserService;
import com.netease.contentsalesystem.vo.LoginForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    public void loginTest() throws JsonProcessingException {
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername("buyer");
        loginForm.setPassword("37254660e226ea65ce6f1efd54233424");
        User user = userService.login(loginForm);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(user));
    }

}
