package com.netease.contentsalesystem.dao;

import com.netease.contentsalesystem.entity.AccountItem;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AccountItemMapper {
    int deleteById(Long id);

    int insert(AccountItem record);

    AccountItem selectById(Long id);

    int update(AccountItem record);

    List<AccountItem> list(Integer userId);

    List<BigDecimal> listPrices(@Param("userId") Integer userId, @Param("productId") Integer productId);

    Integer findByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    int updateByUserAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    int batchInsert(@Param("accountItems") List<AccountItem> accountItems);
}