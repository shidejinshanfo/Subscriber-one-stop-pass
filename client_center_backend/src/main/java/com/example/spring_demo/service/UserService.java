package com.example.spring_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spring_demo.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录
 */
public interface UserService extends IService<User> {


    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    User dologin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * @param originUser
     * @return
     */
    User getsaftyUser(User originUser);

    /**
     * 请求用户注销
     * @param request
     */
    int userLogout(HttpServletRequest request);
}
