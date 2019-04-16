package com.sucl.sbjms.security.config;

import com.sucl.sbjms.security.realm.DefaultRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.AbstractShiroWebConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collection;

/**
 * 在shiro-spring中已经提供了ShiroWebFilterConfiguration、ShiroWebConfiguration将相关的配置都引入了，但是又很多都是默认配置
 * @author sucl
 * @date 2019/4/12
 */
@Configuration
//@ImportAutoConfiguration(classes = {ShiroWebFilterConfiguration.class,ShiroWebConfiguration.class})
public class ShiroConfig extends AbstractShiroWebConfiguration {

    @Bean
    public Realm realm(){
        return new DefaultRealm();
    }

}
