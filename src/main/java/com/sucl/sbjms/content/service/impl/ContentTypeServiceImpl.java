package com.sucl.sbjms.content.service.impl;

import com.sucl.sbjms.content.dao.ContentTypeDao;
import com.sucl.sbjms.content.entity.ContentType;
import com.sucl.sbjms.content.service.ContentTypeService;
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
public class ContentTypeServiceImpl extends BaseServiceImpl<ContentTypeDao,ContentType> implements ContentTypeService {
    @Override
    public Class<ContentType> getDomainClazz() {
        return ContentType.class;
    }
}
