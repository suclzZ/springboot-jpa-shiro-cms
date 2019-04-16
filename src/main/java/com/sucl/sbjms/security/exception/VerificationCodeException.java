package com.sucl.sbjms.security.exception;

import lombok.NoArgsConstructor;
import org.apache.shiro.authc.AuthenticationException;

/**
 * @author sucl
 * @date 2019/4/16
 */
public class VerificationCodeException extends AuthenticationException {

    public VerificationCodeException(){
        super("验证码错误！");
    }
}
