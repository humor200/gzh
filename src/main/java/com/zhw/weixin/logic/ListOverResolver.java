package com.zhw.weixin.logic;

import com.zhw.weixin.bean.ResolverResult;
import com.zhw.weixin.bean.UserContext;
import com.zhw.weixin.cons.Common;
import com.zhw.weixin.entity.GupiaoEvent;
import com.zhw.weixin.entity.Mygupiao;
import com.zhw.weixin.enums.CommandTypeEnum;
import com.zhw.weixin.service.GupiaoEventService;
import com.zhw.weixin.service.MygupiaoService;
import com.zhw.weixin.util.DateUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghongwei on 18/1/5.
 */
@Component
public class ListOverResolver implements ICommandResolver{

    private Logger logger = LoggerFactory.getLogger(ListOverResolver.class);

    @Autowired
    private CommandRouter commandRouter;

    @Autowired
    private MygupiaoService mygupiaoService;

    @Autowired
    private GupiaoEventService gupiaoEventService;

    @Autowired
    private GupiaoDetailResolver gupiaoDetailResolver;

    @Override
    public boolean contextMake(Object logicResult, String username, UserContext currentContext) {
        try {
            Map<String, String> map = new HashMap<String, String>();
            List<Mygupiao> mygupiaoList = (List<Mygupiao>) logicResult;
            int index = 1;
            for (Mygupiao mygupiao : mygupiaoList) {
                map.put(String.valueOf(index), mygupiao.getGupiaoCode());
                index++;
            }
            JSONObject jsonObject = JSONObject.fromObject(map);
            UserContext userContext = new UserContext();
            userContext.setUsername(username);
            userContext.setCommand(CommandTypeEnum.ListOver.getType());
            userContext.setLastJson(jsonObject.toString());
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
                return "指令过期\r\n" + "输入“"+Common.listGupiao+"”列出您的所有股票\r\n 输入“"+Common.addGupiao+"股票编码”添加您的自选股";
            }
            JSONObject jsonObject = JSONObject.fromObject(context.getLastJson());
            Map<String, String> map = (Map)JSONObject.toBean(jsonObject, Map.class);
            if (map.containsKey(inputText)) {
                return realShowDetail(context.getUsername(), map.get(inputText), context);
            } else {
                return "错误的指令" + "输入“"+Common.listGupiao+"”列出您的所有股票\r\n 输入“"+Common.addGupiao+"股票编码”添加您的自选股";
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "出错了";
        }
    }

    public String realShowDetail(String username, String code, UserContext currentContext) {
        StringBuilder result = new StringBuilder();
        Mygupiao mygupiao = mygupiaoService.getByUsernameAndCode(username, code);
        if(mygupiao == null) {
            result.append("对不起，您没有这只股票,请添加到自选股里");
        } else {
            result.append("**代码：").append(mygupiao.getGupiaoCode()).append(", 名称:").append(mygupiao.getGupiaoName());
            result.append(", 添加时股价:").append(mygupiao.getMoney()).append(", 添加时间:").append(DateUtil.stampToDate(mygupiao.getCtime())).append("**\r\n");
            result.append("----------------------------\r\n");

            List<GupiaoEvent> gupiaoEventList = gupiaoEventService.getList(mygupiao.getId());
            if (gupiaoEventList.size() == 0) {
                result.append("对不起，您没有关于这只股票的日记!\r\n");
            } else {
                result.append("股票日记：\r\n");

                for (GupiaoEvent event : gupiaoEventList) {
                    result.append("").append(event.getTypeForShow());
                    result.append(" : ").append(event.getContent());
                    result.append("\r\n股价记录:").append(event.getMoney());
                    result.append("\r\n时间：").append(DateUtil.stampToDate(event.getCtime()));
                    result.append("\n----------------------------\r\n");
                }
                result.append("\r\n");
            }
            result.append("\r\n");
            result.append("输入").append(Common.comment).append("开头的文字直接添加您的判断!");
            gupiaoDetailResolver.contextMake(mygupiao.getGupiaoCode(), username, null);
        }
        return result.toString();
    }
}
