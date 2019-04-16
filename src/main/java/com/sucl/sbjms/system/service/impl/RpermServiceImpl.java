package com.sucl.sbjms.system.service.impl;

import com.sucl.sbjms.core.service.impl.BaseServiceImpl;
import com.sucl.sbjms.system.dao.RoleDao;
import com.sucl.sbjms.system.dao.RpermDao;
import com.sucl.sbjms.system.entity.Role;
import com.sucl.sbjms.system.entity.Rperm;
import com.sucl.sbjms.system.service.RoleService;
import com.sucl.sbjms.system.service.RpermService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Service
@Transactional
public class RpermServiceImpl extends BaseServiceImpl<RpermDao,Rperm> implements RpermService {
    @Override
    protected Class<Rperm> getDomainClazz() {
        return Rperm.class;
    }
}
