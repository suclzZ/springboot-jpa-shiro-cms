package com.sucl.sbjms.content.service.impl;

import com.sucl.sbjms.content.dao.ContentCategoryDao;
import com.sucl.sbjms.content.entity.ContentCategory;
import com.sucl.sbjms.content.service.ContentCategoryService;
import com.sucl.sbjms.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Service
@Transactional
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategoryDao,ContentCategory> implements ContentCategoryService {
    @Override
    public Class<ContentCategory> getDomainClazz() {
        return ContentCategory.class;
    }
}
