package com.sucl.sbjms.system.service;

import com.sucl.sbjms.core.service.BaseService;
import com.sucl.sbjms.system.dao.MenuDao;
import com.sucl.sbjms.system.entity.Menu;

import java.util.List;

/**
 * @author sucl
 * @date 2019/4/2
 */
public interface MenuService extends BaseService<MenuDao,Menu>{

    /**
     * 根据用户名获取角色
     * @param username
     */
    List<Menu> getMenusByUsername(String username);

    /**
     * 根据角色id获取菜单
     * @param roleIds
     * @return
     */
    List<Menu> getMenusByRoleids(List<String> roleIds);
}
