package edu.zhuoxin.feicui.phonesafe.entity;

/**
 * Created by Administrator on 2016/12/15.
 */

public class ClassListInfo {
    private String name;
    private int idx;

    public ClassListInfo(String name, int idx) {
        this.name = name;
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public int getIdx() {
        return idx;
    }

    @Override
    public String toString() {
        return "ClassListInfo{" +
                "name='" + name + '\'' +
                ", idx=" + idx +
                '}';
    }
}
