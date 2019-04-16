package com.sucl.sbjms.security.service;

import com.sucl.sbjms.security.token.UserToken;
import org.springframework.core.PriorityOrdered;

/**
 * token校验策略
 * @author sucl
 * @date 2019/4/16
 */
public interface TokenCheckStrategy extends PriorityOrdered {

    void doCheck(UserToken token);

}
