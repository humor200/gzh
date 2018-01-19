package com.zhw.weixin.logic.resolver;

import com.zhw.weixin.bean.UserContext;
import com.zhw.weixin.cons.Common;
import com.zhw.weixin.entity.Mygupiao;
import com.zhw.weixin.enums.CommandTypeEnum;
import com.zhw.weixin.logic.CommandRouter;
import com.zhw.weixin.service.GupiaoEventService;
import com.zhw.weixin.service.MygupiaoService;
import com.zhw.weixin.util.SinaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhanghongwei on 18/1/5.
 */
@Component
public class GupiaoDetailResolver implements ICommandResolver{

    private Logger logger = LoggerFactory.getLogger(GupiaoDetailResolver.class);

    @Autowired
    private CommandRouter commandRouter;

    @Autowired
    private MygupiaoService mygupiaoService;

    @Autowired
    private GupiaoEventService gupiaoEventService;

    @Autowired
    private ListOverResolver listOverResolver;

    @Override
    public boolean contextMake(Object logicResult, String username, UserContext currentContext) {
        try {
            String code = (String)logicResult;
            UserContext userContext = new UserContext();
            userContext.setUsername(username);
            userContext.setCommand(CommandTypeEnum.GupiaoDetail.getType());
            userContext.setLastJson(code);
            commandRouter.putUserContext(username, userContext);
            logger.info("新添加用户context成功,username={},context={}", username, userContext.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return true;
    }

    @Override
    public String resolverAndInvoke(UserContext context, String inputText) {
        try {
            if (context == null) {
                return "指令可能过期了";
            }
            if (inputText.startsWith(Common.comment)) {
                StringBuilder result = new StringBuilder();
                Mygupiao mygupiao = mygupiaoService.getByUsernameAndCode(context.getUsername(), context.getLastJson());
                if(mygupiao == null) {
                    result.append("什么？没有这个股票？不科学，context丢失");
                } else {
                    double price = SinaUtil.getGupiaoPrice(mygupiao.getGupiaoCode()).getPrice();
                    gupiaoEventService.addGupiaoEvent(mygupiao.getId(), 1, inputText.substring(1), price);
                    result.append("您已成功完成点评\r\n").append("\r\n");
                    String nextResult = listOverResolver.realShowDetail(context.getUsername(), mygupiao.getGupiaoCode(), context);
                    result.append(nextResult);

                }
                return result.toString();
            } else {
                return "错误的指令" + Common.buildDetailMsg();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "出错了";
        }
    }
}
