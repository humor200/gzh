package com.zhw.weixin.service;

/**
 * Created by zhanghongwei on 17/12/29.
 */

import com.zhw.weixin.bean.ReceiveXmlEntity;
import com.zhw.weixin.cons.Common;
import com.zhw.weixin.cons.Cons;
import com.zhw.weixin.dao.AppointmentDao;
import com.zhw.weixin.dao.BookDao;
import com.zhw.weixin.dto.AppointExecution;
import com.zhw.weixin.entity.Appointment;
import com.zhw.weixin.entity.Book;
import com.zhw.weixin.enums.AppointStateEnum;
import com.zhw.weixin.enums.UserMode;
import com.zhw.weixin.logic.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MainProcessService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserModeCache userModeCache;

    @Autowired
    private MygupiaoService mygupiaoService;

    @Autowired
    private CommandRouter commandRouter;

    /**
     * xml接受处理
     * @param xml
     * @return
     */
    public String processWechatMag(String xml){
        /** 解析xml数据 */
        ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);

        /** 以文本消息为例，调用图灵机器人api接口，获取回复内容 */
        String result = "";

        String msgType = xmlEntity.getMsgType();
        String userName = xmlEntity.getFromUserName();

        if(Cons.REQ_MESSAGE_TYPE_TEXT.equals(msgType)){

            //文字切换模式
            if(xmlEntity.getContent().equals("机器人")){
                userModeCache.changeMode(userName, UserMode.ROBOT);
                result = "您已切换至机器人聊天模式，尽情聊天吧。";
            } else if(xmlEntity.getContent().equals("股票")) {
                userModeCache.changeMode(userName, UserMode.GUPIAO);
                result = "您已切换至股票模式，祝您早日发财。"+ Common.listGupiao +"：列出自己的股票列表";
            } else {
                //判断是股票模式，还是机器人模式
                UserMode mode = userModeCache.getMyMode(userName);
                if (mode == UserMode.ROBOT) {
                    result = new TulingApiProcess().getTulingResult(xmlEntity.getContent());
                } else {
                    //result = mygupiaoService.mygupiaoLogic(xmlEntity);
                    result = commandRouter.route(userName, xmlEntity.getContent());
                    logger.info(result);
                }
            }
        }

        // 图片消息
        else if (msgType.equals(Cons.REQ_MESSAGE_TYPE_IMAGE)) {
            result = "图片格式暂时不支持！";
        }
        // 语音消息
        else if (msgType.equals(Cons.REQ_MESSAGE_TYPE_VOICE)) {
            result = "你的普通话也太不标准了吧？我都听不懂，要不你说英语吧！";
        }
        // 视频消息
        else if (msgType.equals(Cons.REQ_MESSAGE_TYPE_VIDEO)) {
            result = "您发送的是视频消息,暂时不支持！";
        }
        // 视频消息
        else if (msgType.equals(Cons.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
            result = "您发送的是小视频消息,暂时不支持！";
        }
        // 地理位置消息
        else if (msgType.equals(Cons.REQ_MESSAGE_TYPE_LOCATION)) {
            result = "您发送的是地理位置消息,暂时不支持！";
        }
        // 链接消息
        else if (msgType.equals(Cons.REQ_MESSAGE_TYPE_LINK)) {
            result = "什么链接？不会是爱情动作片吧？";
        }
        // 事件推送
        else if (msgType.equals(Cons.REQ_MESSAGE_TYPE_EVENT)) {
            // 事件类型
            String eventType = xmlEntity.getEvent();
            // 关注
            if (eventType.equals(Cons.EVENT_TYPE_SUBSCRIBE)) {
                result = "感谢您的关注！\r\n 您可以输入“股票”和“机器人”进行两种模式的切换，您默认处于股票模式\r\n 机器人模式中您可以对机器人说任何话，机器人会智能回复. \r\n 股票模式里输入“"+Common.listGupiao+"”列出您的所有股票\r\n 输入“"+Common.addGupiao+"股票编码”添加您的自选股";
            }
            // 取消关注
            else if (eventType.equals(Cons.EVENT_TYPE_UNSUBSCRIBE)) {
                // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
            }
            // 扫描带参数二维码
            else if (eventType.equals(Cons.EVENT_TYPE_SCAN)) {
                // TODO 处理扫描带参数二维码事件
            }
            // 上报地理位置
            else if (eventType.equals(Cons.EVENT_TYPE_LOCATION)) {
                // TODO 处理上报地理位置事件
            }
            // 自定义菜单
            else if (eventType.equals(Cons.EVENT_TYPE_CLICK)) {
                // TODO 处理菜单点击事件
            }
        }

        /** 此时，如果用户输入的是“你好”，在经过上面的过程之后，result为“你也好”类似的内容
         *  因为最终回复给微信的也是xml格式的数据，所有需要将其封装为文本类型返回消息
         * */
        result = new FormatXmlProcess().formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), result);

        return result;
    }

}