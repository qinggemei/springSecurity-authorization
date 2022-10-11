package com.star.exception;

import lombok.Data;

import javax.naming.AuthenticationException;


/**
 * @author liuxing
 */
@Data
public class KaptchaNotMatchException extends RuntimeException {

    public KaptchaNotMatchException(String msg) {
        super(msg);
    }

}