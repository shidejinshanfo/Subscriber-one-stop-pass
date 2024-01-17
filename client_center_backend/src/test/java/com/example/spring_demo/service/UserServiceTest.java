package com.example.spring_demo.service;

import com.example.spring_demo.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest

public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("test");
        user.setUserAccount("123");
        user.setAvatarUrl("https://images.zsxq.com/FvLh3A2dx2VixzMHxS-ymhKw-fDj?e=1698767999&token=kIxbL07-8jAj8w1n4s9zv64FuZZNEATmlU_Vm6zD:OKw66IUW6KHHwVwY3qsGqwHFtVU=" +
                "");
        user.setGender((byte) 0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister() {
        //正常
        String userAccount = "shidejinshanfo";
        String userPassword="12345678";
        String checkPassword = "12345678";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertTrue(result>0);
//        //少一个密码
//        userAccount = "shidejinshanfo";
//        userPassword = "";
//        checkPassword = "12345678";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1,result);
//        //用户名小于四位
//        userAccount = "sh";
//        userPassword = "12345678";
//        checkPassword = "12345678";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1,result);
//        //密码小于8位
//        userAccount = "shidejinshan";
//        userPassword = "123456";
//        checkPassword = "123456";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1,result);
//        //用户名重复
//        userAccount = "shidejinshanfo";
//        userPassword = "12345678";
//        checkPassword = "12345678";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1,result);
//        //包含特殊字符
//        userAccount = "shide jinshanfo";
//        userPassword = "12345678";
//        checkPassword = "12345678";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1,result);
//        //两次密码不一致
//        userAccount = "shidejinshanfo";
//        userPassword = "12345678";
//        checkPassword = "123456789";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1,result);


    }
}
