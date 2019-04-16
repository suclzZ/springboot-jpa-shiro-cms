package com.sucl.sbjms.security.permission;

import org.apache.shiro.SecurityUtils;

/**
 * @author sucl
 * @date 2019/4/16
 */
public class ShiroTag {

    public static boolean hasPermission(String permissionStr){
        return SecurityUtils.getSubject().isPermitted(permissionStr);
    }
}
