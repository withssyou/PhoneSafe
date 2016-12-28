package edu.zhuoxin.feicui.phonesafe.api;

/**
 * Created by Administrator on 2016/12/28.
 */

public interface SearchFileCallback {
    void searching(long fileSize);//检索到文件时会调用的方法
    void ending(boolean isEnd);//当文件检索完毕时，回调的方法
}
