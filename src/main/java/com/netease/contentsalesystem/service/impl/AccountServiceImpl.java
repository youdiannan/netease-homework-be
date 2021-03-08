package com.netease.contentsalesystem.service.impl;

import com.netease.contentsalesystem.constant.ResponseCode;
import com.netease.contentsalesystem.dao.AccountItemMapper;
import com.netease.contentsalesystem.dao.UserTradedItemMapper;
import com.netease.contentsalesystem.entity.AccountItem;
import com.netease.contentsalesystem.entity.CartItem;
import com.netease.contentsalesystem.entity.UserTradedItem;
import com.netease.contentsalesystem.service.IAccountService;
import com.netease.contentsalesystem.vo.CommonResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountItemMapper accountItemMapper;

    @Autowired
    private UserTradedItemMapper userTradedItemMapper;

    @Override
    public List<AccountItem> list(Integer userId) {
        return accountItemMapper.list(userId);
    }

    @Transactional
    public CommonResponse add(Integer userId, List<CartItem> cartItemList) {
        List<AccountItem> insertAccountItems = new ArrayList<>();
        List<UserTradedItem> insertUserTradedItems = new ArrayList<>();
        for (CartItem item: cartItemList) {
            Integer row = accountItemMapper.findByUserIdAndProductId(userId, item.getProductId());
            AccountItem accountItem = new AccountItem();
            BeanUtils.copyProperties(item, accountItem);
            accountItem.setUserId(userId);
            accountItem.setProductName(item.getName());
            accountItem.setTotalAmount(accountItem.getPrice().
                    multiply(BigDecimal.valueOf(accountItem.getCount())));
            // 加入批量更新队列
            if (row == null || row == 0) {
                insertAccountItems.add(accountItem);
                // 买家
                UserTradedItem buyerItem = new UserTradedItem();
                buyerItem.setProductId(item.getProductId());
                buyerItem.setUserId(userId);
                buyerItem.setTradeNum(item.getCount());
                insertUserTradedItems.add(buyerItem);
                // 卖家
                UserTradedItem sellerItem = new UserTradedItem();
                sellerItem.setProductId(item.getProductId());
                sellerItem.setUserId(item.getSeller());
                sellerItem.setTradeNum(item.getCount());
                insertUserTradedItems.add(sellerItem);
            } else {
                // 增量购买
                accountItemMapper.updateByUserAndProductId(userId, accountItem.getProductId());
            }
        }
        accountItemMapper.batchInsert(insertAccountItems);
        userTradedItemMapper.batchInsert(insertUserTradedItems);
        return new CommonResponse(ResponseCode.SUCCESS.getCode(), "成功");
    }
}
