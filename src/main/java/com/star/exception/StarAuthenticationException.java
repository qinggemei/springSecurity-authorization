package com.star.exception;

import com.star.common.ResultEnum;
import lombok.Data;

/**
 * @author liuxing
 */

@Data
public class StarAuthenticationException extends RuntimeException {

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 错误信息
     */
    private final String message;

    public StarAuthenticationException(){
        super("error");
        this.code= ResultEnum.ERROR.getCode();
        this.message=ResultEnum.ERROR.getMessage();
    }


    public StarAuthenticationException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        code=resultEnum.getCode();
        message= resultEnum.getMessage();
    }

    public StarAuthenticationException(Integer code,String message){
        super(message);
        this.code=code;
        this.message=message;
    }

    public StarAuthenticationException(String message){
        super(message);
        this.code=ResultEnum.ERROR.getCode();
        this.message=message;
    }


}
