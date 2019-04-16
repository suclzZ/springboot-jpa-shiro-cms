package com.sucl.sbjms.core.service.impl;

import lombok.NoArgsConstructor;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import java.util.Date;

/**
 * @author sucl
 * @date 2019/4/10
 */
@NoArgsConstructor
public class ExpressionValueFormater {
    private Expression expression;
    private Object value;

    public ExpressionValueFormater(Path path,Object value){
        if(value != null){
            if(value instanceof String){
                expression = path.as(String.class);
            }else if(value instanceof Date){
                expression = path.as(Date.class);
            }
        }
    }
}
