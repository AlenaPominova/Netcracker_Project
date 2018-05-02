package com.parking.server.service;

import com.parking.server.objects.Pojo;

public interface IUserService {

    Pojo getUser(String login);//, String password);
}
