package edu.zhuoxin.feicui.phonesafe.biz;

import java.io.File;

/**
 * Created by Administrator on 2016/12/24.
 *  文件管理业务类
 */

public class FileManager {
    public static long getFileSize(File file){
        long size = 0;
        if (!file.isDirectory()){ // 是文件
            return file.length();
        }else {
            for (File f : file.listFiles()){
                if (f.isDirectory()){
                    size += getFileSize(f);
                }else {
                    size += f.length();
                }
            }
        }
        return size;
    }
    /**文件删除的方法*/
    public static void deleteFile(File file){
        if (!file.isDirectory()){
            file.delete();
        }else {
            for (File f :file.listFiles()) {
                if (f.isDirectory()){
                    deleteFile(f);
                }else {
                    f.delete();
                }
            }
        }
    }
}
