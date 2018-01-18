package com.zhw.weixin.logic;

import com.zhw.weixin.bean.ReceiveXmlEntity;
import com.zhw.weixin.cons.Cons;

/**
 * Created by zhanghongwei on 17/11/6.
 */
public class WechatProcess {
    /**
     * 解析处理xml、获取智能回复结果（通过图灵机器人api接口）
     * @param xml 接收到的微信数据
     * @return  最终的解析结果（xml格式数据）
     */
    public String processWechatMag(String xml){
        /** 解析xml数据 */
        ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);

        /** 以文本消息为例，调用图灵机器人api接口，获取回复内容 */
        String result = "";

        String msgType = xmlEntity.getMsgType();

        if(Cons.REQ_MESSAGE_TYPE_TEXT.equals(msgType)){
            result = new TulingApiProcess().getTulingResult(xmlEntity.getContent());
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
                result = "谢谢您的关注！您可以说任何消息给公众号，机器人会智能回复. Tips:公众号即将改名为<上了我的贼船>，拉更多的人上船吧；";
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
