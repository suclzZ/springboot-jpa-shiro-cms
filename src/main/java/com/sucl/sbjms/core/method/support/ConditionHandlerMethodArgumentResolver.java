package com.sucl.sbjms.core.method.support;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sucl.sbjms.core.method.annotation.QueryCondition;
import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.orm.Domain;
import com.sucl.sbjms.core.orm.jpa.JpaCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sucl
 * @date 2019/4/3
 */
public class ConditionHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    public static final String CONDITIONS = "_conditions";
    private Map<String, Set<String>> cachedDomainFieldNames = new ConcurrentHashMap<>();
    private Map<String, List<FieldClass>> cachedDomainFieldClasss = new ConcurrentHashMap<>();

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(QueryCondition.class) &&
                Collection.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
//        ResolvableType resolvableType = ResolvableType.forMethodParameter(methodParameter);
//        Class<?> domainClazz = resolvableType.getGeneric(0).resolve();
//        Set<String> fieldNames = getDomainProperties(domainClazz);
//        List<FieldClass> fieldClasss = getDomainParamClasss(domainClazz);

        String conditionJson = nativeWebRequest.getParameter(CONDITIONS);
        if(StringUtils.isNotEmpty(conditionJson)){
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(conditionJson, new TypeReference<Collection<JpaCondition>>() { });
        }else{
            return toConditionCollection(nativeWebRequest.getParameterMap());
//            converToWrapper(parameters, fieldNames,fieldClasss);
        }
    }

    private Collection<Condition> toConditionCollection(Map<String,String[]> parameterMap) {
        Collection<Condition> conditions = new ArrayList<>();
        if(MapUtils.isNotEmpty(parameterMap)){
            for(Map.Entry<String,String[]> entry : parameterMap.entrySet()){
                Object value = entry.getValue();
                if(value instanceof String[]){
                    value = ((String[])value)[0];
                }
                ((ArrayList<Condition>) conditions).add(new JpaCondition(entry.getKey(),value));
            }
        }
        return conditions;
    }

    private Map<String,String> toStringValueMap(Map<String,String[]> parameterMap) {
        Map<String,String> parameters = new HashMap<>();
        if(parameterMap!=null){
            for(Map.Entry<String,String[]> entry : parameterMap.entrySet()){
                String[] vs = entry.getValue();
                String value = vs!=null&&vs.length>0?vs[0]:null;
                parameters.put(entry.getKey(),value);
            }
        }
        return parameters;
    }

    /**
     * 查询出对应的所有属性
     * @param domainClazz
     * @return
     */
    private Set<String> getDomainProperties(Class<?> domainClazz){
        String domainClazzName = domainClazz.getName();
        Set<String> paramNames = new HashSet<String>();
//        List<FieldClass> paramClasss = new ArrayList<>();
        if (cachedDomainFieldNames.containsKey(domainClazzName)) {
            paramNames = cachedDomainFieldNames.get(domainClazzName);
        }else {
            ConditionFieldCallback conditionFieldCallback = new ConditionFieldCallback("");
            ReflectionUtils.doWithFields(domainClazz, conditionFieldCallback);
            paramNames.addAll(conditionFieldCallback.getFieldNames());
            cachedDomainFieldNames.put(domainClazzName, paramNames);

//            paramClasss.addAll(conditionFieldCallback.getFieldClasss());
//            this.cachedDomainFieldClasss.put(domainClazzName, paramClasss);
        }
        return paramNames;
    }

    private List<FieldClass> getDomainParamClasss(Class<?> domainClazz){
        String domainClazzName = domainClazz.getName();
        List<FieldClass> paramClasss = new ArrayList<>();
        if (cachedDomainFieldClasss.containsKey(domainClazzName)) {
            paramClasss = cachedDomainFieldClasss.get(domainClazzName);
        }else {
            getDomainProperties(domainClazz);
        }
        return paramClasss;
    }


    private class ConditionFieldCallback implements ReflectionUtils.FieldCallback{
        private List<String> fieldNames = new ArrayList();
//        private List<FieldClass> fieldClasss = new ArrayList();
        private String prefix = "";

        public ConditionFieldCallback(String prefix) {
            this.prefix = Objects.toString(prefix,"");
        }

        public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException{
            String fieldName = field.getName();
            Id tableId = field.getAnnotation(Id.class);
            if (tableId!=null) {
                this.fieldNames.add(this.prefix + fieldName);
//                this.fieldClasss.add(new FieldClass(fieldName,field.getType()));
            }else {
                Class fieldClass = field.getType();
                if (Domain.class.isAssignableFrom(fieldClass)){
                    if (this.prefix.split("\\.").length > 3) {
                        return;
                    }
                    ConditionFieldCallback conditionFieldCallback = new ConditionFieldCallback( this.prefix + field.getName() + ".");
                    ReflectionUtils.doWithFields(fieldClass, conditionFieldCallback);
                    this.fieldNames.addAll(conditionFieldCallback.getFieldNames());
//                    this.fieldClasss.addAll(conditionFieldCallback.getFieldClasss());
                }
            }
        }
        public List<String> getFieldNames() {
            return this.fieldNames;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private class FieldClass {
        private String fileName;
        private Class<?> clazz;

    }
}
