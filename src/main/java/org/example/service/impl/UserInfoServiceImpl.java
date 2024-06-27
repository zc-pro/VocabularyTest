package org.example.service.impl;

import com.alibaba.druid.sql.visitor.functions.Now;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.pojo.UserInfo;
import org.example.service.UserInfoService;
import org.example.mapper.UserInfoMapper;
import org.example.utils.JwtHelper;
import org.example.utils.MD5Util;
import org.example.utils.Result;
import org.example.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.LambdaConversionException;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* @author a1380
* @description 针对表【user_info】的数据库操作Service实现
* @createDate 2024-06-20 16:36:17
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private JwtHelper jwtHelper;
    @Override
    public Result UserLogin(UserInfo userInfo) {

        //根据账号查询
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getAccount,userInfo.getAccount());
        UserInfo loginUserAccount = userInfoMapper.selectOne(queryWrapper);
        //账号判断
        if(loginUserAccount == null){
            //账号错误
            return  Result.build(null,ResultCodeEnum.USERNAME_ERROR);
        }
        //判断密码
        if(!StringUtils.isEmpty(userInfo.getPassword())
            &&loginUserAccount.getPassword().equals(MD5Util.encrypt(userInfo.getPassword()))){
            //账号密码正确
            //根据用户唯一标识生成token
            String token = jwtHelper.createToken(Long.valueOf(loginUserAccount.getId()));
            Map result = new HashMap();
            result.put("token",token);
            return Result.ok(result);
        }
        //密码错误
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    @Override
    public Result UserRegister(UserInfo userInfo) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getAccount, userInfo.getAccount());
        Long count_Account = userInfoMapper.selectCount(queryWrapper);
        queryWrapper.eq(UserInfo::getUsername, userInfo.getUsername());
        Long count_Username = userInfoMapper.selectCount(queryWrapper);
        if(count_Account>0||count_Username>0){
            return  Result.build(null, ResultCodeEnum.ACCOUNT_USED);
        }

        userInfo.setPassword(MD5Util.encrypt(userInfo.getPassword()));
        Date date = new Date();
        userInfo.setCreatedAt(date);
        userInfo.setUpdatedAt(date);
        int rows = userInfoMapper.insert(userInfo);
        return Result.ok(null);
    }

    @Override
    public Result setUserGrade(String authorization, UserInfo userInfo) {
        if(jwtHelper.isExpiration(authorization)){
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        int userId = jwtHelper.getUserId(authorization).intValue();
        UserInfo userInfo_select = userInfoMapper.selectById(userId);
        if(userInfo_select!=null) {
            userInfo_select.setFourGrade(userInfo.getFourGrade());
            userInfo_select.setSixGrade(userInfo.getSixGrade());
            userInfo_select.setUpdatedAt(new Date());
            userInfoMapper.updateById(userInfo_select);
            return Result.ok(null);
        }
        return Result.build(null, ResultCodeEnum.NOTLOGIN);
    }

    @Override
    public Result getUserInfo(String authorization) {
        if(jwtHelper.isExpiration(authorization)){
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        int userId = jwtHelper.getUserId(authorization).intValue();
        UserInfo userInfo = userInfoMapper.selectById(userId);
        if(userInfo!=null){
            Map result = new HashMap();
            result.put("userId",userInfo.getId());
            result.put("username",userInfo.getUsername());
            result.put("account",userInfo.getAccount());
            result.put("password",userInfo.getPassword());
            result.put("fourGrade",userInfo.getFourGrade());
            result.put("sixGrade",userInfo.getSixGrade());
            result.put("basicVocabulary",userInfo.getBasicVocabulary());
            return Result.ok(result);
        }
        return Result.build(null,ResultCodeEnum.NOTLOGIN);
    }
}




