package edu.zhuoxin.feicui.phonesafe.entity;

import android.app.ActivityManager;

/**
 * Created by Administrator on 2016/12/26.
 */

public class ProcessInfo {
    private boolean isCheck;
    private ActivityManager.RunningAppProcessInfo info;
    private int tag;

    public int getTag() {
        return tag;
    }

    public ProcessInfo(boolean isCheck, int tag, ActivityManager.RunningAppProcessInfo info) {
        this.isCheck = isCheck;
        this.info = info;
        this.tag = tag;

    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public ActivityManager.RunningAppProcessInfo getInfo() {
        return info;
    }

    public void setInfo(ActivityManager.RunningAppProcessInfo info) {
        this.info = info;
    }
}
