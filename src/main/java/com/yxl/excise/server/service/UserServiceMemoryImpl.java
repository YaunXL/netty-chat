package com.yxl.excise.server.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserServiceMemoryImpl implements UserService{

    private static Map<String,String> initUser = new ConcurrentHashMap<>();

    static {
        initUser.put("zhangsan","123");
        initUser.put("lisi","123");
        initUser.put("wangwu","123");
        initUser.put("zhaoliu","123");
    }

    @Override
    public boolean login(String username, String password) {
        String pwd = initUser.get(username);
        if(pwd!=null){
            return pwd.equals(password);
        }
        return false;
    }
}
