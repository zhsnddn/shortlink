package com.example.shortlink.admin.common.enums;

import com.example.shortlink.admin.common.convention.errorcode.IErrorCode;

/**
 * 用户异常码
 */
public enum UserErrorCodeEnum implements IErrorCode {

    USER_TOKEN_ERROR("A000200", "用户TOKEN校验失败"),

    USER_NULL("B000200", "用户记录不存在"),

    USER_EXIST("B000201", "用户记录已存在");

    private final String code;

    private final String message;

    UserErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
