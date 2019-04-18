package com.sucl.sbjms.security.util;

import com.sucl.sbjms.security.auth.IUser;
import com.sucl.sbjms.security.dev.DevUser;
import org.apache.shiro.SecurityUtils;

import java.util.Set;

/**
 * @author sucl
 * @date 2019/4/16
 */
public abstract class ShiroUtils extends SecurityUtils{

    public static IUser getUser(){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if(principal instanceof IUser){
            return (IUser) principal;
        }
        return new IUser() {
            @Override
            public String getUsername() {
                return principal.toString();
            }

            @Override
            public Set<String> getRoleIds() {
                return null;
            }
        };
    }

    public static boolean isAdmin() {
        return "admin".equals(getUser().getUsername());
    }

    public static boolean isDev() {
        return getUser() instanceof DevUser;
    }
}
