package edu.zhuoxin.feicui.phonesafe.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/29.
 */

public class TimeUtils {
    public static  String fomartTiem(long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
        String date = format.format(new Date(time));
        return date;
    }
}
