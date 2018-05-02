package com.parking.server.service;

import com.parking.server.objects.Pojo;
import com.parking.server.objects.enums.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    //@Autowired
    private IUserService userService = new UserServiceImpl();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Pojo pojo = userService.getUser(email);
        Set<GrantedAuthority> roles = new HashSet();

        if (pojo.getReferences().containsValue(new Long(1))) {
            roles.add(new SimpleGrantedAuthority(UserRoleEnum.ADMIN.name()));
        }
        if (pojo.getReferences().containsValue(new Long(2))) {
            roles.add(new SimpleGrantedAuthority(UserRoleEnum.OWNER.name()));
        }
        if (pojo.getReferences().containsValue(new Long(3))) {
            roles.add(new SimpleGrantedAuthority(UserRoleEnum.USER.name()));
        }

        String pass = null;
        for (Map.Entry entry : pojo.getValues().entrySet()) {
            if ((Long) entry.getKey() == 204) {
                pass = entry.getValue().toString();
            }
        }

        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(email, pass, roles);

        return userDetails;
    }
}