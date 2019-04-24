package com.sucl.sbjms.core.method.support;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sucl.sbjms.core.method.annotation.QuerySort;
import com.sucl.sbjms.core.orm.jpa.JpaSort;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {_sort:[prop:asc,prop:desc]}
 * {_sort:'prop:asc;prop:asc'}
 * @author sucl
 * @date 2019/4/3
 */
public class SortHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    public static final String SORT = "_sort";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(QuerySort.class) &&
                Sort.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String sortStr = nativeWebRequest.getParameter(SORT);
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> sorts = null;
        if(sortStr != null){
            try {
                sorts = objectMapper.readValue(sortStr, new TypeReference<List<String>>() { });
            } catch (IOException e) {
                //
            }
            if(sorts==null){
                String[] sortArr = sortStr.split(";");
                sorts = Arrays.asList(sortArr);
            }
        }

        return new JpaSort(buildOrder(sorts));
    }

    private List<Sort.Order> buildOrder(List<String> sorts){
        List<Sort.Order> orders = new ArrayList<>();
        if(sorts!=null){
            for(String sort : sorts){
                if(sort.indexOf(":")!=-1){//prop:direct
                    String[] _sorts = sort.split(":");
                    orders.add(new Sort.Order(Sort.Direction.valueOf(_sorts[1]),_sorts[0]));
                }else{
                    orders.add(new Sort.Order(sort));
                }
            }
        }
        return orders;
    }
}
