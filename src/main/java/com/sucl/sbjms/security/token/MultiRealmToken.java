package com.sucl.sbjms.security.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 多realm token
 * @author sucl
 * @date 2019/4/15
 */
public class MultiRealmToken extends UsernamePasswordToken {

    private String tokenType;
}
