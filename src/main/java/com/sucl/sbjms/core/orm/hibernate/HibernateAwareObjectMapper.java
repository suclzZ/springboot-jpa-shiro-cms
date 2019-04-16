package com.sucl.sbjms.core.orm.hibernate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * 解决@ResponseBody导致FetchType.LAZY失效问题
 * 主要是LAZY对象使用了代理，我们可以通过在Lazy对象上加上@JsonIgnore
 * 或者@JsonIgnoreProperties({ "handler","hibernateLazyInitializer","fieldHandler"})
 *
 * 记得需要注册到MappingJackson2HttpMessageConverter
 *
 * @author sucl
 * @since 2019/3/19
 */
public class HibernateAwareObjectMapper extends ObjectMapper {

    public HibernateAwareObjectMapper(){
        Hibernate4Module hibernate4Module = new Hibernate4Module();
        hibernate4Module.configure(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION, false);
        registerModule(hibernate4Module);
    }

    public void setPrettyPrint(boolean prettyPrint) {
        configure(SerializationFeature.INDENT_OUTPUT, prettyPrint);
    }
}
