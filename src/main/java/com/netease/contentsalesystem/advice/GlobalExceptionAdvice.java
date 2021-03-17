package com.netease.contentsalesystem.advice;

import com.netease.contentsalesystem.constant.ResponseCode;
import com.netease.contentsalesystem.exception.LoginException;
import com.netease.contentsalesystem.vo.CommonResponse;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(LoginException.class)
    public CommonResponse handleLoginException(LoginException e) {
        CommonResponse response = new CommonResponse(ResponseCode.FAILED.getCode(), "用户名或密码错误");
        // response.setData(e.getMessage());
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResponse notValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        Objects.requireNonNull(bindingResult.getFieldError());
        return new CommonResponse(ResponseCode.PARAM_ERROR.getCode(),
                bindingResult.getFieldError().getField() + " " +
                        bindingResult.getFieldError().getDefaultMessage());
    }
}
