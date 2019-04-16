package com.sucl.sbjms.security.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 用户名密码token
 * @author sucl
 * @date 2019/4/15
 */
public class PhoneToken extends UsernamePasswordToken implements UserToken{
    private String phone;
    private String code;

    public PhoneToken(String phone,String code){
        super(phone,code);
    }

}
