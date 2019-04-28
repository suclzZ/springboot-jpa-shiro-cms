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
     * verificationCode enable,defualt false
     */
    private boolean verify = false;

    /**
     * dev mode default true
     */
    private boolean dev = true;

    /**
     *
     */
    private String hashAlgorithmName = Constant.DEFAULT_HASH_ALGORITHM;

    /**
     *
     */
    private int hashiterations = Constant.DEFAULT_HASH_ITERATIONS;
}
