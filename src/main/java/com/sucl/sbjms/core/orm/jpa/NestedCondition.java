package com.sucl.sbjms.core.orm.jpa;

import com.sucl.sbjms.core.orm.Condition;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * 嵌套查询条件
 * @author sucl
 * @date 2019/4/2
 */
public class NestedCondition implements Condition {

    private JpaCondition[] conditions;

    public NestedCondition(JpaCondition... conditions) {
        this.conditions = conditions;
    }

    public JpaCondition[] getConditions() {
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
}
