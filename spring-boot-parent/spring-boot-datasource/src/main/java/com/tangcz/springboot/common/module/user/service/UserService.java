package com.tangcz.springboot.common.module.user.service;

import com.tangcz.springboot.common.module.user.pojo.UserDO;

/**
 * ClassName:UserService
 * Package:com.tangcz.springboot.common.module.user.service
 * Description:
 *
 * @date:2020/6/6 19:54
 * @author:tangchengzao
 */
public interface UserService {

    UserDO findById(long id);

}
