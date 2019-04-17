package com.sucl.sbjms.security.service;

import com.sucl.sbjms.core.service.FreemarkerVariable;
import com.sucl.sbjms.security.permission.ShiroTag;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

/**
 * @author sucl
 * @date 2019/4/16
 */
@Component
public class ShiroFreemarkerVariable implements FreemarkerVariable {
    @Override
    public String getName() {
        return "shiro";
    }

    @Override
    public Object getVariables() {
        return new ShiroTag();
    }
}
