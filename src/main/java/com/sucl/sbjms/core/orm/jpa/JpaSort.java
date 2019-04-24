package com.sucl.sbjms.core.orm.jpa;

import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author sucl
 * @date 2019/4/19
 */
public class JpaSort extends Sort {

    public JpaSort(Order... orders){
        super(orders);
    }

    public JpaSort(List<Order> orders){
        super(orders);
    }

    public JpaSort(String prop,boolean asc){
        super(new Order(asc?Direction.ASC:Direction.DESC,prop));
    }

    public JpaSort(String prop){
        super(new Order(prop));
    }
}
