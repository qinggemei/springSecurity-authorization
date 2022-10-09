package com.star.service;

import com.star.dao.UserMapper;
import com.star.entity.Role;
import com.star.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class   StarUserDetailsServce implements UserDetailsService , UserDetailsPasswordService {

    @Resource
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(StringUtils.isEmpty(username.trim())){
            throw new RuntimeException("请输入正确用户名");
        }
        User user = userMapper.loadUserByUsername(username);
        if(ObjectUtils.isEmpty(user)){
            throw new RuntimeException("用户不存在");
        }
        List<Role> rolesByUserId = userMapper.getRolesByUserId(user.getId());
        user.setRoles(rolesByUserId);
        return user;
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        userMapper.updatePassword(user.getUsername(),newPassword);
        ((User)user).setPassword(newPassword);
        return user;
    }
}
