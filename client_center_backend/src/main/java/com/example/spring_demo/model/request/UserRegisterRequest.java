package com.example.spring_demo.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author zzc
 */

@Data
//serializable 一个类的对象可以被序列化
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = -9121397041932207986L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
