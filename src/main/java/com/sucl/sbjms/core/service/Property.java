package com.sucl.sbjms.core.service;

import java.io.Serializable;

/**
 * @author sucl
 * @date 2019/4/22
 */
@FunctionalInterface
public interface Property<T, R> extends Serializable {

    R apply(T t);
}
