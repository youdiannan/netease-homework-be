package com.netease.contentsalesystem.service.impl;

import com.netease.contentsalesystem.constant.ResponseCode;
import com.netease.contentsalesystem.dao.AccountItemMapper;
import com.netease.contentsalesystem.dao.ProductMapper;
import com.netease.contentsalesystem.dao.UserTradedItemMapper;
import com.netease.contentsalesystem.entity.AccountItem;
import com.netease.contentsalesystem.entity.CartItem;
import com.netease.contentsalesystem.entity.UserTradedItem;
import com.netease.contentsalesystem.service.IAccountService;
import com.netease.contentsalesystem.vo.CommonResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountItemMapper accountItemMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<AccountItem> list(Integer userId) {
        return accountItemMapper.list(userId);
    }

    @Transactional
    public CommonResponse add(Integer userId, List<CartItem> cartItemList) {
        if (cartItemList == null) {
            return new CommonResponse(ResponseCode.FAILED.getCode(), "购物车为空");
        }
        // TODO: 异常处理
        Date transTime = new Date(); // 交易时间
        List<AccountItem> insertAccountItems = new ArrayList<>();
        for (CartItem item: cartItemList) {
            AccountItem accountItem = new AccountItem();
            accountItem.setUserId(userId);
            BeanUtils.copyProperties(item, accountItem);
            // set value
            accountItem.setProductName(item.getName());
            accountItem.setTotalAmount(accountItem.getPrice().multiply(
                    BigDecimal.valueOf(accountItem.getCount())));
            accountItem.setTransTime(transTime);
            insertAccountItems.add(accountItem);
            // TODO: 批量更新
            // 更新商品表已售出数量
            productMapper.updateSold(accountItem.getProductId(), accountItem.getCount());
        }
        accountItemMapper.batchInsert(insertAccountItems);
        return new CommonResponse(ResponseCode.SUCCESS.getCode(), "成功");
    }
}
