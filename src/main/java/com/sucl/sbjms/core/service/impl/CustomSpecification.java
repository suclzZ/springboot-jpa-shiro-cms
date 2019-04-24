package com.sucl.sbjms.core.service.impl;

import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.orm.OrCondition;
import com.sucl.sbjms.core.rem.BusException;
import lombok.NoArgsConstructor;
import org.hibernate.jpa.internal.metamodel.EntityTypeImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 配合JpaSpecificationExecutor接口实现多条件查询
 * 关联查询？ join 只支持一级关联查询，超过的有问题，目前没找到原因
 * 嵌套查询？ () and/or () 不支持
 * @author sucl
 * @date 2019/4/1
 */
@NoArgsConstructor
public class CustomSpecification<T> implements Specification {
    private Collection<Condition> conditions;
    private Map<String,Set<String>> entityField = new HashMap<>();//ConcurrentHashMap<>();
    private Map<String,Attribute> entityAttribute = new HashMap<>();
    private Map<String,Join> joinMap = new HashMap<>();
    private boolean andOr = true;//true:and false:or

    public CustomSpecification(Collection<Condition> conditions){
        this.conditions = conditions;
    }
    public CustomSpecification(Collection<Condition> conditions,boolean andOr){
        this.conditions = conditions;
        this.andOr = andOr;
    }

    /**
     * or and同时发生，无法在一次条件中即构建or又构建and，只能通过嵌套实现
     * @param root
     * @param query
     * @param criteriaBuilder
     * @return
     */
    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        List<Boolean> ors = null;
        if(conditions!=null){
            ors = conditions.stream().map(c->{
                Predicate predicate = createPredicate(root, query, criteriaBuilder, c);
                if(predicate!=null){
                    predicates.add( predicate );
                }
                return OrCondition.class.isAssignableFrom(c.getClass());//记录每个条件是or还是and
            }).collect(Collectors.toList());
        }
//        List<Predicate> orPredicates = new ArrayList<>();
//        List<Predicate> andPredicates = new ArrayList<>();
//        for(int i=0;i<predicates.size();i++){
//            if(ors.get(i)){
//                orPredicates.add( criteriaBuilder.or(predicates.get(i)) );
//            }else{
//                andPredicates.add( criteriaBuilder.and(predicates.get(i)) );
//            }
//        }
//        Predicate p1 = criteriaBuilder.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
//        Predicate p2 = criteriaBuilder.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
        return andOr? criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])):criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
