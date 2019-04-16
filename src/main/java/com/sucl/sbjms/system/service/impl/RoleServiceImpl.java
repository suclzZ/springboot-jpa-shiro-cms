package com.sucl.sbjms.system.service.impl;

import com.sucl.sbjms.core.service.impl.BaseServiceImpl;
import com.sucl.sbjms.system.dao.RoleDao;
import com.sucl.sbjms.system.dao.UserDao;
import com.sucl.sbjms.system.entity.Role;
import com.sucl.sbjms.system.entity.User;
import com.sucl.sbjms.system.service.RoleService;
import com.sucl.sbjms.system.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<RoleDao,Role> implements RoleService {
    @Override
    protected Class<Role> getDomainClazz() {
        return Role.class;
    }
}
