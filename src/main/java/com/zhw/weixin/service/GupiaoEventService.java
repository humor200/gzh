package com.zhw.weixin.service;

/**
 * Created by zhanghongwei on 17/12/29.
 */

import com.zhw.weixin.bean.ReceiveXmlEntity;
import com.zhw.weixin.dao.GupiaoEventDao;
import com.zhw.weixin.dao.MygupiaoDao;
import com.zhw.weixin.entity.GupiaoEvent;
import com.zhw.weixin.entity.Mygupiao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GupiaoEventService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GupiaoEventDao gupiaoEventDao;

    /**
     * 获取我的所有自选股
     * @return
     */
    public List<GupiaoEvent> getList(long mygupiaoId) {
        return gupiaoEventDao.listEvent(mygupiaoId);
    }

    /**
     * 增加我的自选股
     * @return
     */
    public int addGupiaoEvent(long gupiaoId, int type, String content, double money) {
        GupiaoEvent event = new GupiaoEvent();
        event.setMygupiaoId(gupiaoId);
        event.setMoney(money);
        event.setContent(content);
        event.setType(type);
        return gupiaoEventDao.addGupiaoEvent(event);
    }

}