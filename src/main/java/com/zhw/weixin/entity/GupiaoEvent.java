package com.zhw.weixin.entity;

/**
 * Created by zhanghongwei on 17/12/29.
 */
public class GupiaoEvent {
    private long id;//

    private long mygupiaoId;

    private int type;

    private String content;

    private double money;

    private int ctime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMygupiaoId() {
        return mygupiaoId;
    }

    public void setMygupiaoId(long mygupiaoId) {
        this.mygupiaoId = mygupiaoId;
    }

    public int getType() {
        return type;
    }

    public String getTypeForShow() {
        if (type == 1) {
            return "判断";
        } else if (type == 2) {
            return "买入";
        } else if (type == 3) {
            return "卖出";
        }
        return "未知";
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return "GupiaoEvent{" +
                "ctime=" + ctime +
                ", money=" + money +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", mygupiaoId=" + mygupiaoId +
                ", id=" + id +
                '}';
    }
}
