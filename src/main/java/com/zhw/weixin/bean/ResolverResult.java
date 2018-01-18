package com.zhw.weixin.bean;

/**
 * Created by zhanghongwei on 18/1/14.
 */
public class ResolverResult {
    private String returnText;

    private UserContext userContext;

    public String getReturnText() {
        return returnText;
    }

    public void setReturnText(String returnText) {
        this.returnText = returnText;
    }

    public UserContext getUserContext() {
        return userContext;
    }

    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }
}
