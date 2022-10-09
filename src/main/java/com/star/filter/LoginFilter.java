package com.star.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.star.exception.KaptchaNotMatchException;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 自定义前后端分离认证 Filter
 *
 * @author liuxing
 */

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    public static final String KAPTCHA_KEY = "kaptcha";//默认值

    private String kaptcha = KAPTCHA_KEY;

    public String getKaptcha() {
        return kaptcha;
    }

    public void setKaptcha(String kaptcha) {
        this.kaptcha = kaptcha;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //1.判断是否是 post 方式请求
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        //2.判断是否是 json 格式请求类型
        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            //3.从 json 数据中获取用户输入用户名和密码进行认证 {"uname":"xxx","password":"xxx","remember-me":true}
            Map<String, String> userInfo = null;
            try {
                userInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            } catch (IOException e) {
                logger.info("-------------------请求数据解析失败-------------------");
                e.printStackTrace();
            }
            String username = userInfo.get(getUsernameParameter());
            String password = userInfo.get(getPasswordParameter());
            // 验证码
            String kaptcha = userInfo.get(getKaptcha());
            String code = (String) request.getSession().getAttribute(getKaptcha());
            if(StringUtils.hasText(kaptcha) && StringUtils.hasText(code) && kaptcha.equals(code)){
                // remember-me
                String rememberValue = userInfo.get(AbstractRememberMeServices.DEFAULT_PARAMETER);
                // remember-me 放入request中，为自定义rememberMeService提供
                if (StringUtils.hasText(rememberValue)) {
                    request.setAttribute(AbstractRememberMeServices.DEFAULT_PARAMETER, rememberValue);
                }
                logger.info("用户名: " + username + " 密码: " + password + " 是否记住我: " + rememberValue);
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }else {
                throw new KaptchaNotMatchException("验证码错误");
            }
        }
        throw new AuthenticationServiceException("登录信息错误");
    }
}
