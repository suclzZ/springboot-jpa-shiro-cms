package com.sucl.sbjms.core.util;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sucl
 * @date 2019/4/22
 */
public class ReflectionHelper<T> {
    private List<String> properties = new ArrayList<>();
    private Map<String,Class> types = new HashMap<>();
    private Class<T> clazz;
    private List<? extends FieldFilter> fieldFilters;

    public ReflectionHelper(Class<T> clazz){
        this.clazz = clazz;
    }

    public ReflectionHelper(Class<T> clazz,List<? extends FieldFilter> fieldFilters){
        this.clazz = clazz;
        this.fieldFilters = fieldFilters;
    }

    public void processField(){
        ReflectionUtils.doWithFields(clazz, new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                properties.add(field.getName());
                types.put(field.getName(),field.getType());
            }
        }, new ReflectionUtils.FieldFilter() {
            @Override
            public boolean matches(Field field) {
                if(fieldFilters!=null){
                    for(FieldFilter fieldFilter : fieldFilters){
                        if(!fieldFilter.test(field)){
                            return false;
                        }
                    }
                }
                return true;
            }
        });
    }

    public void processLocalField(){
        ReflectionUtils.doWithLocalFields(clazz, new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                properties.add(field.getName());
                types.put(field.getName(),field.getType());
            }
        });
    }

    public List<String> getProperties() {
        return properties;
    }

    public Map<String, Class> getTypes() {
        return types;
    }

    public interface FieldFilter{
        boolean test(Field field);
    }

    private class ParentFieldFilter implements FieldFilter{
        public boolean test(Field field){
            return true;
        }
    }
}
