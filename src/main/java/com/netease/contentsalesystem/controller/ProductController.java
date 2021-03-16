package com.netease.contentsalesystem.controller;

import com.netease.contentsalesystem.constant.ResponseCode;
import com.netease.contentsalesystem.entity.Product;
import com.netease.contentsalesystem.entity.User;
import com.netease.contentsalesystem.service.IFileService;
import com.netease.contentsalesystem.service.IProductService;
import com.netease.contentsalesystem.vo.CommonResponse;
import com.netease.contentsalesystem.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.netease.contentsalesystem.constant.Const.CURRENT_USER;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IFileService fileService;

    @GetMapping("/api/product")
    public List<ProductVo> list(HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return productService.list(user);
    }

    // 成功则返回修改后商品的id
    @PostMapping("/api/product")
    public CommonResponse edit(HttpSession session, @RequestBody @Valid Product product) {
        // 编辑时需保证商品id不为空
        if (null == product.getId()) {
            return new CommonResponse(ResponseCode.FAILED);
        }
        User user = (User) session.getAttribute(CURRENT_USER);
        return productService.edit(user.getId(), product);
    }

    // 添加商品，成功则返回新增商品的id
    @PutMapping("/api/product")
    public CommonResponse add(HttpSession session, @RequestBody @Valid Product product) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return productService.add(user.getId(), product);
    }

    // 图片上传，返回上传成功后的图片url
    @PostMapping("/api/upload")
    public CommonResponse<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        return fileService.upload(file);
    }

    @GetMapping("/api/product/{productId}")
    public ProductVo detail(HttpSession session, @PathVariable Integer productId) {
        User user = (User) session.getAttribute(CURRENT_USER);
        Integer userId = user == null ? null : user.getId();
        return productService.detail(userId, productId);
    }

    @DeleteMapping("/api/product/{productId}")
    public CommonResponse delete(@PathVariable Integer productId) {
        return productService.delete(productId);
    }
}
