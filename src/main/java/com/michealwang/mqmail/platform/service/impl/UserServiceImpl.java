package com.michealwang.mqmail.platform.service.impl;

import com.michealwang.mqmail.common.json.JSONResponse;
import com.michealwang.mqmail.common.json.ResponseCode;
import com.michealwang.mqmail.common.util.StringRedisUtils;
import com.michealwang.mqmail.config.exception.ServiceException;
import com.michealwang.mqmail.platform.mapper.UserMapper;
import com.michealwang.mqmail.platform.pojo.User;
import com.michealwang.mqmail.platform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisUtils stringRedisUtils;

    @Override
    public List<User> getAll() {
        return userMapper.selectAll();
    }

    @Override
    public User getOne(Integer id) {
        return userMapper.selectOne(id);
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }

    @Override
    public List<User> getByUsernameAndPassword(String username, String password) {
        return userMapper.selectByUsernameAndPassword(username, password);
    }

    @Override
    public JSONResponse testIdempotence(String token) {
        Assert.notNull(token, ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        Assert.isTrue(stringRedisUtils.hasKey(token), ResponseCode.REPETITIVE_OPERATION.getMsg());
        stringRedisUtils.delete(token);
        return JSONResponse.success("操作成功");
    }

}
