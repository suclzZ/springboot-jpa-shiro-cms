package com.sucl.sbjms.content.dao;

import com.sucl.sbjms.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author sucl
 * @date 2019/4/1
 */
@Repository
public interface ContentDao extends JpaRepository<Content,Serializable>,JpaSpecificationExecutor<Content>,
        org.springframework.data.repository.Repository<Content,Serializable>{
}
