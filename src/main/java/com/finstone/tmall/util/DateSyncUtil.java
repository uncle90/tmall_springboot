package com.finstone.tmall.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类，同步、线程安全
 */
public class DateSyncUtil {

    //静态变量，避免重复创建消耗过多资源
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    //格式化字符串 ==> 日期
    public static Date parse(String dateStr) throws ParseException {
        synchronized (sdf){
            return sdf.parse(dateStr);
        }
    }

    //日期 ==> 格式化字符串
    public static String format(Date date){
        synchronized (sdf){
            return sdf.format(date);
        }
    }

}
