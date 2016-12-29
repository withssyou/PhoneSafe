package edu.zhuoxin.feicui.phonesafe.biz;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.zhuoxin.feicui.phonesafe.api.SearchFileCallback;
import edu.zhuoxin.feicui.phonesafe.entity.FileInfo;
import edu.zhuoxin.feicui.phonesafe.utils.FileTypeUtil;

/**
 * Created by Administrator on 2016/12/24.
 * 文件管理业务类
 */

public class FileManager {
    /**
     * 单例模式
     */
    private FileManager() {
        init();
    }

    private static FileManager manager = new FileManager();

    public static FileManager getFileManager() {
        return manager;
    }

    /**
     * 内置内存卡
     */
    private static File inSdStorageFile;
    /**
     * 外置内存卡
     */
    private static File outSdStorageFile; //如果外置内存卡不存在,

    /**静态代码块初始化文件根路径*/
    static {
        //如果有手机内置 sdcard  卡路径, 进行内置 File  实例化( 防止 File file =new File(null))
        if (MemeryManager.getSDstoragePath() != null) {
            inSdStorageFile = null;
            inSdStorageFile = new File(MemeryManager.getSDstoragePath());
        }
        //如果有手机外置 sdcard  卡路径, 进行内置 File  实例化( 防止 File file =new File(null))
        if (MemeryManager.getOutSDstoragePath() != null) {
            outSdStorageFile = null;
            outSdStorageFile = new File(MemeryManager.getOutSDstoragePath());
        }
    }

    /**
     * 存储所有文件的集合
     */
    private List<FileInfo> allFile;
    private List<FileInfo> docFile;
    private List<FileInfo> videofFile;
    private List<FileInfo> audioFile;
    private List<FileInfo> imageFile;
    private List<FileInfo> zipFile;
    private List<FileInfo> apkFile;
    private long allFileSize;
    private long docFileSize;
    private long videofFileSize;
    private long audioFileSize;
    private long imageFileSize;
    private long apkFileSize;
    private long zipFileSize;

    /**
     * 回调的接口
     */
    private SearchFileCallback listener;
    /**
     * 判读是否在检索文件
     */
    private boolean isSearching = false;
    /**
     * 初始化集合
     */
    private void init() {
        allFile = new ArrayList<>();
        docFile = new ArrayList<>();
        videofFile = new ArrayList<>();
        audioFile = new ArrayList<>();
        imageFile = new ArrayList<>();
        zipFile = new ArrayList<>();
        apkFile = new ArrayList<>();
    }

    //初始化数据
    private void initData() {
        //清空集合
        allFile.clear();
        docFile.clear();
        videofFile.clear();
        audioFile.clear();
        imageFile.clear();
        zipFile.clear();
        apkFile.clear();
        //文件大小置为零
        allFileSize = 0;
        docFileSize = 0;
        videofFileSize = 0;
        audioFileSize = 0;
        imageFileSize = 0;
        apkFileSize = 0;
        zipFileSize = 0;
    }

    /**
     * 文件检索
     */
    public void searchFiles() {
        if (isSearching) {
            return;
        } else {
            initData();
            search(outSdStorageFile ,false);//传入true，让结果可以反馈文件检索结束
            search(inSdStorageFile ,true);//传入false，不让结果反馈文件检索

        }
    }

