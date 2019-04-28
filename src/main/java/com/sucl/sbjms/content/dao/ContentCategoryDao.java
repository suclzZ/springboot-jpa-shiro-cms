package com.sucl.sbjms.content.dao;

import com.sucl.sbjms.content.entity.ContentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author sucl
 * @date 2019/4/1
 */
@Repository
public interface ContentCategoryDao extends JpaRepository<ContentCategory,Serializable>,JpaSpecificationExecutor<ContentCategory>,
        org.springframework.data.repository.Repository<ContentCategory,Serializable>{
}
