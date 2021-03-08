package com.netease.contentsalesystem.service;

import com.netease.contentsalesystem.entity.AccountItem;
import com.netease.contentsalesystem.entity.CartItem;
import com.netease.contentsalesystem.vo.CommonResponse;

import java.util.List;

public interface IAccountService {

    List<AccountItem> list(Integer userId);

    CommonResponse add(Integer userId, List<CartItem> cartItemList);
}
