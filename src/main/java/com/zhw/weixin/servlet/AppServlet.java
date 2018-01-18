package com.zhw.weixin.servlet;

import com.zhw.weixin.logic.WechatProcess;
import com.zhw.weixin.util.SignUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ThriftServer
 */
public class AppServlet extends HttpServlet {

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("doPost");

//        response.setContentType("text/html;charset=utf-8");
//        response.getWriter().println("大鱼<br>\n" +
//                "(动画电影《大鱼海棠》印象曲)<br>\n" +
//                "作词：尹约 作曲：钱雷<br>\n" +
//                "演唱：周深<br>\n" +
//                "海浪无声将夜幕深深淹没<br>\n" +
//                "漫过天空尽头的角落<br>\n" +
//                "大鱼在梦境的缝隙里游过<br>\n" +
//                "凝望你沉睡的轮廓<br>\n" +
//                "看海天一色 听风起雨落<br>\n" +
//                "执子手吹散苍茫茫烟波<br>\n" +
//                "大鱼的翅膀 已经太辽阔<br>\n" +
//                "我松开时间的绳索<br>\n" +
//                "怕你飞远去 怕你离我而去<br>\n" +
//                "更怕你永远停留在这里<br>\n" +
//                "每一滴泪水 都向你流淌去<br>\n" +
//                "倒流进天空的海底<br>\n" +
//                "海浪无声将夜幕深深淹没<br>\n" +
//                "漫过天空尽头的角落<br>\n" +
//                "大鱼在梦境的缝隙里游过<br>\n" +
//                "凝望你沉睡的轮廓<br>\n" +
//                "看海天一色 听风起雨落<br>\n" +
//                "执子手吹散苍茫茫烟波<br>\n" +
//                "大鱼的翅膀 已经太辽阔<br>\n" +
//                "我松开时间的绳索<br>\n" +
//                "看你飞远去 看你离我而去<br>\n" +
//                "原来你生来就属于天际<br>\n" +
//                "每一滴泪水 都向你流淌去<br>\n" +
//                "倒流回最初的相遇<br>");
//        response.getWriter().println("");


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

        System.out.println(xml);

        String result = "";
        /** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */
        String echostr = request.getParameter("echostr");
        if (echostr != null && echostr.length() > 1) {
            result = echostr;
        } else {
            //正常的微信处理流程
            result = new WechatProcess().processWechatMag(xml);
        }

        try {
            OutputStream os = response.getOutputStream();
            os.write(result.getBytes("UTF-8"));
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("doGet");
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
//        doPost(request, response);
    }
}
