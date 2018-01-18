package com.zhw.weixin.enums;

/**
 * Created by zhanghongwei on 17/12/29.
 */
/**
 * 使用枚举表述常量数据字典
 */
public enum UserMode {

    ROBOT(1, "图灵机器人"), GUPIAO(0, "股票");

    private int value;

    private String desc;

    private UserMode(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}