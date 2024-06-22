package org.example.utils;

/**
 * 统一返回结果状态信息类
 *
 */
public enum ResultCodeEnum {

    SUCCESS(0,"success"),
//  USERNAME_ERROR(500,"usernameError"),
    USERNAME_ERROR(500,"不存在该用户"),

    ACCOUNT_USED(502, "学号已被使用"),

    PASSWORD_ERROR(503,"密码错误"),

    NOTLOGIN(504,"notLogin"),

    USERNAME_USED(505,"userNameUsed");

    private Integer code;
    private String message;
    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
