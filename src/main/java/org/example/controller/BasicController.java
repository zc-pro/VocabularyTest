package org.example.controller;

import org.apache.tomcat.util.http.parser.Authorization;
import org.example.pojo.UserInfo;
import org.example.service.UserInfoService;
import org.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: BasicalController
 * Package: org.example.controller
 *
 * @Author 吴圳城
 * @Create 2024/6/20 23:10
 * @Version 1.0
 * Description: 基本信息管理类
 */
@RestController
@RequestMapping("basic-api")
@CrossOrigin
public class BasicController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("setGrade")
    public Result setUserGrade(@RequestHeader String authorization, @RequestBody UserInfo userInfo){
//        System.out.println("authorization = " + authorization + ", userInfo = " + userInfo);
        Result result = userInfoService.setUserGrade(authorization, userInfo);
        return  result;
    }

    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String authorization){
//        System.out.println("authorization = " + authorization);
        Result result = userInfoService.getUserInfo(authorization);
        return result;
    }

}
