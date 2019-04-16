package com.sucl.sbjms.system.service.impl;

import com.sucl.sbjms.core.service.impl.BaseServiceImpl;
import com.sucl.sbjms.system.dao.CodeitemDao;
import com.sucl.sbjms.system.entity.Codeitem;
import com.sucl.sbjms.system.service.CodeitemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Service
@Transactional
public class CodeitemServiceImpl extends BaseServiceImpl<CodeitemDao,Codeitem> implements CodeitemService {
    @Override
    protected Class<Codeitem> getDomainClazz() {
        return Codeitem.class;
    }
}
