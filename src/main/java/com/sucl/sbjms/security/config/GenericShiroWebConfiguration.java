package com.sucl.sbjms.security.config;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.*;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.config.AbstractShiroWebConfiguration;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.OrderComparator;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 参考ShiroWebConfiguration，同时将ShiroAutoConfiguration已有的以及需要重构的留下了
 * @author sucl
 * @date 2019/4/12
 */
@Configuration
@ConditionalOnProperty(name = "shiro.enabled", matchIfMissing = true)
public class GenericShiroWebConfiguration extends AbstractShiroWebConfiguration {

    @Bean
    @Override
    protected AuthenticationStrategy authenticationStrategy() {
        return super.authenticationStrategy();
    }

    @Bean
    @Override
    protected Authenticator authenticator() {
        return super.authenticator();
    }

    @Bean
    @Override
    protected Authorizer authorizer() {
        return super.authorizer();
    }

    @Bean
    @Override
    protected SubjectDAO subjectDAO() {
        return super.subjectDAO();
    }

    @Bean
    @Override
    protected SessionStorageEvaluator sessionStorageEvaluator() {
        return super.sessionStorageEvaluator();
    }

    @Bean
    @Override
    protected SubjectFactory subjectFactory() {
        return super.subjectFactory();
    }

    @Bean
    @Override
    protected SessionFactory sessionFactory() {
        return super.sessionFactory();
    }

    @Bean
    @Override
    protected SessionDAO sessionDAO() {
        return super.sessionDAO();
    }

    @Bean
    @Override
    protected SessionsSecurityManager securityManager(List<Realm> realms) {
        realms.sort(OrderComparator.INSTANCE);
        return super.securityManager(realms);
    }

    @Bean(name = "sessionCookieTemplate")
    @Override
    protected Cookie sessionCookieTemplate() {
        return super.sessionCookieTemplate();
    }

    @Bean(name = "rememberMeCookieTemplate")
    @Override
    protected Cookie rememberMeCookieTemplate() {
        return super.rememberMeCookieTemplate();
    }

    @Bean
    @Override
    protected RememberMeManager rememberMeManager() {
        CookieRememberMeManager rememberMeManager = (CookieRememberMeManager) super.rememberMeManager();
        //设置cookie加密，毕竟cookie是可见的
        //这个值具体是什么不重要，因为系统只要能验证，你不用知道是什么
        rememberMeManager.setCipherKey(Base64.decode("wyLZMDifwq3sW1vhhHpgKA=="));
        return rememberMeManager;
    }

    /**
     * session管理
     * 可以使用ServletContainerSessionManager( nativeSessionManager() )
     * 但是DefaultWebSessionManager更容易扩展，比如通过redis实现session分布式管理
     * @return
     */
    @Bean
    @Override
    protected SessionManager sessionManager() {
        SessionManager sessionManager = super.sessionManager();
        if(sessionManager instanceof DefaultWebSessionManager){
            DefaultWebSessionManager webSessionManager = (DefaultWebSessionManager) sessionManager;
            configureSessionManager(webSessionManager);
        }
        return sessionManager;
    }

    /**
     * 加入一些扩展
     * 比如sessionDao
     * @param webSessionManager
     */
    protected void configureSessionManager(DefaultWebSessionManager webSessionManager) {

    }

    @Bean
    @Override
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        Map<String, String> filterChainMap = new LinkedHashMap<>();
//        filterChainMap.put("/**", DefaultFilter.anon.name());

        filterChainMap.put("/login.html","formAuthc");
        filterChainMap.put("/static/**", DefaultFilter.anon.name());
        filterChainMap.put("/captcha.jpg", DefaultFilter.anon.name());
        filterChainMap.put("/logout", DefaultFilter.logout.name());
        filterChainMap.put("/**", DefaultFilter.authc.name());
        chainDefinition.addPathDefinitions(filterChainMap);
        return chainDefinition;
    }

    @Bean
    public CacheManager cacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:encache.xml");
        return cacheManager;
    }
}
