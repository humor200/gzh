package com.zhw.weixin.cons;

/**
 * Created by zhanghongwei on 17/11/6.
 */
public class Common {

    public static final String addGupiao = "+";

    public static final String removeGupiao = "-";

    public static final String listGupiao = "147";

    public static final String comment = "@";

    public static String buildCommonMsg() {
        StringBuilder result = new StringBuilder();
        result.append("\r\n");
        result.append("输入“"+Common.listGupiao+"”列出您的所有股票\r\n");
        result.append("输入“"+Common.addGupiao+"股票编码”可直接添加或者展示您的自选股\r\n");
        result.append("输入“"+Common.removeGupiao+"股票编码”可隐藏您的自选股\r\n");
        return result.toString();
    }

    public static String buildDetailMsg() {
        StringBuilder result = new StringBuilder();
        result.append("输入").append(Common.comment).append("开头的文字直接添加您的判断!");
        return result.toString();
    }

}
