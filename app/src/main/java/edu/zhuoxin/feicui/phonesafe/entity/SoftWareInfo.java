package edu.zhuoxin.feicui.phonesafe.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/12/20.
 */

public class SoftWareInfo {
    private String label;//应用名
    private Drawable icon;//应用图标
    private String packageName; //应用包名
    private String version; //版本号
    private boolean isCheck ; //是否选中
    private boolean isDelete; //能否删除

    public SoftWareInfo(String label, Drawable icon, String packageName,
                        String version, boolean isCheck, boolean isDelete) {
        this.label = label;
        this.icon = icon;
        this.packageName = packageName;
        this.version = version;
        this.isCheck = isCheck;
        this.isDelete = isDelete;
    }

    public String getLabel() {
        return label;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getVersion() {
        return version;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "SoftWareInfo{" +
                "label='" + label + '\'' +
                ", icon=" + icon +
                ", packageName='" + packageName + '\'' +
                ", version='" + version + '\'' +
                ", isCheck=" + isCheck +
                ", isDelete=" + isDelete +
                '}';
    }
}
