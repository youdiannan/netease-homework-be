package com.netease.contentsalesystem.controller;

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
        return productService.list(user != null ? user.getId() : null);
    }

    // 返回修改后商品的id
    @PostMapping("/api/product")
    public CommonResponse<Integer> edit(HttpSession session, @RequestBody Product product) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return productService.edit(user.getId(), product);
    }

    // 图片上传，返回上传成功后的图片url
    @PostMapping("/api/upload")
    public CommonResponse<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        return fileService.upload(file);
    }

    @GetMapping("/api/product/{productId}")
    public ProductVo detail(HttpSession session, @PathVariable Integer productId) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return productService.detail(user == null ? null : user.getId(), productId);
    }

    @DeleteMapping("/api/product/{productId}")
    public CommonResponse delete(@PathVariable Integer productId) {
        return productService.delete(productId);
    }
}
