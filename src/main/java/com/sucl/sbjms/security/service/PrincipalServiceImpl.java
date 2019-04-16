package com.sucl.sbjms.security.service;


import com.sucl.sbjms.security.GenericAccount;
import com.sucl.sbjms.security.IUser;
import com.sucl.sbjms.security.token.UserToken;
import org.apache.commons.collections.MapUtils;
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
        if(principalAdapters!=null){
            for(PrincipalAdapter principalAdapter : principalAdapters){
                if(principalAdapter.support(token)){
                    return principalAdapter.getGenericAccount(token);
                }
            }
        }
        return null;
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
