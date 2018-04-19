package com.site.services;

import com.site.entity.Pojo;
import com.site.entity.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService{

    @Autowired
    private MainService userService;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Pojo pojo = userService.getPojo();
        Set<GrantedAuthority> roles = new HashSet();
        roles.add(new SimpleGrantedAuthority(RoleEnum.USER.name()));

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                pojo.getValues().get("login"),
                pojo.getValues().get("password"),
                roles);
        return userDetails;
    }
}
