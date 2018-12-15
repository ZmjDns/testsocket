package com.zmj.mvp.testsocket.utils;

import java.text.SimpleDateFormat;

/**
 * @author Zmj
 * @date 2018/12/15
 */
public class DateUtils {
    public static String millins2Date(Long seconds){
        if (seconds == null || seconds < 1){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(seconds/1000);
    }
}
