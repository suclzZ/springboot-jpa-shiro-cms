package com.sucl.sbjms.security.util;

import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.orm.jpa.JpaCondition;
import com.sucl.sbjms.security.annotation.Principal;
import com.sucl.sbjms.system.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sucl
 * @date 2019/4/18
 */
public abstract class AbstractPrincipalHelper {

    public static <T> List<Condition> princinalCondition(String princinal){
        ConditionFieldCallback<T> callback = new ConditionFieldCallback<T>(princinal);
        ReflectionUtils.doWithFields(User.class, callback);
        return callback.getConditions();
    }

    private static class ConditionFieldCallback<T> implements ReflectionUtils.FieldCallback{
        private List<OrderExample> orderExamples = new ArrayList<>();
        private Object value;

        public ConditionFieldCallback(Object value){
            this.value = value;
        }

        public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException{
            Principal principal = field.getAnnotation(Principal.class);
            if(principal!=null){
                if(principal.matcher()){
                    orderExamples.add(new OrderExample(principal.order(),new JpaCondition(field.getName(),value)));
                }
            }
        }

        public List<Condition> getConditions(){
            orderExamples.sort(Comparator.comparing(OrderExample::getOrder));
            return orderExamples.stream().map(oe->oe.getCondition()).collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    private static class OrderExample{
        private int order;
        private Condition condition;

        public OrderExample(int order,Condition condition){
            this.order = order;
            this.condition = condition;
        }
    }
}
