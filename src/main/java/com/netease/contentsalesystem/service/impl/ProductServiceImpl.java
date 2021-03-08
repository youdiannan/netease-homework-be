package com.netease.contentsalesystem.service.impl;

import com.netease.contentsalesystem.constant.ResponseCode;
import com.netease.contentsalesystem.dao.AccountItemMapper;
import com.netease.contentsalesystem.dao.ProductMapper;
import com.netease.contentsalesystem.dao.UserTradedItemMapper;
import com.netease.contentsalesystem.entity.Product;
import com.netease.contentsalesystem.entity.UserTradedItem;
import com.netease.contentsalesystem.service.IProductService;
import com.netease.contentsalesystem.vo.CommonResponse;
import com.netease.contentsalesystem.vo.ProductVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("productService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserTradedItemMapper userTradedItemMapper;
    
    @Autowired
    private AccountItemMapper accountItemMapper;

    @Override
    public List<ProductVo> list(Integer userId) {
        List<Product> productList = productMapper.findAll();
        List<UserTradedItem> userTradedItems = userTradedItemMapper.selectByUserId(userId);
        return buildProductVo(productList, userTradedItems);
    }

    @Override
    public ProductVo detail(Integer userId, Integer productId) {
        Product product = productMapper.selectById(productId);
        if (product == null) return null;
        Set<Integer> tradedUsers = userTradedItemMapper.selectByProductId(product.getId());
        ProductVo productVo = new ProductVo();
        BeanUtils.copyProperties(product, productVo);
        if (userId != null && tradedUsers.contains(userId)) {
            productVo.setTraded(true);
            // 需要查询交易时的价格。
            // 但是如果可以多次购买的话，应该返回哪一次的价格？
            // Integer byUserIdAndProductId = accountItemMapper.findByUserIdAndProductId(userId, productId);
        } else {
            productVo.setTraded(false);
        }
        // 暂时不设置已交易数量
        return productVo;
    }

    @Override
    public CommonResponse<Integer> edit(Integer userId, Product product) {
        product.setSeller(userId);
        int row = 0;
        // 更新
        if (product.getId() != null) {
            row = productMapper.update(product);
        } else {
            // 新增
            row = productMapper.insert(product);
        }
        if (row == 0) {
            return new CommonResponse(ResponseCode.FAILED.getCode(), "添加/更新失败");
        }
        return new CommonResponse(product.getId());
    }

    private List<ProductVo> buildProductVo(List<Product> products, List<UserTradedItem> userTradedItems) {
        List<ProductVo> res = new ArrayList<>();
        for (Product product : products) {
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(product, productVo);
            for (UserTradedItem item : userTradedItems) {
                if (item.getProductId().equals(product.getId())) {
                    productVo.setTraded(true);
                    productVo.setTradeNum(item.getTradeNum());
                    break;
                }
            }
            res.add(productVo);
        }
        return res;
    }

    @Override
    public List<Product> findAllByProductIdList(List<Integer> productIds) {
        return productMapper.findAllByProductIdList(productIds);
    }

    @Override
    public CommonResponse delete(Integer productId) {
        int row = productMapper.deleteById(productId);
        return row == 0 ? new CommonResponse(ResponseCode.FAILED) : new CommonResponse(ResponseCode.SUCCESS);
    }
}
