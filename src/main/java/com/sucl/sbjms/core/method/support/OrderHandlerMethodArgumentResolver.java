package com.sucl.sbjms.core.method.support;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sucl.sbjms.core.method.annotation.QueryOrder;
import com.sucl.sbjms.core.orm.Order;
import com.sucl.sbjms.core.orm.jpa.JpaOrder;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.*;

/**
 * @author sucl
 * @date 2019/4/3
 */
public class OrderHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    public static final String ORDER_PROPERTY = "order:property";
    public static final String ORDER_DIRECTION = "order:direction";// ASC DESC
    public static final String ORDER = "_order";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(QueryOrder.class) &&
                Collection.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
//        String property = nativeWebRequest.getParameter(ORDER_PROPERTY);
//        String direction = nativeWebRequest.getParameter(ORDER_DIRECTION);
//        return Arrays.asList(new JpaOrder(property,direction));

        String orderStr = nativeWebRequest.getParameter(ORDER);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, String>> sortList = null;
        if(orderStr != null){
            sortList = objectMapper.readValue(orderStr, new TypeReference<List<Map<String, String>>>() { });
        }
        if(sortList!=null){
            List<Order> orders = new ArrayList();
            for(Map smap : sortList){
                JpaOrder order = new JpaOrder();
                order.setProperty(Objects.toString(smap.get("property")));
                order.setDirection(Objects.toString( smap.get("direction")));
            }
            return orders;
        }else{
            return null;
        }
    }
}
