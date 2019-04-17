package com.sucl.sbjms.security.auth;

import org.apache.shiro.authz.Permission;

import java.util.Set;

/**
 * @author sucl
 * @date 2019/4/13
 */
public interface IUser {

    String getUsername();

    Set<String> getRoleIds();

    default Set<String> getStringPermissions(){
        return null;
    }

    default Set<Permission> getObjectPermissions(){
        return null;
    }
}
