package com.sucl.sbjms.core.util;

import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.orm.jpa.JpaCondition;
import com.sucl.sbjms.core.orm.jpa.JpaOrCondition;
import com.sucl.sbjms.core.orm.jpa.NestedCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sucl
 * @date 2019/4/22
 */
public class ConditionUtils {
    private static final String OR = "or",AND = "and";


    public static List<Condition> toCondition(NestedCondition nestedCondition){
        List<Condition> conds = new ArrayList<>(),children = null;
        if(nestedCondition !=null && (children = nestedCondition.getConditions())!=null){
            if(AND.equals(nestedCondition.getType())){
                for(Condition cond : children){
                    if(cond instanceof NestedCondition){
                        conds.addAll(toCondition((NestedCondition) cond));
                    }else{
                        conds.add(cond);
                    }
                }
            }else{
                JpaOrCondition orConditon = new JpaOrCondition();
                for(Condition cond : children){
                    if(cond instanceof NestedCondition){
//                        conds.addAll(toCondition((NestedCondition) cond));
                        orConditon.addCondition((JpaCondition) cond);
                    }else if(cond instanceof JpaCondition){
                        orConditon.addCondition((JpaCondition) cond);
                    }
                }
                conds.add(orConditon);
            }
        }
        return conds;
    }
}
