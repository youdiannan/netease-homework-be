package com.netease.contentsalesystem.controller;

import com.netease.contentsalesystem.annotation.IgnoreResponseAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

// 单页面应用视图解析
@RestController
@IgnoreResponseAdvice
public class RootController {

    @GetMapping
    public ModelAndView getIndex() {
        return new ModelAndView("index");
    }
}
