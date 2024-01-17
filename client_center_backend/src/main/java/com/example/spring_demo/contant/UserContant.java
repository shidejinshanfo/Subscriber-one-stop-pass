package com.example.spring_demo.contant;

/**
 * 用户常量
 *
 * @author zzc
 */
public interface UserContant {
    /**
     * 用户登录态键,保存在session中的键值对中的那个键
     */
    String USER_LOGIN_STATE = "userLoginState";

    // --------- 权限 ---------
    /**
     * 默认权限
     */
    int DEFAULT_ROLE = 0;

    /**
     * 管理员权限
     */
    int ADMIN_ROLE = 1;


}
