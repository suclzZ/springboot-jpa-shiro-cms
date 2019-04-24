package com.sucl.sbjms.core.orm.jpa;

import com.sucl.sbjms.core.orm.OrCondition;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * or 查询条件
 * @author sucl
 * @date 2019/4/1
 */
@Getter
@Setter
@NoArgsConstructor
public class JpaOrCondition extends JpaCondition implements OrCondition {
    private JpaCondition condition1;
    private JpaCondition condition2;
    private List<JpaCondition> conditions = new ArrayList<>();

    public JpaOrCondition(JpaCondition condition1,JpaCondition condition2){
        this.condition1 = condition1;
        this.condition2 = condition2;
        this.conditions.addAll(Arrays.asList(condition1,condition2));
    }

    public JpaOrCondition(JpaCondition ... conditions){
        this.conditions.addAll(Arrays.asList(conditions));
    }

    public JpaOrCondition(String property,Object value){
        this(property,Opt.EQ,value);
    }

    public JpaOrCondition(String property,Opt opt,Object value){
        super(property,opt,value);
    }

    public void addCondition(JpaCondition condition){
        conditions.add(condition);
    }

    public Criterion _generateExpression() {
        return Restrictions.or(convertToCriterion(this.condition1), convertToCriterion(this.condition2));
    }

    public Criterion generateExpression() {
        if(condition1!=null && condition2!=null){
            return _generateExpression();
        }
        if(conditions.size()==0){
            return null;
        }else if(conditions.size()==1){
            return conditions.get(0).generateExpression();
        }else{
            List<Criterion> criterias = new ArrayList<>(conditions.size());
            for(JpaCondition cond : conditions){
                criterias.add(convertToCriterion(cond));
            }
            return Restrictions.or(criterias.toArray(new Criterion[criterias.size()]));
        }
    }

}
