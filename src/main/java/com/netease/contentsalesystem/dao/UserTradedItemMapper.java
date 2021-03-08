package com.netease.contentsalesystem.dao;

import com.netease.contentsalesystem.entity.UserTradedItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserTradedItemMapper {
    int deleteById(Long id);

    int insert(UserTradedItem record);

    List<UserTradedItem> selectByUserId(Integer userId);

    Set<Integer> selectByProductId(Integer productId);

    int update(UserTradedItem record);

    int batchInsert(@Param("items") List<UserTradedItem> insertItems);
}