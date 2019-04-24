package com.sucl.sbjms.core.service;

import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.orm.Order;
import com.sucl.sbjms.core.orm.Pager;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author sucl
 * @date 2019/4/1
 */
public interface BaseService<R ,T> {

    T getById(Serializable id);

    List<T> getByIds(Collection ids);

    T getOne(String property, Object value);

    T getInitializeObject(Serializable id, String[] props);

    List<T> getAll2(Collection<Condition> conditions);

    List<T> getAll2(Collection<Condition> conditions, Sort sort);

    List<T> getAll(T t);

    public List<T> getAll(T t, Sort sort);

    Pager<T> getPagerWithCondOrder(Pager pager, Collection<Condition> conditions, Collection<Order> orders);

    Pager<T> getPagerWithCondSort(Pager pager, Collection<Condition> conditions, Sort sort);

    T save(T t);

    void saveBatch(Collection<T> ts);

    T updateById(T t);

    T saveOrUpdate(T t);

    void deleteById(Serializable id);

    void delete(String property, Object value);

    void delete(T t);

    void deleteAll(Collection<T> ts);

    boolean exist(Serializable id);

    boolean exist(T t);

    boolean exist(String prop, Object value);

    List<T> query(Collection<Condition> conditions);
}
