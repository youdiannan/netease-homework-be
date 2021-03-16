package com.netease.contentsalesystem.service.impl;

import com.netease.contentsalesystem.constant.ResponseCode;
import com.netease.contentsalesystem.dao.AccountItemMapper;
import com.netease.contentsalesystem.dao.ProductMapper;
import com.netease.contentsalesystem.dao.UserTradedItemMapper;
import com.netease.contentsalesystem.entity.Product;
import com.netease.contentsalesystem.entity.User;
import com.netease.contentsalesystem.service.IProductService;
import com.netease.contentsalesystem.vo.CommonResponse;
import com.netease.contentsalesystem.vo.ProductVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("productService")
@Slf4j
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private AccountItemMapper accountItemMapper;

    @Override
    public List<ProductVo> list(User user) {
        List<Product> productList = productMapper.findAll();
        // TODO: 多用户情况需要重写
        // 单一买家和卖家的情况下，若商品售出数不为0则说明该商品已经被购买/售出
        return buildProductVo(productList, user);
    }

    @Override
    public ProductVo detail(Integer userId, Integer productId) {
        // TODO: 多用户情况需要重写
        log.info("userId: {}, productId: {}", userId, productId);
        Product product = productMapper.selectById(productId);
        if (product == null) return null;
        ProductVo productVo = new ProductVo();
        BeanUtils.copyProperties(product, productVo);
        List<BigDecimal> oldPriceList = null;
        if (userId != null) {
            oldPriceList = accountItemMapper.listPrices(userId, productId);
            // 单一买家和卖家的情况下，若商品售出数不为0则说明该商品已经被购买/售出
            if (!product.getSold().equals(0)) {
                productVo.setTraded(true);
            }
        }
        productVo.setOldPriceList(oldPriceList);
        return productVo;
    }

    @Override
    public CommonResponse edit(Integer userId, Product product) {
        product.setSeller(userId);
        int row = productMapper.update(product);
        if (row == 0) {
            return new CommonResponse(ResponseCode.FAILED.getCode(), "更新失败");
        }
        return new CommonResponse(product.getId());
    }

    @Override
    public CommonResponse add(Integer userId, Product product) {
        product.setSeller(userId);
        int row = productMapper.insert(product);
        if (row == 0) {
            return new CommonResponse(ResponseCode.FAILED.getCode(), "添加失败");
        }
        // mybatis配置插入成功后注入生成的id
        return new CommonResponse(product.getId());
    }

    private List<ProductVo> buildProductVo(List<Product> products, User user) {
        List<ProductVo> res = new ArrayList<>();
        for (Product product: products) {
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(product, productVo);
            if (user != null && !product.getSold().equals(0)) {
                productVo.setTraded(true);
                productVo.setTradeNum(product.getSold());
            } else {
                productVo.setTraded(false);
                productVo.setTradeNum(0);
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
        // TODO: 修改商品状态而不是直接删除数据库
        int row = productMapper.deleteById(productId);
        return row == 0 ? new CommonResponse(ResponseCode.FAILED) : new CommonResponse(ResponseCode.SUCCESS);
    }
}
