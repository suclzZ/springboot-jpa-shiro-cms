package com.sucl.sbjms.security.realm;

import com.sucl.sbjms.security.IUser;
import com.sucl.sbjms.security.service.PrincipalService;
import com.sucl.sbjms.security.service.TokenCheckService;
import com.sucl.sbjms.security.token.UserToken;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 对于同一张表我们可以通过多realm实现不同身份（用户名、邮箱、手机）登录
 * 对于不同表建议使用多token
 * @author sucl
 * @date 2019/4/12
 */
@Slf4j
@NoArgsConstructor
public class DefaultRealm extends AuthorizingRealm {

    @Autowired(required = false)
    private PrincipalService principalService;
    @Autowired(required = false)
    private TokenCheckService tokenCheckService;

    public DefaultRealm(PrincipalService principalService,TokenCheckService tokenCheckService){
        this.principalService = principalService;
        this.tokenCheckService = tokenCheckService;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Object principal = token.getPrincipal();
        if(principalService != null){
            if(token instanceof UserToken){
                if(tokenCheckService != null){
                    tokenCheckService.doCheck((UserToken) token);
                }
                return principalService.getGenericAccount((UserToken)token);
            }
        }
        return null;
    }

    /**
     * 鉴权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Object principal = principals.getPrimaryPrincipal();
        if(principal instanceof IUser){
            return principalService.getGenericAccount((IUser) principal);
        }
        return null;
    }
}
