package com.sucl.sbjms.core.method.support;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sucl
 * @date 2019/4/8
 */
@Component
public class HandlerMethodReturnValueHandlerModifier implements InitializingBean {

    @Autowired(required = false)
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(requestMappingHandlerAdapter!=null){
            //UnmodifiableRandomAccessList
            List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
            List<HandlerMethodReturnValueHandler> handlerMethodReturnValueHandlers = new ArrayList<>(returnValueHandlers);
            replaceResponseBodyHandler(handlerMethodReturnValueHandlers);
            requestMappingHandlerAdapter.setReturnValueHandlers(handlerMethodReturnValueHandlers);
        }
    }

    private void replaceResponseBodyHandler(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        for(int i=0; i<returnValueHandlers.size(); i++){
            if(RequestResponseBodyMethodProcessor.class.isAssignableFrom(returnValueHandlers.get(i).getClass())){
                returnValueHandlers.set(i,new MessageHandlerMethodReturnValueHandler(returnValueHandlers.get(i)));
                break;
            }
        }
    }
}
