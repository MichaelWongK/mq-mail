package com.michealwang.mqmail.platform.service;


import com.michealwang.mqmail.common.json.JSONResponse;
import com.michealwang.mqmail.platform.pojo.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getOne(Integer id);

    void add(User user);

    void update(User user);

    void delete(Integer id);

    User getByUsernameAndPassword(String username, String password);

    JSONResponse login(String username, String password);

}
