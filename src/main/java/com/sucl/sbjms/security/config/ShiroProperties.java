package com.sucl.sbjms.security.config;

import com.sucl.sbjms.security.Constant;
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

    /**
     * 是否启用验证码
     */
    private boolean verify = false;

    /**
     * 默认开启
     */
    private boolean dev;

    /**
     *
     */
    private String hashAlgorithmName = Constant.DEFAULT_HASH_ALGORITHM;

    /**
     *
     */
    private int hashiterations = Constant.DEFAULT_HASH_ITERATIONS;
}
