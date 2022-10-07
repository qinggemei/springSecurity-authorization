package com.star.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @description Session过期策略,用于前后端分离开发处理
 * @author liuxing
 */

public class StarSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletRequest request = event.getRequest();
        HttpServletResponse response = event.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<>(2);
        map.put("status",201);
        map.put("msg","当前会话已失效，请重新登录");
        ObjectMapper objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(map);
        PrintWriter writer = response.getWriter();
        writer.write(value);
        writer.flush();
        writer.close();
    }
}
