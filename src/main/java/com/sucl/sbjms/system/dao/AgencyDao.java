package com.sucl.sbjms.system.dao;

import com.sucl.sbjms.system.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author sucl
 * @date 2019/4/1
 */
//@RepositoryRestResource(path="user")
@Repository
public interface AgencyDao extends JpaRepository<Agency,Serializable>,JpaSpecificationExecutor<Agency>,
        org.springframework.data.repository.Repository<Agency,Serializable>{
}
