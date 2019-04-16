package com.sucl.sbjms.system.dao;

import com.sucl.sbjms.system.entity.Rperm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author sucl
 * @date 2019/4/1
 */
@Repository
public interface RpermDao extends JpaRepository<Rperm,Serializable>,JpaSpecificationExecutor<Rperm>,
        org.springframework.data.repository.Repository<Rperm,Serializable>{

}
