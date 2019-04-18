package com.sucl.sbjms.security.config;

import com.sucl.sbjms.security.dev.DevUserService;
import com.sucl.sbjms.security.realm.DefaultRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.AbstractShiroWebConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;

/**
 * 在shiro-spring中已经提供了ShiroWebFilterConfiguration、ShiroWebConfiguration将相关的配置都引入了，但是又很多都是默认配置
 * @author sucl
 * @date 2019/4/12
 */
@Configuration
//@ImportAutoConfiguration(classes = {ShiroWebFilterConfiguration.class,ShiroWebConfiguration.class})
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroConfig extends AbstractShiroWebConfiguration {

    @Bean
    public Realm realm(){
        return new DefaultRealm();
    }

    @Bean
    @ConditionalOnProperty(value = "shior.dev",matchIfMissing = true)
    public DevUserService devUserService(){
        return new DevUserService();
    }

    @Bean
    public CredentialsMatcher credentialsMatcher(ShiroProperties shiroProperties){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        if(!StringUtils.isEmpty(shiroProperties.getHashAlgorithmName()))
            credentialsMatcher.setHashAlgorithmName(shiroProperties.getHashAlgorithmName());
        if(!StringUtils.isEmpty(shiroProperties.getHashiterations()))
            credentialsMatcher.setHashIterations(shiroProperties.getHashiterations());
        return credentialsMatcher;
    }
}
