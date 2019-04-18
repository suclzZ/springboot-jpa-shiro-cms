package com.sucl.sbjms.security.auth;

import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.service.BaseService;
import com.sucl.sbjms.security.token.UserToken;
import com.sucl.sbjms.security.util.AbstractPrincipalHelper;
import com.sucl.sbjms.system.entity.User;
import org.springframework.core.PriorityOrdered;

import java.util.List;

/**
 * @author sucl
 * @date 2019/4/15
 */
public interface PrincipalAdapter<U extends IUser,UT extends UserToken> extends PriorityOrdered {

    boolean support(IUser user);

    GenericAccount getGenericAccount(IUser user);

    boolean support(UserToken token);

    GenericAccount getGenericAccount(UserToken token);

    /**
     * 对自定义的多条件查询用户
     * @param princinal
     * @param baseService
     * @return
     */
    default IUser queryPrincipal(String princinal, BaseService baseService){
        List<Condition> conditions = AbstractPrincipalHelper.princinalCondition(princinal);
        if(conditions!=null && conditions.size()>0){
            for(Condition condition : conditions){
                Object user = null;
                try {
                    user = baseService.getOne(condition.getProperty(), condition.getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(user!=null && user instanceof IUser){
                    return (IUser) user;
                }
            }
        }
        return null;
    }
}
