package com.zhw.weixin.logic.resolver;

import com.zhw.weixin.bean.ResolverResult;
import com.zhw.weixin.bean.UserContext;
import org.springframework.stereotype.Component;

/**
 * Created by zhanghongwei on 18/1/5.
 */
public interface ICommandResolver {

    public boolean contextMake(Object logicResult, String username, UserContext currentContext);

    public String resolverAndInvoke(UserContext context, String inputText);
}
