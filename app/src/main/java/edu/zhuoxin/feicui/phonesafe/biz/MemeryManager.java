package edu.zhuoxin.feicui.phonesafe.biz;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;

/**
 * Created by Administrator on 2016/12/19.
 *  管理内存
 */

public class MemeryManager {
    /**
     *  获取内存详情
     * @param context
     * @return
     */
    public static ActivityManager.MemoryInfo getMemeryInfo(Context context){
        //打开系统服务，获取ActivityManager
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //内存详情
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        //activitymanager将内存信息，存入info中
        am.getMemoryInfo(info);
        return info;
    }
    /**获取系统的总内存*/
    public static long getAllMemeray(Context context){
        return getMemeryInfo(context).totalMem;
    }
    /**获取可用内存*/
    public static long getAvailMemary(Context context){
        return getMemeryInfo(context).availMem;
    }
    /**内存大小格式化*/
    public static String getFamaterMem(Context context,long l){
        return Formatter.formatFileSize(context,l);
    }
    /**获取外置sd卡的根路径*/
    public static  String getSDstoragePath(){
       String state =  Environment.getExternalStorageState();
            //内存卡挂载
        if (!state.equals(Environment.MEDIA_MOUNTED)){
            return  null;
        }else {
           return  Environment.getExternalStorageDirectory().getPath();
        }

    }
}
