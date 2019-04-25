package com.sucl.sbjms.system.service.impl;

import com.sucl.sbjms.core.service.impl.BaseServiceImpl;
import com.sucl.sbjms.system.dao.CodegroupDao;
import com.sucl.sbjms.system.dao.UserDao;
import com.sucl.sbjms.system.entity.Codegroup;
import com.sucl.sbjms.system.entity.User;
import com.sucl.sbjms.system.service.CodegroupService;
import com.sucl.sbjms.system.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Service
@Transactional
public class CodegroupServiceImpl extends BaseServiceImpl<CodegroupDao,Codegroup> implements CodegroupService {
    @Override
    public Class<Codegroup> getDomainClazz() {
        return Codegroup.class;
    }
}
