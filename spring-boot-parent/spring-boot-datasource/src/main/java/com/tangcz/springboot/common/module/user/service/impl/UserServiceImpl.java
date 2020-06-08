package com.tangcz.springboot.common.module.user.service.impl;

import com.tangcz.springboot.common.module.user.dao.UserMapper;
import com.tangcz.springboot.common.module.user.pojo.UserDO;
import com.tangcz.springboot.common.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName:UserServiceImpl
 * Package:com.tangcz.springboot.common.module.user.service.impl
 * Description:
 *
 * @date:2020/6/6 19:54
 * @author:tangchengzao
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDO findById(long id) {
        return userMapper.selectById(id);
    }
}
