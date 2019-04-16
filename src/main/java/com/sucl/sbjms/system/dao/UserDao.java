package com.sucl.sbjms.system.dao;

import com.sucl.sbjms.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author sucl
 * @date 2019/4/1
 */
//@RepositoryRestResource(path="user")
@Repository
public interface UserDao extends JpaRepository<User,Serializable>,JpaSpecificationExecutor<User>,
        org.springframework.data.repository.Repository<User,Serializable>{

    User findByUsername(String username);
}
