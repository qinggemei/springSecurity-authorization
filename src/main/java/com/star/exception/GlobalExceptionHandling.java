package com.star.exception;

import com.star.common.ResultEnum;
import com.star.common.ResultVo;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 刘星
 * @Descriptoin: 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(StarAuthenticationException.class)
    public ResultVo handleAuthentication(StarAuthenticationException e){
        return ResultVo.failed(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(StarCustomException.class)
    public ResultVo handleCustom(StarCustomException e){
        return ResultVo.failed(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(KaptchaNotMatchException.class)
    public ResultVo handleCustom(KaptchaNotMatchException e){
        return ResultVo.failed(ResultEnum.ERROR.getCode(),e.getMessage());
    }

    

}
