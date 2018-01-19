package com.zhw.weixin.enums;

/**
 * Created by zhanghongwei on 17/12/29.
 */

import com.zhw.weixin.logic.resolver.GupiaoDetailResolver;
import com.zhw.weixin.logic.resolver.ListOverResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用枚举表述常量数据字典
 */
public enum CommandTypeEnum {

    ListOver(1, ListOverResolver.class),GupiaoDetail(2, GupiaoDetailResolver.class)

    ;

    private int type;

    private Class commandResolver;

    private static Map<Integer, Class> nestMap = new HashMap<Integer, Class>();

    static {
        for (CommandTypeEnum e : CommandTypeEnum.values()) {
            nestMap.put(e.getType(), e.getCommandResolver());
        }
    }

    public static Class getResolver(int type) {
        return nestMap.get(type);
    }

    private CommandTypeEnum(int type, Class commandResolver) {
        this.type = type;
        this.commandResolver = commandResolver;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Class getCommandResolver() {
        return commandResolver;
    }

    public void setCommandResolver(Class commandResolver) {
        this.commandResolver = commandResolver;
    }
}