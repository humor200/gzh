package com.zhw.weixin.bean;

/**
 * 用户上下文
 * Created by zhanghongwei on 18/1/5.
 */
public class UserContext {

    private String lastJson;

    private int command;

    private String username;

    private UserContext prev;       //暂时不用

    public String getLastJson() {
        return lastJson;
    }

    public void setLastJson(String lastJson) {
        this.lastJson = lastJson;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserContext getPrev() {
        return prev;
    }

    public void setPrev(UserContext prev) {
        this.prev = prev;
    }

    @Override
    public String toString() {
        return "UserContext{" +
                "lastJson='" + lastJson + '\'' +
                ", command=" + command +
                ", username='" + username + '\'' +
                ", prev=" + prev +
                '}';
    }
}
