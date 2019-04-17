package com.sucl.sbjms.system.entity;

import com.sucl.sbjms.security.auth.AbstractGenericAccount;

/**
 * @author sucl
 * @date 2019/4/16
 */
public class UserAccount extends AbstractGenericAccount {

    public UserAccount(User principal) {
        super(principal, principal.getPassword());
    }
}
