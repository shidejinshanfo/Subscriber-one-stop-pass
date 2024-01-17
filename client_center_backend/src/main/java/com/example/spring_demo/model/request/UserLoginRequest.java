package com.example.spring_demo.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author zzc
 */

@Data
//serializable 一个类的对象可以被序列化
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 2977853984037338352L;
    private String userAccount;
    private String userPassword;
}
