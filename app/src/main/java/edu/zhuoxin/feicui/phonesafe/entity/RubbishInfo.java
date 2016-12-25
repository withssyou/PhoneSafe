package edu.zhuoxin.feicui.phonesafe.entity;

import java.io.File;

/**
 * Created by Administrator on 2016/12/23.
 */
public class RubbishInfo {
    private boolean isCheck;
    private File file;
    /**文件图标名*/
    private String iconName;
    /**MiniType类型*/
    private String fileType;

    public RubbishInfo(File file, String fileName, String fileType) {
        this.file = file;
        this.iconName = fileName;
        this.fileType = fileType;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public File getFile() {
        return file;
    }

    public String getIconName() {
        return iconName;
    }

    public String getFileType() {
        return fileType;
    }

    @Override
    public String toString() {
        return "RubbishInfo{" +
                "isCheck=" + isCheck +
                ", file=" + file +
                ", iconName='" + iconName + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
