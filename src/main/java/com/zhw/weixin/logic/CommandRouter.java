package com.zhw.weixin.logic;

import com.zhw.weixin.bean.UserContext;
import com.zhw.weixin.cons.Common;
import com.zhw.weixin.entity.GupiaoEvent;
import com.zhw.weixin.entity.Mygupiao;
import com.zhw.weixin.enums.CommandTypeEnum;
import com.zhw.weixin.service.GupiaoEventService;
import com.zhw.weixin.service.MygupiaoService;
import com.zhw.weixin.util.DateUtil;
import com.zhw.weixin.util.SinaUtil;
import com.zhw.weixin.util.SpringContextUtils;
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
public class CommandRouter {

    private static Logger logger = LoggerFactory.getLogger(CommandRouter.class);

    //用户上下文对象
    private Map<String, UserContext> userContextMap = new HashMap<String, UserContext>();

    @Autowired
    private MygupiaoService mygupiaoService;

    @Autowired
    private GupiaoEventService gupiaoEventService;

    @Autowired
    private ListOverResolver listOverResolver;

    /**
     * 替换新的context
     * @param username
     * @param userContext
     * @return
     */
    public boolean putUserContext(String username, UserContext userContext) {
        userContextMap.put(username, userContext);
        return true;
    }

    /**
     * 输入路由
     * @param username
     * @param content
     * @return
     */
    public String route(String username, String content) {
        StringBuilder result = new StringBuilder();
        if (content.equals(Common.listGupiao)) {
            List<Mygupiao> mygupiaoList = mygupiaoService.getList(username);
            result.append("您共有").append(mygupiaoList.size()).append("只自选股。\r\n");
            result.append("\r\n");
            int index = 1;
            for (Mygupiao mygupiao : mygupiaoList) {
                result.append("(").append(index).append("),");
                result.append(mygupiao.getGupiaoCode()).append(",").append(mygupiao.getGupiaoName());
                result.append(",添加时股价:").append(mygupiao.getMoney()).append(",时间:").append(DateUtil.stampToDate(mygupiao.getCtime()));
                result.append("\r\n");
                index++;
            }
            result.append("\r\n");
            result.append("请输入数字编号查看该股票日记\r\n");
            result.append("输入“"+Common.addGupiao+"股票编码”可直接添加您的自选股\r\n");
            listOverResolver.contextMake(mygupiaoList, username, null);
        } else if (content.startsWith(Common.addGupiao)) {
            String code = content.substring(1);
            Mygupiao mygupiao = mygupiaoService.getByUsernameAndCode(username, code);
            if (mygupiao != null) {
                result.append("您已经存在该股票");
            } else {
                int resultCode = mygupiaoService.addMygupiao(username, code);
                if (resultCode == -1) {
                    result.append("错误的股票代码");
                } else {
                    result.append("您已成功添加" + code + "股票到您的自选股里!");
                }
            }
//        } else if (content.startsWith("show:")) {
//            String[] strings = content.split(":");
//            Mygupiao mygupiao = mygupiaoService.getByUsernameAndCode(username, strings[1]);
//            if(mygupiao == null) {
//                result.append("对不起，您没有这只股票,请添加到自选股里");
//            } else {
//
//                result.append("**股票代码：").append(mygupiao.getGupiaoCode()).append(",名称").append(mygupiao.getGupiaoName());
//                result.append(",添加时股价:").append(mygupiao.getMoney()).append(",添加时间:").append(DateUtil.stampToDate(mygupiao.getCtime())).append("**\r\n");
//                result.append("--------------------\r\n");
//
//                List<GupiaoEvent> gupiaoEventList = gupiaoEventService.getList(mygupiao.getId());
//                if (gupiaoEventList.size() == 0) {
//                    result.append("对不起，您没有关于这只股票的事件记录!");
//                }
//                for (GupiaoEvent event : gupiaoEventList) {
//                    result.append("类型：").append(event.getTypeForShow());
//                    result.append(",内容:").append(event.getContent());
//                    result.append(",当时股价:").append(event.getMoney());
//                    result.append(",时间：").append(DateUtil.stampToDate(event.getCtime()));
//                    result.append("\r\n");
//                }
//            }
//
//        } else if (content.startsWith("1:")) {
//            String[] strings = content.split(":");
//            Mygupiao mygupiao = mygupiaoService.getByUsernameAndCode(username, strings[1]);
//            if(mygupiao == null) {
//                result.append("对不起，您没有这只股票,请添加到自选股里");
//            } else {
//                gupiaoEventService.addGupiaoEvent(mygupiao.getId(), 1, strings[2], SinaUtil.getGupiaoPrice(mygupiao.getGupiaoCode()).getPrice());
//                result.append("您已成功完成点评");
//            }
        } else {
            UserContext userContext = userContextMap.get(username);
            if (userContext == null) {
                logger.info("userContext is null,username={}", username);
                return "错误的指令\r\n输入"+Common.listGupiao+"列出您的所有自选股";
            }

            //从上下文中找到命令并解析
            Class resolverClass = CommandTypeEnum.getResolver(userContext.getCommand());
            Object resolverBean = SpringContextUtils.getBeanByClass(resolverClass);
            if (resolverBean instanceof ICommandResolver) {
                return ((ICommandResolver) resolverBean).resolverAndInvoke(userContext, content);
            } else {
                logger.error("错误的编码,username:{}, command:{}", username, userContext.getCommand());
                return "输入错误";
            }
        }
        return result.toString();
    }
}
