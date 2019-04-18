package com.sucl.sbjms.security.service;


import com.sucl.sbjms.security.auth.GenericAccount;
import com.sucl.sbjms.security.auth.IUser;
import com.sucl.sbjms.security.auth.PrincipalAdapter;
import com.sucl.sbjms.security.token.UserToken;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author sucl
 * @date 2019/4/13
 */
@Service
public class PrincipalServiceImpl implements PrincipalService, ApplicationContextAware {
    private List<PrincipalAdapter> principalAdapters;

    @Override
    public GenericAccount getGenericAccount(IUser user) {
        if(principalAdapters!=null){
            for(PrincipalAdapter principalAdapter : principalAdapters){
                if(principalAdapter.support(user)){
                    return principalAdapter.getGenericAccount(user);
                }
            }
        }
        return null;
    }

    @Override
    public GenericAccount getGenericAccount(UserToken token) {
        GenericAccount account = null;
        if(principalAdapters!=null){
            for(PrincipalAdapter principalAdapter : principalAdapters){
                if(principalAdapter.support(token)){
                    account = principalAdapter.getGenericAccount(token);
                    if(account!=null){
                        return account;
                    }
                }
            }
        }
        throw new UnsupportedTokenException();
    }

    @Override
    public PrincipalAdapter getUserAdapter(String paramString) {
        return null;
    }

    @Override
    public boolean modifyPassword(IUser user, String pldPw, String newPw, String rePw) {
        return false;
    }

    @Override
    public boolean resetPassword(IUser user) {
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, PrincipalAdapter> principalAdapter = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, PrincipalAdapter.class, true, false);
        if(MapUtils.isNotEmpty(principalAdapter)){
            principalAdapters = new ArrayList<PrincipalAdapter>(principalAdapter.values());
            principalAdapters.sort(OrderComparator.INSTANCE);
        }
    }
}
