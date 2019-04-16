package com.sucl.sbjms.core.orm.jpa;

import com.sucl.sbjms.core.orm.Condition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * and查询条件
 * @author sucl
 * @date 2019/4/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JpaCondition implements Condition {

    protected String property;
    protected Opt operate;
    protected Object value;

    public JpaCondition(String property,Object value){
        this(property,Opt.EQ,value);
    }

    @Override
    public String getProperty() {
        return this.property;
    }

    @Override
    public Opt getOperate() {
        return this.operate;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    /**
     * 通过Criterion构建条件
     * @param alias
     * @return
     */
    public Criterion generateExpression(String alias){
        if (this.value != null) {
            switch (this.operate) {
                case EQ:
                    return Restrictions.eq(this.property, this.value);
                case NQ:
                    return Restrictions.ne(this.property, this.value);
                case IS_NULL:
                    return Restrictions.isNull(this.property);
                case NOT_NULL:
                    return Restrictions.isNotNull(this.property);
                case GT:
                    return Restrictions.gt(this.property, this.value);
                case GE:
                    return Restrictions.ge(this.property, this.value);
                case LT:
                    return Restrictions.lt(this.property, this.value);
                case LE:
                    return Restrictions.le(this.property, this.value);
                case LIKE:
                    return Restrictions.like(this.property, this.value.toString(), MatchMode.ANYWHERE);
                case LEFT_LIKE:
                    return Restrictions.like(this.property, this.value.toString(), MatchMode.START);
                case RIGHT_LIKE:
                    return Restrictions.like(this.property, this.value.toString(), MatchMode.END);
                case BWT:
                    String[] betweenArray = this.value.toString().split(",");
                    if (betweenArray.length < 2) return null;
                    return generateBetween(betweenArray[0], betweenArray[1]);
                case IN:
                    if ((this.value instanceof Object[]))
                        return Restrictions.in(this.property, (Object[]) this.value);
                case NOT_IN:
                    if ((this.value instanceof Object[]))
                        return Restrictions.not(Restrictions.in(this.property, (Object[]) this.value));
            }
        }else{
            if (this.operate == Opt.IS_NULL)
                return Restrictions.isNull(this.property);
            if (this.operate == Opt.NOT_NULL) {
                return Restrictions.isNotNull(this.property);
            }
        }
        return null;
    }

    private Criterion generateBetween(String begin, String end) {
        return Restrictions.between(this.property, begin, end);
    }
}
