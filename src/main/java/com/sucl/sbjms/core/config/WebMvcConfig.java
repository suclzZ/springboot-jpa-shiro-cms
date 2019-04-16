package com.sucl.sbjms.core.config;

import com.sucl.sbjms.core.method.support.ConditionHandlerMethodArgumentResolver;
import com.sucl.sbjms.core.method.support.OrderHandlerMethodArgumentResolver;
import com.sucl.sbjms.core.method.support.PagerHandlerMethodArgumentResolver;
import com.sucl.sbjms.core.orm.hibernate.HibernateAwareObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * WebMvcConfigurationSupport、WebMvcConfigurerAdapter
 * 因为WebMvcConfigurationSupport与WebMvcAutoConfiguration互斥，导致WebMvcAutoConfiguration中的配置会有很多失效，比如静态资源访问
 * 在处理jpa延迟加载时，如果使用的是WebMvcConfigurationSupport，那么原有的MappingJackson2HttpMessageConverter不会替换，
 * 此时需呀我们手动处理，可见configureMessageConverters
 * @author sucl
 * @date 2019/4/3
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 注册自定义参数解析器
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PagerHandlerMethodArgumentResolver());
        argumentResolvers.add(new ConditionHandlerMethodArgumentResolver());
        argumentResolvers.add(new OrderHandlerMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    /**
     * 监听请求国际化 uri?lang=en_US
     * @return
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor changeInterceptor = new LocaleChangeInterceptor();
        changeInterceptor.setParamName("lang");
        return changeInterceptor;
    }

    /**
     * 本地化配置
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.getDefault());
        return localeResolver;
    }

    /**
     * 注册过国际化请求参数拦截
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * 将返回值统一化处理，目前通过HandlerMethodReturnValueHandlerModifier实现，主要是修改ResponseBody的逻辑
     * @param returnValueHandlers
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {

        super.addReturnValueHandlers(returnValueHandlers);
    }

    /**
     * 静态资源请求处理
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    /**
     * 修改默认的消息转换器
     * 处理jpa查询数据JavassistLazyInitializer 相关问题
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for(HttpMessageConverter converter: converters){
            if(converter instanceof MappingJackson2HttpMessageConverter){
                HibernateAwareObjectMapper hibernateAwareObjectMapper = new HibernateAwareObjectMapper();
                ((MappingJackson2HttpMessageConverter) converter).setObjectMapper(hibernateAwareObjectMapper);
            }
        }
        super.extendMessageConverters(converters);
    }

}
