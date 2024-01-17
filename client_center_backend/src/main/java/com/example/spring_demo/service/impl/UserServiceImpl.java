package com.example.spring_demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spring_demo.common.ErrorCode;
import com.example.spring_demo.exception.BusinessException;
import com.example.spring_demo.model.domain.User;
import com.example.spring_demo.service.UserService;
import com.example.spring_demo.mapper.UserMapper;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.spring_demo.contant.UserContant.USER_LOGIN_STATE;

/**
 *用户服务实现类
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "shidejinshanfo";


    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        //校验账户
        if (userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账户长度小于四");
        }
        //校验密码
        if (userPassword.length() < 8 || checkPassword.length() < 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度不得小于8");
        }
        //校验账户不能包含特殊字符
        String validPattern = "[\\s`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        //校验密码和密码相同
        if (!userPassword.equals(checkPassword)){
            return -1;
        }
        //账号不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count>0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        //2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //3.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveresult = this.save(user);
        if (!saveresult){
            return -1;
        }
        return user.getId();
    }

    @Override
    public User dologin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        //校验账户
        if (userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账户长度小于四");
        }
        //校验密码
        if (userPassword.length() < 8 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度不得小于8");
        }
        //校验账户不能包含特殊字符
        String validPattern = "[\\s`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名不得包含特殊字符");
        }
        //2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if (user == null){
            log.info("user login faild, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户不存在");
        }
        //3.用户脱敏
        User saftyUser = getsaftyUser(user);

        //4.记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE,saftyUser);//setsession里面的attribute是map类型（键值对）


        return saftyUser;
    }

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    @Override
    public User getsaftyUser(User originUser){
        if (originUser == null) {
            return null;
        }
        User saftyUser = new User();
        saftyUser.setId(originUser.getId());
        saftyUser.setUsername(originUser.getUsername());
        saftyUser.setUserAccount(originUser.getUserAccount());
        saftyUser.setAvatarUrl(originUser.getAvatarUrl());
        saftyUser.setGender(originUser.getGender());
        saftyUser.setPhone(originUser.getPhone());
        saftyUser.setEmail(originUser.getEmail());
        saftyUser.setUserRole(originUser.getUserRole());
        saftyUser.setCreateTime(originUser.getCreateTime());
        saftyUser.setUserStatus(originUser.getUserStatus());
        return saftyUser;
    }

    /**
     * 用户注销
     * @param request
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        //移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }


}




