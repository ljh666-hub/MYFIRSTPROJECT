package com.ljh.controller;

import com.ljh.exception.BusinessException;
import com.ljh.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class exceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public Result businessException(BusinessException e){
        return new Result(null,e.getStatus(),e.getMessage());
    }

    @ExceptionHandler(SystemException.class)
    public Result exception(SystemException e){
        return new Result(null,e.getStatus(),e.getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    public Result exception(Exception e){
//        return new Result(null,50000,"服务器繁忙，请稍后重试");
//    }
}
