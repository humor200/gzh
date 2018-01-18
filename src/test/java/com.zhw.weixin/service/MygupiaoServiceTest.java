package com.zhw.weixin.service;

import com.zhw.weixin.BaseTest;
import com.zhw.weixin.bean.ReceiveXmlEntity;
import com.zhw.weixin.entity.GupiaoEvent;
import com.zhw.weixin.entity.Mygupiao;
import com.zhw.weixin.logic.CommandRouter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by zhanghongwei on 17/12/31.
 */
public class MygupiaoServiceTest extends BaseTest {

    @Autowired
    private MygupiaoService mygupiaoService;

    @Autowired
    private GupiaoEventService gupiaoEventService;

    @Autowired
    private CommandRouter commandRouter;

    @Test
    public void listMygupiao () {
        String username = "test001";
        List<Mygupiao> mygupiaoList =  mygupiaoService.getList(username);

        System.out.println(mygupiaoList.toString());
    }

    @Test
    public void queryMygupiao () {
        String username = "test002";
        String code = "006249";
        Mygupiao mygupiao =  mygupiaoService.getByUsernameAndCode(username, code);
        if (mygupiao == null) {
            System.out.println("没有");
            return;
        }
        System.out.println(mygupiao.toString());
    }

    @Test
    public void addMygupiao () {
        String username = "test001";
        String code = "006249";
        int result = mygupiaoService.addMygupiao(username, code);

        System.out.println(result);
    }

    @Test
    public void addGupiaEvent () {
        int result = gupiaoEventService.addGupiaoEvent(11, 1, "明天肯定涨", 0.00);

        System.out.println(result);
    }

    @Test
    public void queryGupiaoEvent () {
        List<GupiaoEvent> gupiaoEventList =  gupiaoEventService.getList(11);
        System.out.println(gupiaoEventList.toString());
    }

    @Test
    public void logic() {

        //输入list   ：列出我的自选股列表
        //输入add:code  ：添加自选股
        //输入show:code ：列出该股票所有操作记录
        //输入1:code:内容  ：评价该股票
        //输入2:code:money:count  ：买
        //输入3:code:money:count  ：卖

        ReceiveXmlEntity entity = new ReceiveXmlEntity();
        entity.setFromUserName("11");
        entity.setContent("123");

        //entity.setContent("add:000302");

        //entity.setContent("show:000302");

        //entity.setContent("1:000302:macd零轴线上形成金叉，好像还不错的样子，接下来一周要涨");

        System.out.println(commandRouter.route("11", entity.getContent()));

        System.out.println(commandRouter.route("11", "0"));

        System.out.println(commandRouter.route("11", "1"));

//        System.out.println(commandRouter.route("11", "+测试点评"));
//        System.out.println(commandRouter.route("11", "错误的测试点评"));

        //System.out.println(commandRouter.route("11", "2"));
    }
}
