package com.sucl.sbjms.security.service;

import com.sucl.sbjms.security.token.UserToken;

/**
 * @author sucl
 * @date 2019/4/16
 */
public interface TokenCheckService {

    boolean doCheck(UserToken token);
}
