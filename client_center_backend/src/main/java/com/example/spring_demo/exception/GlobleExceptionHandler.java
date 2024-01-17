package com.example.spring_demo.exception;

import com.example.spring_demo.common.BaseResponse;
import com.example.spring_demo.common.ErrorCode;
import com.example.spring_demo.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author zzc
 */
@RestControllerAdvice //整个应用程序范围内处理抛出的异常。这些方法通常用于捕获和处理控制器中抛出的异常，然后返回适当的响应，而不是让异常直接传播到客户端。
@Slf4j
public class GlobleExceptionHandler {

    @ExceptionHandler(BusinessException.class)//接受一个数组，必须继承异常接口，
    public BaseResponse businessExceptionHandler(BusinessException e){
        log.error("businessException"+e.getMessage(),e);
        return ResultUtils.error(e.getCode(),e.getMessage(),e.getDesciption());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e){
        log.error("runtimeException",e);//撰写日志，出现runtimeException都会日志记录
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
    }

}
