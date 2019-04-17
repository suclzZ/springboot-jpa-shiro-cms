package com.sucl.sbjms.security.token;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 用户名密码token
 * Token->PrincipalService->GenericAccount
 * @author sucl
 * @date 2019/4/15
 */
@Getter
@Setter
public class DefaultToken extends UsernamePasswordToken implements UserToken,VerifiedCodeAble {
    private boolean verification;

    public DefaultToken(String username,String password,boolean rememberMe,String host){
        super(username,password,rememberMe,host);
    }

    @Override
    public boolean verify() {
        return verification;
    }
}
