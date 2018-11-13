package com.caozhihu.tmall.service;

import com.caozhihu.tmall.pojo.User;

public interface UserService extends BaseService {
    boolean isExit(String name);

    User get(String name, String password);
}
