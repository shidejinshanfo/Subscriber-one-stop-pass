package com.example.spring_demo.exception;

import com.example.spring_demo.common.ErrorCode;

/**
 * 自定义异常类
 *
 * @author zzc
 */
public class BusinessException extends RuntimeException {   //运行时异常，不用捕获
    private final int code;
    private final String desciption;

    public BusinessException(String message, int code, String desciption) {
        super(message);
        this.code = code;
        this.desciption = desciption;
    }

    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.desciption = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode,String desciption){
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.desciption = desciption;
    }

    public int getCode() {
        return code;
    }

    public String getDesciption() {
        return desciption;
    }
}
