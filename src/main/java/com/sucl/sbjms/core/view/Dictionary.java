package com.sucl.sbjms.core.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dictionary {
    private String group;
//    private Map<String ,String> data;//无序，除非不用hashmap
    private Collection<Code> data;//代码集有序
}
