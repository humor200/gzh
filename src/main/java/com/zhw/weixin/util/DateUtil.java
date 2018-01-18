package com.zhw.weixin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhanghongwei on 17/12/31.
 */
public class DateUtil {

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(int timestamp){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp);
        res = simpleDateFormat.format((long)timestamp*1000);
        return res;
    }
}
