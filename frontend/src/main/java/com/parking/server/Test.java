package com.parking.server;

import com.parking.server.cache.CacheClient;
import com.parking.server.objects.Pojo;
import com.parking.server.service.IUserService;
import com.parking.server.service.UserDetailsServiceImpl;
import com.parking.server.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

public class Test {

    public static void main(String[] args)
    {
        //CacheClient client = new CacheClient();

        //client.getAll();

        /*for(Pojo p: client.getAll())
        {
            System.out.println(p.getName()+"  "+p.getValues());
        }*/

        /*IUserService userService = new UserServiceImpl();
        Pojo p = userService.getUser("mail@yandex.ru");
        System.out.println(p.getName());*/

        UserDetailsService userDetailsService = new UserDetailsServiceImpl();

        userDetailsService.loadUserByUsername("mail@yandex.ru");
    }
}
