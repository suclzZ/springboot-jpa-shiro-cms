package com.sucl.sbjms.core.rem;

import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @author sucl
 * @date 2019/4/2
 */
public abstract class ExceptionUtils {

    public static Message getMessage(Exception ex, MessageSource messageSource, Locale locale){
        if ((ex instanceof ExceptionMessage)) {
            return ((ExceptionMessage)ex).getExceptionMessage(messageSource, locale);
        }
        Throwable cause = ex.getCause();
        while(cause!=null){
            if(cause instanceof ExceptionMessage){
                return ((ExceptionMessage)ex).getExceptionMessage(messageSource, locale);
            }
            cause = cause.getCause();
        }
        return new Message(Message.FAILURE_CODE, ex.getMessage());
    }

    public static Throwable getRootCause(Throwable original) {
        if (original == null) {
            return null;
        }
        Throwable rootCause = null;
        Throwable cause = original.getCause();
        while (cause != null && cause != rootCause) {
            rootCause = cause;
            cause = cause.getCause();
        }
        return rootCause;
    }
}
