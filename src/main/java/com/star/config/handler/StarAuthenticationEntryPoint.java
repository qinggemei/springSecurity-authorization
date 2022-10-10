package com.star.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author liuxing
 * @description
 * @create: 2022-10-10 20:24
 */
public class StarAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("status", 401);
        map.put("msg", "用户未登录");
        String value = new ObjectMapper().writeValueAsString(map);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().write(value);
    }
}
