package com.sucl.sbjms.system.dao;

import com.sucl.sbjms.system.entity.Codegroup;
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
public interface CodegroupDao extends JpaRepository<Codegroup,Serializable>,JpaSpecificationExecutor<Codegroup>,
        org.springframework.data.repository.Repository<Codegroup,Serializable>{
}
