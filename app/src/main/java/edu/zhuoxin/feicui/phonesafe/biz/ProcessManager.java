package edu.zhuoxin.feicui.phonesafe.biz;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/26.
 */

public class ProcessManager {
    /**
     * 获取第三方正在运行的进程
     *
     * @param context
     */
    public static List<ActivityManager.RunningAppProcessInfo> getRunningProcess(Context context) {
        //获取ActivityManager
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获取所有进程
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        //判断是否为第三方

        return list;
    }
}
