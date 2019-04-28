package com.sucl.sbjms.content.dao;

import com.sucl.sbjms.content.entity.Comment;
import com.sucl.sbjms.content.entity.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author sucl
 * @date 2019/4/1
 */
@Repository
public interface ContentTypeDao extends JpaRepository<ContentType,Serializable>,JpaSpecificationExecutor<ContentType>,
        org.springframework.data.repository.Repository<ContentType,Serializable>{
}
