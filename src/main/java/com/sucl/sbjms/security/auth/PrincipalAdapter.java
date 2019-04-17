package com.sucl.sbjms.security.auth;

import com.sucl.sbjms.security.auth.GenericAccount;
import com.sucl.sbjms.security.auth.IUser;
import com.sucl.sbjms.security.token.UserToken;
import org.springframework.core.PriorityOrdered;

/**
 * @author sucl
 * @date 2019/4/15
 */
public interface PrincipalAdapter<U extends IUser,UT extends UserToken> extends PriorityOrdered {

    boolean support(IUser user);

    GenericAccount getGenericAccount(IUser user);

    boolean support(com.sucl.sbjms.security.token.UserToken token);

    GenericAccount getGenericAccount(com.sucl.sbjms.security.token.UserToken token);
}
