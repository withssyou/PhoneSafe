package edu.zhuoxin.feicui.phonesafe.entity;

/**
 * Created by Administrator on 2016/12/15.
 */

public class TelNumber {
    private String name;
    private String number;

    public TelNumber(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "TelNumber{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
