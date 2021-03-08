package com.netease.contentsalesystem.dao;

import com.netease.contentsalesystem.entity.User;

public interface UserMapper {
    int deleteById(Integer id);

    int insert(User record);

    User selectById(Integer id);

    int update(User record);

    User selectByUsername(String username);
}