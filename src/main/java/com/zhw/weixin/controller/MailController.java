package com.zhw.weixin.controller;

import com.zhw.weixin.dto.AppointExecution;
import com.zhw.weixin.dto.Result;
import com.zhw.weixin.entity.Book;
import com.zhw.weixin.enums.AppointStateEnum;
import com.zhw.weixin.logic.WechatProcess;
import com.zhw.weixin.service.MainProcessService;
import com.zhw.weixin.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by zhanghongwei on 17/12/29.
 */
@Controller
@RequestMapping("/") // url:/模块/资源/{id}/细分 /seckill/list
public class MailController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MainProcessService mainProcessService;

    @RequestMapping(method = RequestMethod.GET)
    private void get(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        //微信加密签名
        String signature = request.getParameter("signature");
        //时间戳
        String timestamp = request.getParameter("timestamp");
        //随机数
        String nonce = request.getParameter("nonce");
        //随机字符串
        String echostr = request.getParameter("echostr");
        PrintWriter out = response.getWriter();
        //通过校验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if(SignUtil.checkSignature(signature, timestamp, nonce)){
            out.print(echostr);
        }
        out.close();
        out = null;
    }

    @RequestMapping(method = RequestMethod.POST)
    private void post(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        /** 读取接收到的xml消息 */
        StringBuffer sb = new StringBuffer();
        InputStream is = request.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String s = "";
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        String xml = sb.toString(); //次即为接收到微信端发送过来的xml数据

        logger.info(xml);

        String result = "";
        /** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */
        String echostr = request.getParameter("echostr");
        if (echostr != null && echostr.length() > 1) {
            result = echostr;
        } else {
            //正常的微信处理流程
            result = mainProcessService.processWechatMag(xml);
        }

        logger.info(result);

        try {
            OutputStream os = response.getOutputStream();
            os.write(result.getBytes("UTF-8"));
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}