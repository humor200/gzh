package com.zhw.weixin.controller;

import com.zhw.weixin.bean.*;
import com.zhw.weixin.service.MainProcessService;
import com.zhw.weixin.util.SignUtil;
import com.zhw.weixin.util.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by zhanghongwei on 17/12/29.
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    private void create(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        // 第三方用户唯一凭证
        String appId = "wx3294393391d9e914";
        // 第三方用户唯一凭证密钥
        String appSecret = "c169f5b18810aced7ffbcb2473606a5d";

        // 调用接口获取access_token
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

        if (null != at) {
            // 调用接口创建菜单
            int result = WeixinUtil.createMenu(getMenu(), at.getToken());

            // 判断菜单创建结果
            if (0 == result)
                logger.info("菜单创建成功！");
            else
                logger.info("菜单创建失败，错误码：" + result);
        }
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    private String get(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        // 第三方用户唯一凭证
        String appId = "wx3294393391d9e914";
        // 第三方用户唯一凭证密钥
        String appSecret = "c169f5b18810aced7ffbcb2473606a5d";

        // 调用接口获取access_token
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

        if (null != at) {
            // 调用接口创建菜单
            return WeixinUtil.getMenu(at.getToken());
        }
        return "null";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    private int delete(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        // 第三方用户唯一凭证
        String appId = "wx3294393391d9e914";
        // 第三方用户唯一凭证密钥
        String appSecret = "c169f5b18810aced7ffbcb2473606a5d";

        // 调用接口获取access_token
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

        if (null != at) {
            // 调用接口创建菜单
            return WeixinUtil.deleteMenu(at.getToken());
        }
        return 0;
    }

    /**
     * 组装菜单数据
     *
     * @return
     */
    private Menu getMenu() {
        CommonButton btn11 = new CommonButton();
        btn11.setName("股票模式");
        btn11.setType("click");
        btn11.setKey("gupiao");

        CommonButton btn12 = new CommonButton();
        btn12.setName("机器人模式");
        btn12.setType("click");
        btn12.setKey("robot");

        CommonButton btn21 = new CommonButton();
        btn21.setName("我的自选股");
        btn21.setType("click");
        btn21.setKey("list");

        CommonButton btn22 = new CommonButton();
        btn22.setName("添加自选股");
        btn22.setType("click");
        btn22.setKey("add");

        CommonButton btn23 = new CommonButton();
        btn23.setName("添加判断");
        btn23.setType("click");
        btn23.setKey("panduan");

        CommonButton btn24 = new CommonButton();
        btn24.setName("昨日判断");
        btn24.setType("click");
        btn24.setKey("yestoday");

        CommonButton btn31 = new CommonButton();
        btn31.setName("同花顺");
        btn31.setType("click");
        btn31.setKey("tonghuashun");

        CommonButton btn32 = new CommonButton();
        btn32.setName("蜻蜓点金");
        btn32.setType("click");
        btn32.setKey("qingting");

        CommonButton btn33 = new CommonButton();
        btn33.setName("其他");
        btn33.setType("click");
        btn33.setKey("other");


        /**
         * 微信：  mainBtn1,mainBtn2,mainBtn3底部的三个一级菜单。
         */

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("功能切换");
        //一级下有4个子菜单
        mainBtn1.setSub_button(new CommonButton[] { btn11, btn12});


        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("股票列表");
        mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23, btn24 });


        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("更多体验");
        mainBtn3.setSub_button(new CommonButton[] { btn31, btn32, btn33 });


        /**
         * 封装整个菜单
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

        return menu;
    }
}