    /**
     * 检索具体文件
     * @param file      要检索的文件
     * @param endFlag   用来判断是否回调文件检索结束方法true 表示回调，false表示不回调
     */
    private void search(File file ,boolean endFlag) {
        //#1.文件异常的情况：文件为空，或者不能读，或者不存在
        if (file == null || !file.canRead() || !file.exists()){
            if (endFlag){
                searchdeEnd(true);//表示文件检索异常结束
            } return;

        }else if (file.isDirectory()){  //#2.file为文件夹的情况
            File[] files = file.listFiles();
            if (files == null || files.length <= 0) {
                return;
            }
            for (File f : files){
                if (f != null && f.length() > 0){
                    search(f,false);//递归检索文件夹中的文件，传入false，不让文件检索反馈结束
                }
            }
            if (endFlag){
                searchdeEnd(false);//如果可以反馈结束，反馈正常结束
            }
        }else {

            //#3.file为文件的情况
            if (file.getName().lastIndexOf(".") == -1){
               return;
            }
            if (file.length() <= 0){
                return;
            }
            //获取文件的图像及类型
            String[] iconAndName = FileTypeUtil.getFileIconAndTypeName(file);
            String iconName = iconAndName[0];
            String typeName = iconAndName[1];
            FileInfo info = new FileInfo(file,iconName,typeName);
            //获取文件大小
            long size = getFileSize(file);
            // file.length();//文件 而非文件夹 所以不需要递归
            //添加集合
            allFile.add(info);
            allFileSize += size;
            //分类
            if (typeName.equals(FileTypeUtil.TYPE_IMAGE)){
                imageFile.add(info);
                imageFileSize += size;
            }
            if (typeName.equals(FileTypeUtil.TYPE_APK)){
                apkFile.add(info);
                apkFileSize += size;
            }
            if (typeName.equals(FileTypeUtil.TYPE_AUDIO)){
                audioFile.add(info);
                audioFileSize += size;
            }
            if (typeName.equals(FileTypeUtil.TYPE_TXT)){
                docFile.add(info);
                docFileSize += size;
            }
            if (typeName.equals(FileTypeUtil.TYPE_VIDEO)){
                videofFile.add(info);
                videofFileSize += size;
            }
            if (typeName.equals(FileTypeUtil.TYPE_ZIP)){
                zipFile.add(info);
                zipFileSize += size;
            }
            //没检索到一个文件，回调一次方法
            searchedFile(allFileSize);
        }
    }

    /**
     * 检索到文件之后调用的方法
     */
    private void searchedFile(long fileSize) {
        if (listener != null) {
            listener.searching(fileSize);
        }
    }

    /**
     * 文件检索完毕之后调用的方法
     */
    private void searchdeEnd(boolean isEnd) {
        if (listener != null) {
            listener.ending(isEnd);
        }
    }

    /**
     * 获取文件大小
     */
    public static long getFileSize(File file) {
        long size = 0;
        if (!file.isDirectory()) { // 是文件
            return file.length();
        } else {
            for (File f : file.listFiles()) {
                if (f.isDirectory()) {
                    size += getFileSize(f);
                } else {
                    size += f.length();
                }
            }
        }
        return size;
    }

    /**
     * 文件删除的方法
     */
    public static void deleteFile(File file) {
        if (!file.isDirectory()) {
            file.delete();
        } else {
            for (File f : file.listFiles()) {
                if (f.isDirectory()) {
                    deleteFile(f);
                } else {
                    f.delete();
                }
            }
        }
    }
    /**设置本类的监听事件*/
    public void setListener(SearchFileCallback listener) {
        this.listener = listener;
    }

    public long getZipFileSize() {
        return zipFileSize;
    }

    public long getAllFileSize() {
        return allFileSize;
    }

    public long getDocFileSize() {
        return docFileSize;
    }

    public long getVideofFileSize() {
        return videofFileSize;
    }

    public long getAudioFileSize() {
        return audioFileSize;
    }

    public long getImageFileSize() {
        return imageFileSize;
    }

    public long getApkFileSize() {
        return apkFileSize;
    }

    public List<FileInfo> getAllFile() {
        return allFile;
    }

    public List<FileInfo> getDocFile() {
        return docFile;
    }

    public List<FileInfo> getVideofFile() {
        return videofFile;
    }

    public List<FileInfo> getAudioFile() {
        return audioFile;
    }

    public List<FileInfo> getImageFile() {
        return imageFile;
    }

    public List<FileInfo> getZipFile() {
        return zipFile;
    }

    public List<FileInfo> getApkFile() {
        return apkFile;
    }

    public void setZipFileSize(long zipFileSize) {
        this.zipFileSize = zipFileSize;
    }

    public void setAllFileSize(long allFileSize) {
        this.allFileSize = allFileSize;
    }

    public void setDocFileSize(long docFileSize) {
        this.docFileSize = docFileSize;
    }

    public void setVideofFileSize(long videofFileSize) {
        this.videofFileSize = videofFileSize;
    }

    public void setAudioFileSize(long audioFileSize) {
        this.audioFileSize = audioFileSize;
    }

    public void setImageFileSize(long imageFileSize) {
        this.imageFileSize = imageFileSize;
    }

    public void setApkFileSize(long apkFileSize) {
        this.apkFileSize = apkFileSize;
    }
}
