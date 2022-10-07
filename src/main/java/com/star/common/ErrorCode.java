package com.star.common;

/**
 * @author : liuxing
 * Description : 错误码基础接口
 */
public interface ErrorCode {

    /**
     * 获取编码
     * @return 编码
     */
    int getCode();

    /**
     * 获取中文释义
     * @return 中文释义
     */
    String getMessage();

    /**
     * 判断两个错误码是否相等
     * @param another 待比较的其他错误码
     * @return 是否相等
     */
    boolean equals(ErrorCode another);
}
