package com.netease.contentsalesystem.advice;

import com.netease.contentsalesystem.constant.ResponseCode;
import com.netease.contentsalesystem.exception.LoginException;
import com.netease.contentsalesystem.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(LoginException.class)
    public CommonResponse handleLoginException(LoginException e) {
        CommonResponse response = new CommonResponse(ResponseCode.FAILED.getCode(), "用户名或密码错误");
        // response.setData(e.getMessage());
        return response;
    }
}
