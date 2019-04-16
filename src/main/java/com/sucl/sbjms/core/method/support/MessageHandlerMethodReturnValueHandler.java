package com.sucl.sbjms.core.method.support;

import com.sucl.sbjms.core.rem.Message;
import com.sucl.sbjms.core.rem.ResponseInfo;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 包装RequestResponseBodyMethodProcessor，继续使用@ResponseBody注解，但是对返回结构再次封装
 * @author sucl
 * @date 2019/4/8
 */
public class MessageHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {
    private HandlerMethodReturnValueHandler delegate;

    public MessageHandlerMethodReturnValueHandler(HandlerMethodReturnValueHandler handlerMethodReturnValueHandler){
        this.delegate = handlerMethodReturnValueHandler;
    }

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return delegate.supportsReturnType(methodParameter);
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
//        methodParameter.getMethodAnnotation()
        if(o==null || !Message.class.isAssignableFrom(o.getClass())){
            o = new ResponseInfo(o);
        }
        delegate.handleReturnValue(o,methodParameter,modelAndViewContainer,nativeWebRequest);
    }
}
