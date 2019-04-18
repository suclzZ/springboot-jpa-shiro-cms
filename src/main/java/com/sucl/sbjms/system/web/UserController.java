package com.sucl.sbjms.system.web;

import com.sucl.sbjms.core.rem.BusException;
import com.sucl.sbjms.core.web.BaseController;
import com.sucl.sbjms.security.auth.IUser;
import com.sucl.sbjms.system.entity.Role;
import com.sucl.sbjms.system.entity.User;
import com.sucl.sbjms.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sucl
 * @date 2019/4/3
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<UserService,User> {
    @Resource
    private PasswordService passwordService;

    @GetMapping("/current")
    public User currentUser(){
        IUser user = (IUser) SecurityUtils.getSubject().getPrincipal();
        return service.findByUsername(user.getUsername());
    }

    @PostMapping("/authc")
    public void authc(User user){
        List<Role> roles = user.getRoles();
        user = service.getById(user.getUserId());
        user.setRoles(roles);
        service.save(user);
    }

    @PostMapping("/resetpasswd")
    public void resetpasswd(@RequestParam String userId,
                            @RequestParam  String oldPassword,
                            @RequestParam String newPassword,
                            @RequestParam String rePassword){
        User user = null;
        if(!StringUtils.isEmpty(userId)){
            user = service.getById(userId);
        }
        if(user==null){
            throw new BusException(String.format("通过用户id：%s没有找到对应用户！",userId));
        }
        if(!StringUtils.isEmpty(oldPassword) && !StringUtils.isEmpty(newPassword) && !StringUtils.isEmpty(rePassword)){
            boolean match = match = user.getPassword().equals(passwordService.encryptPassword(oldPassword));
            if(!match){
                throw new BusException("旧密码不正确 ！");
            }
            if(newPassword.equals(rePassword)){
                user.setPassword(passwordService.encryptPassword(newPassword));
                service.save(user);
            }else{
                throw new BusException("两次密码不相同！");
            }
        }
    }
}
