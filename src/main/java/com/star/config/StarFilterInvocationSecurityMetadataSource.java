package com.star.config;

import com.star.entity.Menu;
import com.star.entity.Role;
import com.star.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * @author liuxing
 * @Description 授权
 */
@Component
public class StarFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final MenuService menuService;

    @Autowired
    public StarFilterInvocationSecurityMetadataSource(MenuService menuService) {
        this.menuService = menuService;
    }

    AntPathMatcher antPathMatcher = new AntPathMatcher();


    /**
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        final HttpServletRequest httpRequest = ((FilterInvocation) object).getHttpRequest();
        String requestURI = httpRequest.getRequestURI();
        List<Menu> allMenu = menuService.getAllMenu();
        for (Menu menu : allMenu){
            if(antPathMatcher.match(menu.getPattern(),requestURI)){
                String[] roles = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(roles);
            }
        }
            return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }


}
