package com.sucl.sbjms.system.web;

import com.sucl.sbjms.core.web.BaseController;
import com.sucl.sbjms.system.entity.Menu;
import com.sucl.sbjms.system.entity.Role;
import com.sucl.sbjms.system.entity.Rperm;
import com.sucl.sbjms.system.entity.User;
import com.sucl.sbjms.system.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sucl
 * @date 2019/4/3
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<RoleService,Role> {

    @PostMapping("/authc")
    public void menuAuthc(Role role){
        List<Menu> menus = role.getMenus();
        role = service.getById(role.getRoleCode());
        role.setMenus(menus);
        service.save(role);
    }

    @GetMapping("/perm/{roleCode}")
    public List<Rperm> getRperm(@PathVariable String roleCode){
        Role role = service.getInitializeObject(roleCode, new String[]{"perms"});
        return role.getPerms();
    }

}
