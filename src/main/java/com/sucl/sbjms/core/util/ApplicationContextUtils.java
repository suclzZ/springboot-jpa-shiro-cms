package com.sucl.sbjms.core.util;

import org.springframework.context.ApplicationContext;

public class ApplicationContextUtils {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextUtils.applicationContext = applicationContext;
    }

    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    public static Object getBean(Class<?> requiredType){
        return applicationContext.getBean(requiredType);
    }

}
