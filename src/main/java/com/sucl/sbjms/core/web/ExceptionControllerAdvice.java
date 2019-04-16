package com.sucl.sbjms.core.web;

import com.sucl.sbjms.core.rem.BusException;
import com.sucl.sbjms.core.rem.ExceptionUtils;
import com.sucl.sbjms.core.rem.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 处理contriller抛出的异常
 * @ExceptionHandler、@InitBinder、@ModelAttribute
 * @author sucl
 * @date 2019/4/10
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
    @Autowired
    private MessageSource messageSource;

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Message handler(Exception ex){
        if(!BusException.class.isAssignableFrom(ex.getClass())){
            ex.printStackTrace();
        }
        return ExceptionUtils.getMessage(ex,messageSource,LocaleContextHolder.getLocale());
    }
}
