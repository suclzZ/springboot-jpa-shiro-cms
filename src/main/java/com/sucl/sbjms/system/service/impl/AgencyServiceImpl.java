package com.sucl.sbjms.system.service.impl;

import com.sucl.sbjms.core.service.impl.BaseServiceImpl;
import com.sucl.sbjms.system.dao.AgencyDao;
import com.sucl.sbjms.system.entity.Agency;
import com.sucl.sbjms.system.service.AgencyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Service
@Transactional
public class AgencyServiceImpl extends BaseServiceImpl<AgencyDao,Agency> implements AgencyService {
    @Override
    protected Class<Agency> getDomainClazz() {
        return Agency.class;
    }
}
