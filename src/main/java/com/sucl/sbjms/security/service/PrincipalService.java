package com.sucl.sbjms.security.service;


import com.sucl.sbjms.security.auth.GenericAccount;
import com.sucl.sbjms.security.auth.IUser;
import com.sucl.sbjms.security.auth.PrincipalAdapter;
import com.sucl.sbjms.security.token.UserToken;

/**
 * @author sucl
 * @date 2019/4/13
 */
public interface PrincipalService{

    GenericAccount getGenericAccount(IUser user);

    GenericAccount getGenericAccount(UserToken token);

    PrincipalAdapter getUserAdapter(String paramString);

    boolean modifyPassword(IUser user, String pldPw, String newPw, String rePw);

    boolean resetPassword(IUser user);
}
