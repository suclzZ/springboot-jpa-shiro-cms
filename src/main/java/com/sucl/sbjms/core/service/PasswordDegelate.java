package com.sucl.sbjms.core.service;

/**
 * 实现密码的处理的装饰器
 * @author sucl
 * @date 2019/4/11
 */
public interface PasswordDegelate {

    String encode(String password);
}
