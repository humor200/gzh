package com.zhw.weixin.entity;

import java.util.Date;

/**
 * Created by zhanghongwei on 17/12/29.
 */
public class Mygupiao {
    private long id;//

    private String username;

    private String gupiaoCode;

    private String gupiaoName;

    private double money;

    private int ctime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGupiaoCode() {
        return gupiaoCode;
    }

    public void setGupiaoCode(String gupiaoCode) {
        this.gupiaoCode = gupiaoCode;
    }

    public String getGupiaoName() {
        return gupiaoName;
    }

    public void setGupiaoName(String gupiaoName) {
        this.gupiaoName = gupiaoName;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getCtime() {
        return ctime;
    }

    public void setCtime(int ctime) {
        this.ctime = ctime;
    }

    @Override
    public String toString() {
        return "Mygupiao{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", gupiaoCode='" + gupiaoCode + '\'' +
                ", gupiaoName='" + gupiaoName + '\'' +
                ", money=" + money +
                ", ctime=" + ctime +
                '}';
    }
}
