package com.sucl.sbjms.core.rem;

import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @author sucl
 * @date 2019/4/2
 */
public interface ExceptionMessage {

    Message getExceptionMessage();

    Message getExceptionMessage(MessageSource messageSource, Locale locale);

}
