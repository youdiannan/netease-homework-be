package com.netease.contentsalesystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@SpringBootApplication
@MapperScan(basePackages = {"com.netease.contentsalesystem.dao"})
public class ContentSaleSystemApplication {

    private static final String VIEW_PREFIX = "/static/";
    private static final String VIEW_SUFFIX = ".html";
    private static final String VIEW_CONTENT_TYPE = "text/html;charset=UTF-8";

    public static void main(String[] args) {
        SpringApplication.run(ContentSaleSystemApplication.class, args);
    }

    // 本地开发时使用
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOriginPatterns("http://localhost:3000").
                        allowCredentials(true).allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }

    @Bean
    public ViewResolver viewResolver(){

        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setCache(true);
        resolver.setPrefix(VIEW_PREFIX);
        resolver.setSuffix(VIEW_SUFFIX);
        resolver.setExposeContextBeansAsAttributes(true);
        resolver.setContentType(VIEW_CONTENT_TYPE);
        return resolver;
    }

}
