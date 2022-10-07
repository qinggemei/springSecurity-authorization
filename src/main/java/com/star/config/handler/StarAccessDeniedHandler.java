package com.star.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author liuxing
 * @description 未授权处理
 * @create: 2022-10-07 23:21
 */

@Component
public class StarAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("status", 401);
        map.put("msg", "用户无权限");
        String value = new ObjectMapper().writeValueAsString(map);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().write(value);
    }
}
