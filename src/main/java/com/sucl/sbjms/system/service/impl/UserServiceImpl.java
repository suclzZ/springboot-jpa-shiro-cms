package com.sucl.sbjms.system.service.impl;

import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.orm.jpa.JpaCondition;
import com.sucl.sbjms.core.service.impl.BaseServiceImpl;
import com.sucl.sbjms.security.auth.GenericAccount;
import com.sucl.sbjms.security.auth.IUser;
import com.sucl.sbjms.security.annotation.Principal;
import com.sucl.sbjms.security.auth.PrincipalAdapter;
import com.sucl.sbjms.security.token.DefaultToken;
import com.sucl.sbjms.security.token.UserToken;
import com.sucl.sbjms.system.dao.UserDao;
import com.sucl.sbjms.system.entity.User;
import com.sucl.sbjms.system.entity.UserAccount;
import com.sucl.sbjms.system.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        User user = this.attemptQueryUser(defaultToken.getUsername());
        if(user == null){
            user = dao.findByUsername(defaultToken.getUsername());
        }
        if(user==null){
            throw new UnsupportedTokenException();
        }
        return new UserAccount(user);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 对自定义的多条件查询用户
     * @see Principal
     * @param princinal
     * @return
     */
    private User attemptQueryUser(String princinal){
        ConditionFieldCallback<User> callback = new ConditionFieldCallback<>(new User(),princinal);
        ReflectionUtils.doWithFields(User.class, callback);
        List<Condition> conditions = callback.getConditions();
        if(conditions!=null && conditions.size()>0){
            for(Condition condition : conditions){
                User user = getOne(condition.getProperty(),condition.getValue());
                if(user!=null){
                    return user;
                }
            }
        }
        return null;
    }

    private class ConditionFieldCallback<T> implements ReflectionUtils.FieldCallback{
        private List<OrderExample> orderExamples = new ArrayList<>();
        private Object value;
        private T t;

        public ConditionFieldCallback(T t, Object value){
            this.value = value;
            this.t = t;
        }

        public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException{
           Principal principal = field.getAnnotation(Principal.class);
           if(principal!=null){
               if(principal.matcher()){
//                   ExampleMatcher exampleMatcher = ExampleMatcher.matching();
//                   field.setAccessible(true);
//                   field.set(t,value);
//                   Example<T> example = Example.of(t,exampleMatcher.withMatcher(field.getName(),matcher->matcher.exact()));

                   orderExamples.add(new OrderExample(principal.order(),new JpaCondition(field.getName(),value)));
               }
           }
        }

        public List<Example> getExamples(){
            orderExamples.sort(Comparator.comparing(OrderExample::getOrder));
            return orderExamples.stream().map(oe->oe.getExample()).collect(Collectors.toList());
        }

        public List<Condition> getConditions(){
            orderExamples.sort(Comparator.comparing(OrderExample::getOrder));
            return orderExamples.stream().map(oe->oe.getCondition()).collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    private class OrderExample{
        private int order;
        private Example example;
        private Condition condition;

        public OrderExample(int order,Example example){
            this.order = order;
            this.example = example;
        }

        public OrderExample(int order,Condition condition){
            this.order = order;
            this.condition = condition;
        }
    }

}
