package com.star.exception;

import com.star.common.ResultEnum;
import lombok.Data;

/**
 * @author liuxing
 */
@Data
public class StarCustomException extends RuntimeException{

    private final Integer code;

    private final String message;

    public StarCustomException(ResultEnum resultEnum) {
        this.code=resultEnum.getCode();
        this.message=resultEnum.getMessage();
    }

    public StarCustomException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
