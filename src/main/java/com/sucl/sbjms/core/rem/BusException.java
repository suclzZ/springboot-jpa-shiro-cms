package com.sucl.sbjms.core.rem;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.NestedRuntimeException;
import org.springframework.util.StringUtils;

import java.util.Locale;

/**
 * @author sucl
 * @date 2019/4/2
 */
public class BusException extends NestedRuntimeException implements ExceptionMessage{
    private String code;
    private String i18n;
    private String[] args;

    public BusException(String msg) {
        this(msg,Message.FAILURE_CODE);
    }

    public BusException(String msg,String code) {
        super(msg);
        this.code = code;
    }

    public BusException(String i18n,String[] args) {
        super(i18n);
        this.i18n = i18n;
        this.args = args;
        this.code = Message.FAILURE_CODE;
    }

    public BusException(String msg, Throwable cause) {
        super(msg, cause);
    }

    @Override
    public Message getExceptionMessage() {
        return new Message(Message.FAILURE_CODE,getMessage());
    }

    @Override
    public Message getExceptionMessage(MessageSource messageSource, Locale locale) {
        if(messageSource!=null && !StringUtils.isEmpty(this.i18n)){
            if(locale==null){
                locale = Locale.getDefault();
            }
            try {
                return new Message(Message.FAILURE_CODE, messageSource.getMessage(this.i18n,args,locale));
            } catch (NoSuchMessageException e) {
                return new Message(Message.FAILURE_CODE,e.getMessage());
            }
        }
        return getExceptionMessage();
    }
}
