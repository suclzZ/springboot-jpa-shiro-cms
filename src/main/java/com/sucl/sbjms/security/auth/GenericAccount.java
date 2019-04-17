package com.sucl.sbjms.security.auth;

import org.apache.shiro.authc.Account;
import org.apache.shiro.authc.MergableAuthenticationInfo;
import org.apache.shiro.authc.SaltedAuthenticationInfo;

import java.io.Serializable;

/**
 * @author sucl
 * @date 2019/4/15
 */
public interface GenericAccount extends Account, MergableAuthenticationInfo, SaltedAuthenticationInfo,Serializable {

}
