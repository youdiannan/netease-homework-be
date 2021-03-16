package com.netease.contentsalesystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.netease.contentsalesystem.constant.Const.DEFAULT_UPLOAD_PATH;

// 图片资源映射，解决上传后需要重启才能看到的问题
@Configuration
public class ImgConfig implements WebMvcConfigurer {

    @Autowired
    private Environment environment;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations(
                "file:" + environment.getProperty(DEFAULT_UPLOAD_PATH) + "\\");
    }
}
