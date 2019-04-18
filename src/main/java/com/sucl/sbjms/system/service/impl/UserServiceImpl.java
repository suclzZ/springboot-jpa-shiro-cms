package com.sucl.sbjms.system.service.impl;

import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.service.impl.BaseServiceImpl;
import com.sucl.sbjms.security.auth.GenericAccount;
import com.sucl.sbjms.security.auth.IUser;
import com.sucl.sbjms.security.annotation.Principal;
import com.sucl.sbjms.security.auth.PrincipalAdapter;
import com.sucl.sbjms.security.util.AbstractPrincipalHelper;
import com.sucl.sbjms.security.token.DefaultToken;
import com.sucl.sbjms.security.token.UserToken;
import com.sucl.sbjms.system.dao.UserDao;
import com.sucl.sbjms.system.entity.User;
import com.sucl.sbjms.system.entity.UserAccount;
import com.sucl.sbjms.system.service.UserService;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<UserDao,User> implements UserService ,PrincipalAdapter {
    @Override
    protected Class<User> getDomainClazz() {
        return User.class;
    }

    private GenericAccount getGenericAccountByUser(User user) {
        return new UserAccount(user);
    }


    @Override
    public User findByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Override
    public User saveOrUpdate(User user) {
        boolean update = !StringUtils.isEmpty(user.getUserId());
        if(update){
            User temp = getById(user.getUserId());
            user.setPassword(temp.getPassword());
        }
        return super.save(user);
    }

    @Override
    public boolean support(IUser user) {
        return user instanceof User;
    }

    @Override
    public GenericAccount getGenericAccount(IUser user) {
        return getGenericAccountByUser((User)user);
    }

    @Override
    public boolean support(UserToken token) {
        return token instanceof DefaultToken;
    }

    @Override
    public GenericAccount getGenericAccount(UserToken token) {
        DefaultToken defaultToken = (DefaultToken) token;
        IUser user = this.queryPrincipal(defaultToken.getUsername(), this);
        if(user == null){
            user = dao.findByUsername(defaultToken.getUsername());
        }
        if(user==null){
            throw new UnsupportedTokenException();
        }
        return new UserAccount((User) user);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
