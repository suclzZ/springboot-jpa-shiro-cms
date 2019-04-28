package com.sucl.sbjms.content.service.impl;

import com.sucl.sbjms.content.dao.ContentDao;
import com.sucl.sbjms.content.entity.Content;
import com.sucl.sbjms.content.service.ContentService;
import com.sucl.sbjms.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Service
@Transactional
public class ContentServiceImpl extends BaseServiceImpl<ContentDao,Content> implements ContentService {
    @Override
    public Class<Content> getDomainClazz() {
        return Content.class;
    }
}
