package org.eason.service;

import org.eason.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Eason
 * @date 2022/11/3
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public String getUser() {
        return userMapper.getAll();
    }
}
