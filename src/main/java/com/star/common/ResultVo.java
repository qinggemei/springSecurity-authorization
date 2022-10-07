package com.star.common;

import lombok.Data;

/**
 * Description : 通用返回对象
 */
@Data
//@ApiModel("通用返回对象")
public class ResultVo {

    /**
     * 错误码
     */
//    @ApiModelProperty("错误码")
    private Long code;

    /**
     * 提示信息
     */
//    @ApiModelProperty("提示信息")
    private String message;

    /**
     * 具体的内容
     */
//    @ApiModelProperty("响应数据")
    private Object data;

    protected ResultVo() {
    }

    protected ResultVo(long code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    public static ResultVo success() {
        return new ResultVo(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static ResultVo success(Object data) {
        return new ResultVo(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param message 提示信息
     * @param data    获取的数据
     */
    public static ResultVo success(String message, Object data) {
        return new ResultVo(ResultEnum.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static ResultVo failed(ErrorCode errorCode) {
        return new ResultVo(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static ResultVo failed(ErrorCode errorCode, Object data) {
        return new ResultVo(errorCode.getCode(), errorCode.getMessage(), data);
    }


    public static ResultVo failed(ErrorCode errorCode, String message) {
        return new ResultVo(errorCode.getCode(), message, null);
    }

    public static ResultVo failed(Integer errorCode, String message) {
        return new ResultVo(errorCode, message, null);
    }


    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static ResultVo failed(String message) {
        return new ResultVo(ResultEnum.ERROR.getCode(), message, null);
    }

    /**
     * 失败返回结果
     *
     * @param
     * @return
     */
    public static ResultVo failed() {
        return failed(ResultEnum.UNKNOWN_EXCEPTION);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     * @param data 数据
     */
    public static ResultVo failed(String message, Object data) {
        return new ResultVo(ResultEnum.UNKNOWN_EXCEPTION.getCode(), message, data);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param
     * @return
     */
    public static ResultVo validateFailed() {
        return failed(ResultEnum.INVALID_PARAMETER);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static ResultVo validateFailed(String message) {
        return new ResultVo(ResultEnum.INVALID_PARAMETER.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     *
     * @param data
     * @param
     * @return
     */
    public static ResultVo unauthorized(Object data) {
        return new ResultVo(ResultEnum.UNAUTHORIZED.getCode(), ResultEnum.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     *
     * @param data
     * @param
     * @return
     */
    public static ResultVo forbidden(Object data) {
        return new ResultVo(ResultEnum.FORBIDDEN.getCode(), ResultEnum.FORBIDDEN.getMessage(), data);
    }
}
