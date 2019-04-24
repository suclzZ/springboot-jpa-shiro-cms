package com.sucl.sbjms.system.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.orm.jpa.JpaCondition;
import com.sucl.sbjms.core.service.DataDictionaryService;
import com.sucl.sbjms.core.service.impl.BaseServiceImpl;
import com.sucl.sbjms.core.view.Code;
import com.sucl.sbjms.core.view.Dictionary;
import com.sucl.sbjms.system.dao.CodeitemDao;
import com.sucl.sbjms.system.entity.Codeitem;
import com.sucl.sbjms.system.service.CodeitemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Service
@Transactional
public class CodeitemServiceImpl extends BaseServiceImpl<CodeitemDao,Codeitem> implements CodeitemService,DataDictionaryService {
    @Override
    protected Class<Codeitem> getDomainClazz() {
        return Codeitem.class;
    }

    @Override
    public List<Dictionary> getDictionaries() {
        List<Codeitem> codes = this.getAll(null);
        return buildDictionaries(codes);
    }

    private List<Dictionary> buildDictionaries(List<Codeitem> codes) {
        List<Dictionary> dds = new ArrayList<>();
        if(codes!=null && codes.size()>0){
            Multimap<String,Code> dataMap = ArrayListMultimap.create();
            for(Codeitem codeitem : codes){
                String code = codeitem.getItemCode(),caption = codeitem.getItemName();
                String group = codeitem.getCodegroup().getGroupName();
                dataMap.put(group,new Code(code,caption));
            }
            Map<String, Collection<Code>> dictionaryMap = dataMap.asMap();
            for(Map.Entry<String, Collection<Code>> entry: dictionaryMap.entrySet()){
                dds.add(new Dictionary(entry.getKey(), entry.getValue()));
            }
        }
        return dds;
    }

    @Override
    public List<Dictionary> getDictionaries(String[] groups) {
        if(groups!=null && groups.length>0){
            List<Condition> conds = new ArrayList<>();
            //没有试是否可行
            conds.add(new JpaCondition("codegroup.groupName",Condition.Opt.IN,groups));
            List<Codeitem> codes = this.getAll2(conds);
            return buildDictionaries(codes);
        }

        return getDictionaries();
    }
}
