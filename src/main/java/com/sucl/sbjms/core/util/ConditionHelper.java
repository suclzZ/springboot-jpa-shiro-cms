package com.sucl.sbjms.core.util;

import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.orm.Order;
import com.sucl.sbjms.core.orm.jpa.NestedCondition;
import com.sucl.sbjms.core.rem.BusException;
import com.sucl.sbjms.core.service.impl.CustomSpecification;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 将condition转换成jpa查询需要的对象 Specification
 *
 * Example: 仅能对字符串做处理，
 *  DEFAULT (case-sensitive)	firstname = ?0	默认（大小写敏感）
 * DEFAULT (case-insensitive)	LOWER(firstname) = LOWER(?0)	默认（忽略大小写）
 * EXACT (case-sensitive)	firstname = ?0	精确匹配（大小写敏感）
 * EXACT (case-insensitive)	LOWER(firstname) = LOWER(?0)	精确匹配（忽略大小写）
 * STARTING (case-sensitive)	firstname like ?0 + ‘%’	前缀匹配（大小写敏感）
 * STARTING (case-insensitive)	LOWER(firstname) like LOWER(?0) + ‘%’	前缀匹配（忽略大小写）
 * ENDING (case-sensitive)	firstname like ‘%’ + ?0	后缀匹配（大小写敏感）
 * ENDING (case-insensitive)	LOWER(firstname) like ‘%’ + LOWER(?0)	后缀匹配（忽略大小写）
 * CONTAINING (case-sensitive)	firstname like ‘%’ + ?0 + ‘%’	模糊查询（大小写敏感）
 * CONTAINING (case-insensitive)	LOWER(firstname) like ‘%’ + LOWER(?0) + ‘%’	模糊查询（忽略大小写）
 */
public class ConditionHelper {

    public static <T> T conditionPojo(Class<T> clazz){
        return BeanUtils.instantiateClass(clazz);
    }

    /**
     *
     * @param clazz
     * @param property
     * @param value
     * @param <T>
     * @return
     */
    public static <T> Example<T> buildExample(Class<T> clazz, String property,Object value){
        T probe = getProbe(conditionPojo(clazz) , property,value);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        if(probe!=null && property!=null && !"".equals(property)){
            exampleMatcher = exampleMatcher.withMatcher(property,matcher->matcher.exact().ignoreCase());
        }
        return Example.of(probe, exampleMatcher);
    }

    /**
     * 构建Example查询条件
     * @param clazz
     * @param conditions
     * @param <T>
     * @return
     */
    public static <T> Example<T> buildExample(Class<T> clazz, Collection<Condition> conditions){
        T probe = getProbe(conditionPojo(clazz) , conditions);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        if(probe!=null && conditions!=null && conditions.size()>0){
            BusException donotSupportException = new BusException("don't support query!");
            conditions.stream().forEach(cond->{
                switch (cond.getOperate()){
                    case EQ:
                        exampleMatcher.withMatcher(cond.getProperty(),matcher->matcher.exact().ignoreCase());
                        break;
                    case NQ:
                        throw donotSupportException;
                    case IS_NULL:
                        throw donotSupportException;
                    case NOT_NULL:
                        throw donotSupportException;
                    case GT:
                        throw donotSupportException;
                    case GE:
                        throw donotSupportException;
                    case LT:
                        throw donotSupportException;
                    case LE:
                        throw donotSupportException;
                    case LIKE:
                        exampleMatcher.withMatcher(cond.getProperty(),matcher -> matcher.contains());
                        break;
                    case LEFT_LIKE:
                        exampleMatcher.withMatcher(cond.getProperty(),matcher -> matcher.startsWith());
                        break;
                    case RIGHT_LIKE:
                        exampleMatcher.withMatcher(cond.getProperty(),matcher -> matcher.endsWith());
                        break;
                    case BWT:
                        throw donotSupportException;
                    case IN:
                        throw donotSupportException;
                    case NOT_IN:
                        throw donotSupportException;
                }
            });

        }
        return Example.of(probe, exampleMatcher);
    }

    private static <T> T getProbe(T t, String property,Object value){
        if(property!=null && !"".equals(property)){
            try {
                PropertyUtils.setProperty(t,property,value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    private static <T> T getProbe(T t, Collection<Condition> conditions){
        if(conditions!=null && conditions.size()>0){
            conditions.stream().forEach(cond->{
                getProbe(t,cond.getProperty(),cond.getValue());
            });
        }
        return t;
    }

    /**
     * Specification
     * 当属于and时有没括号都一样，所以括号对应的是specifications内容为or关系的数据
     *
     * @param conditions
     * @param <T>
     * @return
     */
    public static <T> Specification<T> buildSpecification(Collection<Condition> conditions){
//        Specifications.where();
        List<Specification> specifications = new ArrayList<>();
        List<Condition> unNestedConditions = new ArrayList<>();
        if(conditions!=null){
            conditions.stream().forEach(cond->{
                if(cond instanceof NestedCondition){
                    //通过某种策略决定是or 还是 and
                    specifications.add( new CustomSpecification<T>( Arrays.asList(((NestedCondition) cond).getConditions()) ));
                }else{
                    unNestedConditions.add(cond);
                }
            });
        }
        specifications.add(new CustomSpecification(unNestedConditions));
        Specifications specification = null;
        for(int i=0;i<specifications.size();i++){
            if(specification==null){
                specification = Specifications.where(specifications.get(i));
            }else{
                specification = specification.and(specifications.get(i));
            }
        }
//        return new CustomSpecification(conditions);
        return specification;
    }

    /**
     * 构建排序条件
     * @param orders
     * @return
     */
    public static Sort buildSort(Collection<Order> orders){
        if(orders!=null && orders.size()>0){
            List<Sort.Order> sortOrders = new ArrayList<>();
            orders.stream().forEach(o->{
                Sort.Direction dire = o.getDirection()==null?Sort.DEFAULT_DIRECTION:Sort.Direction.valueOf(o.getDirection());
                sortOrders.add( new Sort.Order(dire,o.getProperty()) );
            });
            return new Sort(sortOrders);
        }else{
            return null;
        }
    }
}
