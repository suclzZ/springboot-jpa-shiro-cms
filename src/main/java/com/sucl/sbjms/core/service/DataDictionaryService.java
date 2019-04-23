package com.sucl.sbjms.core.service;

import com.sucl.sbjms.core.view.Dictionary;

import java.util.Collections;
import java.util.List;

public interface DataDictionaryService {

    default List<Dictionary> getDictionaries(){return Collections.EMPTY_LIST;}

    default List<Dictionary> getDictionaries(String[] groups){
        return Collections.EMPTY_LIST;
    }
}
