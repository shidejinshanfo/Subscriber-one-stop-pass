package com.example.spring_demo.common;

/**
 * 返回工具类
 *
 * @author zzc
 */
public class ResultUtils {
    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> succes(T data){
        return new BaseResponse<T>(0,data,"ok");
    }

    /**
     * 失败
     * @param errorCode
     * @return 传入了errorCode的 BaseResponse对象，因为调用了BaseResponse的最后一个构造函数
     */
    public static BaseResponse error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败，允许传入状态码信息，错误详情
     * @param errorCode
     * @param message
     * @param description
     * @return 返回错误码（int），状态码表示的信息，详情
     */
    public static BaseResponse error(ErrorCode errorCode,String message,String description){
        return new BaseResponse<>(errorCode.getCode(),null,message,description);
    }

    /**
     * 失败，状态码信息从errorcode获取
     * @param errorCode
     * @param description
     * @return 返回错误码（int），状态码表示的信息，详情
     */
    public static BaseResponse error(ErrorCode errorCode,String description){
        return new BaseResponse<>(errorCode.getCode(),errorCode.getMessage(),description);
    }

    /**
     * 失败，支持自定义code
     * @param code
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(int code,String message,String description){
        return new BaseResponse<>(code,null,message,description);
    }
}
