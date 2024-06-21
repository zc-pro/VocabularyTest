package org.example.controller;

import org.example.pojo.UserInfo;
import org.example.service.UserInfoService;
import org.example.utils.Result;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: UserController
 * Package: org.example.controller
 *
 * @Author 吴圳城
 * @Create 2024/6/20 16:44
 * @Version 1.0
 * Description: 用户管理类
 */
@RestController
@RequestMapping("login-api")
@CrossOrigin
public class UserController {

    @Autowired
    private UserInfoService userInfoService;
    @PostMapping("register")
    public Result UserRegister(@RequestBody UserInfo userInfo){
        Result result = userInfoService.UserRegister(userInfo);
        return result;
    }

    @PostMapping("login")
    public Result UserLogin(@RequestBody UserInfo userInfo){
        Result result = userInfoService.UserLogin(userInfo);
        return result;
    }

}
