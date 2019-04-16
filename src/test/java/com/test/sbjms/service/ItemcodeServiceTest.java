package com.test.sbjms.service;

import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.orm.Pager;
import com.sucl.sbjms.core.orm.jpa.JpaCondition;
import com.sucl.sbjms.system.entity.Codeitem;
import com.sucl.sbjms.system.service.CodeitemService;
import com.test.sbjms.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author sucl
 * @date 2019/4/10
 */
public class ItemcodeServiceTest extends Test {

    @Autowired
    private CodeitemService codeitemService;

    @org.junit.Test
    public void test(){
        f1();
    }

    private void f1() {

        Collection<Condition> conditions= new ArrayList<>();;
//        conditions.add(new JpaCondition("itemId","1"));
        conditions.add(new JpaCondition("codegroup.groupId","1"));
        conditions.add(new JpaCondition("page:pageSize","1"));
//        List<Codeitem> codeitems = codeitemService.getAll2(conditions);
//        System.out.println(codeitems );

        Pager pager = new Pager("1","8");
        Pager<Codeitem> p = codeitemService.getPager(pager, conditions, null);
        System.out.println(p);
    }
}
