package com.sucl.sbjms.security.util;

import com.sucl.sbjms.security.auth.IUser;
import org.apache.shiro.SecurityUtils;

import java.util.Set;

/**
 * @author sucl
 * @date 2019/4/16
 */
public class ShiroUtils {

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
}
