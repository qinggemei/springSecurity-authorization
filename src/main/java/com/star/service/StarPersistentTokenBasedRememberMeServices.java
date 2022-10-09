package com.star.service;

import org.springframework.core.log.LogMessage;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletRequest;

/**
 * @description 自定义记住我持久化登录
 * @author liuxing
 */


public class StarPersistentTokenBasedRememberMeServices extends PersistentTokenBasedRememberMeServices {

    public StarPersistentTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
        super(key, userDetailsService, tokenRepository);
    }

    /**
     * 自定义前后端分离获取 remember-me 方式
     * 从request中获取remember-me值，值是在UserDetailsService中获取时传入Attribute的
     */
    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {

        Object paramValue1 = request.getAttribute(parameter);
        if (paramValue1 != null) {
            String paramValue = paramValue1.toString();
            if (paramValue.equalsIgnoreCase("true") || paramValue.equalsIgnoreCase("on")
                    || paramValue.equalsIgnoreCase("yes") || paramValue.equals("1")) {
                return true;
            }
        }
        this.logger.debug(
                LogMessage.format("Did not send remember-me cookie (principal did not set parameter '%s')", parameter));
        return false;
    }
}
