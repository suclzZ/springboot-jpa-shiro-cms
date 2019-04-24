package com.sucl.sbjms.core.orm.jpa;

import com.sucl.sbjms.core.orm.Condition;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 嵌套查询条件
 * @author sucl
 * @date 2019/4/2
 */
@Slf4j
@NoArgsConstructor
public class NestedCondition extends JpaCondition {

    private String type;//or and

    private List<Condition> conditions;

    public NestedCondition(String type){
        if("or".equals(type) || "and".equals(type)||"".equals(type)){
            if("".equals(type)){
                type = "and";
            }
            this.type = type;
        }else {
            log.warn("construct NestedCondition failed with parameter type【{}】!",type);
        }

    }

    public void addCondition(Condition condition){
        if(conditions==null){
            conditions = new ArrayList<>();
        }
        conditions.add(condition);
    }

    public NestedCondition(Condition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    @Override
    public String getProperty() {
        return null;
    }

    @Override
    public Opt getOperate() {
        return null;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setProperty(String prop) {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Criterion generateExpression() {
        List<Criterion> criterias = new ArrayList<>(conditions.size());
        if(conditions!=null && conditions.size()>0){
            for(Condition cond : conditions){
                criterias.add(convertToCriterion(cond));
            }
        }
        if("and".equals(type)){
            return Restrictions.and(criterias.toArray(new Criterion[criterias.size()]));
        }else if("or".equals(type)){
            return Restrictions.or(criterias.toArray(new Criterion[criterias.size()]));
        }
        return null;
    }
}
