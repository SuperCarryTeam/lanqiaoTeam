package org.lanqiao.service;

import org.lanqiao.bean.user;

import org.lanqiao.dao.userDao;

public class userService {
    public static user userService(){
        userDao dao = new userDao();
        user u = new user();
        return u;
    }
}
