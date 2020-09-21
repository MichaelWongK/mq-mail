package com.michealwang.mqmail.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.michealwang.mqmail.common.annotation.ApiIdempotent;
import com.michealwang.mqmail.common.json.JSONResponse;
import com.michealwang.mqmail.platform.pojo.User;
import com.michealwang.mqmail.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("users")
    public String getAll() {
        List<User> users = userService.getAll();
        return users.toString();
    }

    @GetMapping("{id}")
    public String getOne(@PathVariable Integer id) {
        User user = userService.getOne(id);
        if (null != user)
            return user.toString();
        return "user not exits!";
    }

    @PostMapping("add")
    public String add(User user) {
        userService.add(user);
        return "nice";
    }

    @PutMapping
    public String update(User user) {
        userService.update(user);
        return "nice";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id) {
        userService.delete(id);
        return "nice";
    }

    @GetMapping("users/username_password")
    public String getByUsernameAndPassword(String username, String password) {
        User user = userService.getByUsernameAndPassword(username, password);
        return user.toString();
    }

    @PostMapping("login")
    public JSONResponse login(String username, String password) {
        return userService.login(username, password);
    }


}
