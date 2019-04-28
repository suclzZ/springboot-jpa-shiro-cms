package com.sucl.sbjms.core.method.convert;

import com.sucl.sbjms.core.util.DateUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * demo
 * @author sucl
 * @date 2019/4/3
 */
@Component
public class DateStringConvert implements Converter<Date,String> {

    @Override
    public String convert(Date source) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(source);
    }
}
