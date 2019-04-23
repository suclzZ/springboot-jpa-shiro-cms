package com.sucl.sbjms.core.view;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Dictionary {
    private String group;
//    private Map<String ,String> data;//无序，除非不用hashmap
    private List<Code> data;//代码集有序
}