//        return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
    }

    private Predicate createPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder, Condition cond){
        if(isProperty(root,cond)){
            return predicate(criteriaBuilder,root,cond);
        }
        if(isRelateProperty(cond)){
            Class clazz = getRelationClazz(root,cond);
            Root relRoot = criteriaBuilder.createQuery(clazz).from(clazz);
//            relRoot.fetch()
            if(clazz !=null){
                String relTable = clazz.getSimpleName().toLowerCase();
                Join join;
                if( (join = joinMap.get(relTable))==null){
                    join = root.join(clazz.getSimpleName().toLowerCase(), JoinType.LEFT);//
                    joinMap.put(relTable,join);
                }
                if(isProperty(relRoot,cond)){
                    return joinPredicate(criteriaBuilder, join, cond);
                }
                //二级关联left join一直加不上去
                return createPredicate(relRoot,query,criteriaBuilder,cond);
            }
        }
        return null;
    }

    private Class getRelationClazz(Root root, Condition c) {
        String[] props = c.getProperty().split("\\.");
        int firstIndex = c.getProperty().indexOf(".");
        String left = c.getProperty().substring(0,firstIndex);//props[0]
        String right = c.getProperty().substring(firstIndex+1);//props[1]
        String entityClassName = ((EntityTypeImpl) root.getModel()).getTypeName();
        Attribute attr = entityAttribute.get(entityClassName + "." + left);
        c.setProperty(right);
        return attr.getJavaType();
    }

    /**
     * 是否是对象属性
     * @param root
     * @param c
     * @return
     */
    private boolean isProperty(Root root, Condition c) {
        EntityType model = root.getModel();
        String entityClassName = ((EntityTypeImpl) model).getTypeName();
        Set<String> fields = null;
        if(entityField.get(entityClassName)!=null){
            fields = entityField.get(entityClassName);
        }else{
            Set<Attribute> attrs = model.getDeclaredAttributes();
            fields = attrs.stream().map(s -> {
                entityAttribute.put(entityClassName+"."+s.getName(),s);
                return s.getName();
            }).collect(Collectors.toSet());
            entityField.put(entityClassName,fields);
        }
        return fields.contains(c.getProperty());
    }

    /**
     * 关联字段
     * @param c
     * @return
     */
    private boolean isRelateProperty(Condition c) {
        if(c!=null && c.getProperty().indexOf(".")!=-1){
            int count = StringUtils.countOccurrencesOf(c.getProperty(),".");
            if(count>=3){
                throw new BusException(String.format("join query level is %s,don'tsupport!",count));
            }
            return true;
        }
        return false;
    }


    private Predicate predicate(CriteriaBuilder criteriaBuilder,Root<T> root, Condition condition){
        return compare(root.get(condition.getProperty()),condition.getOperate(),condition.getValue(),criteriaBuilder);
    }

    private Predicate joinPredicate(CriteriaBuilder criteriaBuilder,Join join, Condition condition){
        return compare(join.get(condition.getProperty()),condition.getOperate(),condition.getValue(),criteriaBuilder);
    }

    private Predicate compare(Path path,Condition.Opt operate,Object value,CriteriaBuilder criteriaBuilder){
        Predicate predicate = null;
        ExpressionValueFormater evf = new ExpressionValueFormater();
        if(value!=null){
            switch (operate){
                case EQ:
                    predicate = criteriaBuilder.equal(path.as(String.class),value);
                    break;
                case NE:
                    predicate = criteriaBuilder.notEqual(path.as(String.class),value);
                    break;
                case IS_NULL:
                    predicate = criteriaBuilder.isNull(path.as(String.class));
                    break;
                case NOT_NULL:
                    predicate = criteriaBuilder.isNotNull(path.as(String.class));
                    break;
                case GT:
                    //处理具体类型进行比较，目前全部转换为string
                    predicate = criteriaBuilder.greaterThan(path.as(String.class),value.toString());
                    break;
                case GE:
                    predicate = criteriaBuilder.greaterThanOrEqualTo(path.as(String.class),value.toString());
                    break;
                case LT:
                    predicate = criteriaBuilder.lessThan(path.as(String.class),value.toString());
                    break;
                case LE:
                    predicate = criteriaBuilder.lessThanOrEqualTo(path.as(String.class),value.toString());
                    break;
                case LIKE:
                    predicate = criteriaBuilder.like(path.as(String.class),"%"+value.toString()+"%");
                    break;
                case LEFT_LIKE:
                    predicate = criteriaBuilder.like(path.as(String.class),value.toString()+"%");
                    break;
                case RIGHT_LIKE:
                    predicate = criteriaBuilder.like(path.as(String.class),"%"+value.toString());
                    break;
                case BWT:
                    String[] values = value.toString().trim().split(",|，");
                    assert values.length!=2:"the format of value:"+value+" do not be supported,must contain ',' or '，'";
                    predicate = criteriaBuilder.between(path,values[0],values[1]);
                    break;
                case IN:
                    String[] value2s = value.toString().trim().split(",");
                    if(value2s!=null && value2s.length>0){
                        CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
                        for(String inVal : value2s){
                            in.value(inVal);
                        }
                        predicate = in;
                    }
                    break;
                case NOT_IN:

                    break;
            }
        }else{
            if(operate == Condition.Opt.EQ || operate == Condition.Opt.IS_NULL){
                predicate = criteriaBuilder.isNull(path.as(String.class));
            }
            if(operate == Condition.Opt.NE || operate == Condition.Opt.NOT_NULL){
                predicate = criteriaBuilder.isNotNull(path.as(String.class));
            }
        }
        return predicate;
    }
}
