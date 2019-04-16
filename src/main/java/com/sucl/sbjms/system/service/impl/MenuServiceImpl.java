package com.sucl.sbjms.system.service.impl;

import com.sucl.sbjms.core.service.impl.BaseServiceImpl;
import com.sucl.sbjms.security.util.ShiroUtils;
import com.sucl.sbjms.system.dao.MenuDao;
import com.sucl.sbjms.system.entity.Menu;
import com.sucl.sbjms.system.entity.Role;
import com.sucl.sbjms.system.entity.User;
import com.sucl.sbjms.system.service.MenuService;
import com.sucl.sbjms.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Service
@Transactional
public class MenuServiceImpl extends BaseServiceImpl<MenuDao,Menu> implements MenuService {
    @Autowired
    private UserService userService;

    @Override
    protected Class<Menu> getDomainClazz() {
        return Menu.class;
    }

    @Override
    public List<Menu> getMenusByUsername(String username) {
        if(username==null){
            //获取当前用户
            username = ShiroUtils.getUser().getUsername();
        }
        //用户获取角色
        User user = userService.getOne("username", username);
        List<Role> roles = user.getRoles();
        //角色获取菜单
        List<String> roleIds = roles.stream().map(r->{
            return r.getRoleCode();
        }).collect(Collectors.toList());
        if(roleIds!=null && roleIds.size()>0){
            return getMenusByRoleids(roleIds);
        }
        return null;
    }

    @Override
    public List<Menu> getMenusByRoleids(List<String> roleIds) {
        return dao.getMenusByRoleids(roleIds);
    }
}
