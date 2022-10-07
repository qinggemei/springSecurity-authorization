package com.star.common;

import lombok.Getter;

/**
 * Description : 返回状态枚举
 */
@Getter
public enum ResultEnum implements ErrorCode {
    // basic
    SUCCESS(200, "成功"),
    ERROR(100, "失败"),
    FORMAT_ERROR(101, "参数格式错误"),
    TIME_OUT(102, "超时"),
    ADD_ERROR(103, "添加失败"),
    UPDATE_ERROR(104, "更新失败"),
    DELETE_ERROR(105, "删除失败"),
    GET_ERROR(106, "查找失败"),
    ARGUMENT_TYPE_MISMATCH(107, "参数类型不匹配"),
    REQ_METHOD_NOT_SUPPORT(110,"请求方式不支持"),
    INVALID_PARAMETER(400, "参数错误，请检查相关参数"),
    UNAUTHORIZED(401, "身份信息过期/没有登陆(认证)"),
    FORBIDDEN(403, "没有访问权限"),
    NO_ROLES(450, "无角色信息"),
    NO_PERMISSIONS(451, "无权限信息"),
    UNKNOWN_EXCEPTION(500, "未知异常"),

    // user and permission
    USERNAME_OR_PASSWORD_MISMATCH(1001, "用户名或密码不正确"),
    ROLE_ALREADY_EXISTS(1002, "该角色信息已存在"),

    // common
    FAILED(2001, "失败"),

    // item
    REQUEST_ITEM_NOT_EXISTS(3001, "请求的资源不存在"),
    DUPLICATED_ITEM(3002, "请求的资源已经存在"),

    // user
    COULD_NOT_DELETE_SELF(4001, "不能删除自己"),


    // redis
    REDIS_CORRELATE_EXCEPTION_PREFIX(6379,"Redis 操作异常---->"),

    ;

    private final int code;
    private final String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean equals(ErrorCode another) {
        return this.getCode() == another.getCode();
    }

    /**
     * 通过状态码获取枚举对象
     * @param code 状态码
     * @return 枚举对象
     */
    public static ResultEnum getByCode(int code){
        for (ResultEnum resultEnum : ResultEnum.values()) {
            if(code == resultEnum.getCode()){
                return resultEnum;
            }
        }
        return null;
    }
}