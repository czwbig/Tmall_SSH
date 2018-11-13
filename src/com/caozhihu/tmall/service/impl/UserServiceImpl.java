package com.caozhihu.tmall.service.impl;

import com.caozhihu.tmall.pojo.User;
import com.caozhihu.tmall.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {
    @Override
    public User get(String name, String password) {
        List<User> users = list("name", name, "password", password);
        if (users.isEmpty()){
            return null;
        }
        return users.get(0);
    }

    @Override
    public boolean isExit(String name) {
        List list = list("name", name);
        if (!list.isEmpty()) {
            return true;
        }
        return false;
    }
}
