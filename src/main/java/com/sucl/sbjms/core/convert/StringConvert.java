package com.sucl.sbjms.core.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sucl
 * @date 2019/4/3
 */
@Component
public class StringConvert implements Converter<String,Date> {
    private static final String PREFIX = "DATE:";
    @Override
    public Date convert(String source) {
        if(source.indexOf(PREFIX)==0){
            source = source.substring(PREFIX.length());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return sdf.parse(source);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
