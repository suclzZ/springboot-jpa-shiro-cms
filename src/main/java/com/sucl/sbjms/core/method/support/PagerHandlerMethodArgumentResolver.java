package com.sucl.sbjms.core.method.support;

import com.sucl.sbjms.core.orm.Pager;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author sucl
 * @date 2019/4/3
 */
public class PagerHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    public static final String PAGER_PAGEINDEX = "pager:pageIndex";
    public static final String PAGER_PAGESIZE = "pager:pageSize";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return Pager.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String pageIndex = nativeWebRequest.getParameter(PAGER_PAGEINDEX);
        String pageSize = nativeWebRequest.getParameter(PAGER_PAGESIZE);
        return new Pager(pageIndex,pageSize);
    }
}
