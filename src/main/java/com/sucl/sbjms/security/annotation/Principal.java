package com.sucl.sbjms.security.annotation;

import java.lang.annotation.*;

/** 可以作为身份的标识
 * 加上该注解，标识该字段可以作为身份字段，登录时则可以用该字段作为用户名
 * @author sucl
 * @date 2019/4/16
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Principal {

    int order() default 0;

    boolean matcher() default true;
}
