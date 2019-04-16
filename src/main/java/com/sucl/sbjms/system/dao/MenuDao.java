package com.sucl.sbjms.system.dao;

import com.sucl.sbjms.system.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author sucl
 * @date 2019/4/1
 */
//@RepositoryRestResource(path="user")
@Repository
public interface MenuDao extends JpaRepository<Menu,Serializable>,JpaSpecificationExecutor<Menu>,
        org.springframework.data.repository.Repository<Menu,Serializable>{

    @Query(value = "select * from menu LEFT JOIN role_menu rm on rm.menu_code = menu.menu_code where rm.role_code in(?1) GROUP  BY menu.menu_code",nativeQuery = true)
    List<Menu> getMenusByRoleids(List<String> roleIds);
}
