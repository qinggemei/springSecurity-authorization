package com.star.exception;

import lombok.Data;

import javax.naming.AuthenticationException;


/**
 * @author liuxing
 */
@Data
public class KaptchaNotMatchException extends AuthenticationException {

    public KaptchaNotMatchException(String msg) {
        super(msg);
    }

}