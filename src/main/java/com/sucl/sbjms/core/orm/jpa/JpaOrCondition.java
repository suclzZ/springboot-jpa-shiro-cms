package com.sucl.sbjms.core.orm.jpa;

import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.orm.OrCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * or 查询条件
 * @author sucl
 * @date 2019/4/1
 */
@Data
@NoArgsConstructor
public class JpaOrCondition extends JpaCondition implements OrCondition {
    private JpaCondition condition1;
    private JpaCondition condition2;

    public JpaOrCondition(JpaCondition condition1,JpaCondition condition2){
        this.condition1 = condition1;
        this.condition2 = condition2;
    }

    public JpaOrCondition(String property,Object value){
        this(property,Opt.EQ,value);
    }

    public JpaOrCondition(String property,Opt opt,Object value){
        super(property,opt,value);
    }

    public Criterion generateExpression() {
        return Restrictions.or(convertToCriterion(this.condition1), convertToCriterion(this.condition2));
    }

    private Criterion convertToCriterion(Condition condition) {
        if( condition instanceof JpaCondition ){
            return ((JpaCondition) condition).generateExpression(null);
        }else if(condition instanceof JpaOrCondition){
            return ((JpaOrCondition) condition).generateExpression();
        }
        return null;
    }
}
