package com.sucl.sbjms.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author sucl
 * @date 2019/4/16
 */
@Data
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {

    private boolean verify = false;//是否启用验证码
}
