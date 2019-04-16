package com.sucl.sbjms.core.service.impl;

import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.orm.Order;
import com.sucl.sbjms.core.orm.Pager;
import com.sucl.sbjms.core.service.BaseService;
import com.sucl.sbjms.core.util.ConditionHelper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

/**
 * 方便之后对泛型R的使用，放弃Repository类型注入，改为JpaRepository,否则需要做各种类型校验与转换
 *
 * @author sucl
 * @date 2019/4/1
 */
public abstract class BaseServiceImpl<R extends Repository<T,Serializable>,T> implements BaseService<R,T>{

    protected R dao;
    /**
     * 通过examle查询
     */
    protected JpaRepository<T,Serializable> repository;
    /**
     * 通过Specification查询
     */
    protected JpaSpecificationExecutor<T> specificationExecutor;

    protected abstract Class<T> getDomainClazz();

    @Resource
    public void setRepository(R r) {
        this.dao = r;
        if(r instanceof JpaRepository){
            this.repository = (JpaRepository<T, Serializable>) r;
        }
        if(r instanceof JpaSpecificationExecutor){
            this.specificationExecutor = (JpaSpecificationExecutor<T>) r;
        }
    }

    /**
     * findOne
     * getOne  代理，你会发现 经常查询出来是个null
     * @param id
     * @return
     */
    @Override
    public T getById(Serializable id) {
        return repository.findOne(id);
    }

    @Override
    public T getOne(String property, Object value) {
        return repository.findOne(ConditionHelper.buildExample(getDomainClazz(),property,value));
    }

    @Override
    public T getInitializeObject(Serializable id, String[] props) {
        T t = repository.findOne(id);
        initializeObjectCollections(t, props);
        return t;
    }

    private void initializeObjectCollections(T initializeObject, String[] props) {
        if(props!=null){
            for(String prop : props){
                initializeObjectCollection(initializeObject, prop);
            }
        }
    }

    private void initializeObjectCollection(T initializeObject, String prop) {
        try {
            if(StringUtils.isNotEmpty(prop)){
                Object obj = PropertyUtils.getProperty(initializeObject, prop);
                Hibernate.initialize(obj);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> getAll2(Collection<Condition> conditions) {
        return specificationExecutor.findAll(ConditionHelper.buildSpecification(conditions));
    }

    @Override
    public List<T> getAll(T t) {
        if(t==null){
            return repository.findAll();
        }
        return repository.findAll(Example.of(t));
    }

    @Override
    public Pager<T> getPager(Pager pager, Collection<Condition> conditions, Collection<Order> orders) {
        Pageable pageable = new PageRequest(pager.getPageIndex()-1,pager.getPageSize(),ConditionHelper.buildSort(orders));
        Page<T> page = specificationExecutor.findAll(ConditionHelper.buildSpecification(conditions), pageable);
        pager.setMaxPage(page.getTotalPages());
        pager.setTotal( (int)page.getTotalElements());
        pager.setResult(page.getContent());
        return pager;
    }

    @Override
    public T save(T t) {
        return repository.save(t);
    }

    @Override
    public void saveBatch(Collection<T> ts) {
        repository.save(ts);
    }

    @Override
    public T updateById(T t) {
        return repository.save(t);
    }

    @Override
    public T saveOrUpdate(T t) {
        return repository.save(t);
    }

    @Override
    public void deleteById(Serializable id) {
        repository.delete(id);
    }

    @Override
    public void delete(String property, Object value) {

    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }

    @Override
    public void deleteAll(Collection<T> ts) {
        repository.deleteInBatch(ts);
    }

    @Override
    public boolean exist(Serializable id) {
        return repository.exists(id);
    }

    @Override
    public boolean exist(T t) {
        return repository.exists(Example.of(t));
    }
}
