package com.sucl.sbjms.system.service;

import com.sucl.sbjms.core.service.BaseService;
import com.sucl.sbjms.system.dao.UserDao;
import com.sucl.sbjms.system.entity.User;

/**
 * @author sucl
 * @date 2019/4/2
 */
public interface UserService extends BaseService<UserDao,User>{

    User findByUsername(String username);
}
