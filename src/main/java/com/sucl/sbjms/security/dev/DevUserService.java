package com.sucl.sbjms.security.dev;

import com.sucl.sbjms.security.Constant;
import com.sucl.sbjms.security.auth.GenericAccount;
import com.sucl.sbjms.security.auth.IUser;
import com.sucl.sbjms.security.auth.PrincipalAdapter;
import com.sucl.sbjms.security.token.DefaultToken;
import com.sucl.sbjms.security.token.UserToken;
import com.sucl.sbjms.security.util.ShiroUtils;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class DevUserService implements PrincipalAdapter {
    @Resource
    private PasswordService passwordService;

    @Override
    public boolean support(IUser user) {
        return true;
    }

    @Override
    public GenericAccount getGenericAccount(IUser user) {
        return new DevAccount(user,Constant.DEFAULT_PASSWORD);
    }

    @Override
    public boolean support(UserToken token) {
        return token instanceof DefaultToken;
    }

    @Override
    public GenericAccount getGenericAccount(UserToken token) {
        String username = token.getUsername();
        if(username!=null && username.startsWith("dev-")){
            return new DevAccount(new DevUser(username),passwordService.encryptPassword(Constant.DEFAULT_PASSWORD));
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
