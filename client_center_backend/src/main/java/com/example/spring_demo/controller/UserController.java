package com.example.spring_demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.spring_demo.common.BaseResponse;
import com.example.spring_demo.common.ErrorCode;
import com.example.spring_demo.common.ResultUtils;
import com.example.spring_demo.exception.BusinessException;
import com.example.spring_demo.model.domain.User;
import com.example.spring_demo.model.request.UserLoginRequest;
import com.example.spring_demo.model.request.UserRegisterRequest;
import com.example.spring_demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.spring_demo.contant.UserContant.ADMIN_ROLE;
import static com.example.spring_demo.contant.UserContant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author zzc
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {    //打上注解，不然springmvc不知道怎么把前端传来的json参数和这个对象关联
        if (userRegisterRequest == null) { //校验，和service的校验一样，重复一次的原因是controller倾向于请求参数本身的校验，不涉及业务逻辑本身
            throw new BusinessException(ErrorCode.PARAMS_ERROR);//抛出异常，请求参数异常
        }
        //规范化
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.succes(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.dologin(userAccount, userPassword, request);
        return ResultUtils.succes(user);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.succes(result);
    }

    /**
     * 获取当前用户态
     *
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        //todo 校验用户是否合法
        User user = userService.getById(userId);
        User saftyUser = userService.getsaftyUser(user);
        return ResultUtils.succes(saftyUser);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        if (!IsAdmin(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);//抛出请求参数异常
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user -> userService.getsaftyUser(user)).collect(Collectors.toList());
        return ResultUtils.succes(list);
    }


    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!IsAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(id);//会改造为逻辑删除，
        return ResultUtils.succes(b);
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return 布尔
     */
    private boolean IsAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}

