package org.example.service;

import org.example.pojo.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.utils.Result;

/**
* @author a1380
* @description 针对表【user_info】的数据库操作Service
* @createDate 2024-06-20 16:36:17
*/
public interface UserInfoService extends IService<UserInfo> {

    Result UserLogin(UserInfo userInfo);

    Result UserRegister(UserInfo userInfo);

    Result setUserGrade(String authorization, UserInfo userInfo);

    Result getUserInfo(String authorization);
}
