package com.sucl.sbjms.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sucl
 * @date 2019/4/23
 */
public class DateUtils {
    public static final String FORMATE_SLASH = "yyyy-MM-dd";
    public static final String FORMATE_HYPHEN = "yyyy/MM/dd";

    public static boolean isDate(String date){
        Pattern pattern = Pattern.compile("\\d{4}(-|/)\\d{2}(-|/)\\d{2}");
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public static void main(String[] args) {
        System.out.println(isDate("2018-01-01"));
    }

    public static Object getDate(String value) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATE_SLASH);
        Date date = null;
        try {
            date = sdf.parse(value);
        } catch (ParseException e) {
            SimpleDateFormat sdf2 = new SimpleDateFormat(FORMATE_HYPHEN);
            try {
                date = sdf2.parse(value);
            } catch (ParseException e1) {
                throw new RuntimeException(String.format("不支持的日期转换【%s】",value));
            }
        }
        return date;
    }
}
