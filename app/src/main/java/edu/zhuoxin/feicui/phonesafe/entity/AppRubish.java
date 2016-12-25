package edu.zhuoxin.feicui.phonesafe.entity;

import android.graphics.drawable.Drawable;

import java.io.File;

/**
 * Created by Administrator on 2016/12/23.
 */
public class AppRubish {
    private boolean ischeck;
    private String ChinestName;
    private String EnglishName;
    private String packageName;
    private String filePath;
    private Drawable icon;
    private String fileSize;
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public AppRubish(String chinestName, String englishName, String packageName, String filePath) {
        ChinestName = chinestName;
        EnglishName = englishName;
        this.packageName = packageName;
        this.filePath = filePath;

    }

    public AppRubish(boolean ischeck, Drawable icon, String fileSize, String englishName,File file) {
        this.ischeck = ischeck;
        this.icon = icon;
        this.fileSize = fileSize;
        this.EnglishName = englishName;
        this.file = file;
    }

    public String getFileSize() {
        return fileSize;
    }
    public String getChinestName() {
        return ChinestName;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean ischeck() {
        return ischeck;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    @Override
    public String toString() {
        return "AppRubish{" +
                "ChinestName='" + ChinestName + '\'' +
                ", EnglishName='" + EnglishName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
