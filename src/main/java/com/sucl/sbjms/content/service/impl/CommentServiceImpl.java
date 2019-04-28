package com.sucl.sbjms.content.service.impl;

import com.sucl.sbjms.content.dao.CommentDao;
import com.sucl.sbjms.content.entity.Comment;
import com.sucl.sbjms.content.service.CommentService;
import com.sucl.sbjms.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Service
@Transactional
public class CommentServiceImpl extends BaseServiceImpl<CommentDao,Comment> implements CommentService {
    @Override
    public Class<Comment> getDomainClazz() {
        return Comment.class;
    }
}
