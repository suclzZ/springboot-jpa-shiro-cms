package com.sucl.sbjms.security.config;

import com.sucl.sbjms.security.filter.DefaultFormAuthenticationFilter;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.AbstractShiroWebFilterConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiroFilter
 * @author sucl
 * @date 2019/4/12
 */
@Configuration
@ConditionalOnProperty(name = "shiro.enabled", matchIfMissing = true)
public class GenericShiroWebFilterConfiguration extends AbstractShiroWebFilterConfiguration {

    /**
     * shiro过滤器以及一些uri请求规则配置
     * 相比springmvc竟然不用配置DelegatingFilterProxy，不理解？
     * @param
     * @return
     */
    @Bean("shiroFilter")
    @Override
    protected ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = super.shiroFilterFactoryBean();
        Map<String, Filter> map = new LinkedHashMap<>();
        //加入自定义的Filter
        map.put("formAuthc", new DefaultFormAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(map);
        return shiroFilterFactoryBean;
    }
}
