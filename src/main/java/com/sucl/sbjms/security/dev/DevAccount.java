package com.sucl.sbjms.security.dev;

import com.sucl.sbjms.security.auth.AbstractGenericAccount;
import com.sucl.sbjms.security.auth.IUser;

public class DevAccount extends AbstractGenericAccount {
    public DevAccount(IUser principal, Object credentials) {
        super(principal, credentials);
    }
}
