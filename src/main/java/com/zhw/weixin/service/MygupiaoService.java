package com.zhw.weixin.service;

/**
 * Created by zhanghongwei on 17/12/29.
 */

import com.zhw.weixin.bean.ReceiveXmlEntity;
import com.zhw.weixin.dao.MygupiaoDao;
import com.zhw.weixin.dto.AppointExecution;
import com.zhw.weixin.entity.Appointment;
import com.zhw.weixin.entity.Book;
import com.zhw.weixin.entity.GupiaoEvent;
import com.zhw.weixin.entity.Mygupiao;
import com.zhw.weixin.enums.AppointStateEnum;
import com.zhw.weixin.logic.CommandRouter;
import com.zhw.weixin.util.DateUtil;
import com.zhw.weixin.util.SinaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MygupiaoService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MygupiaoDao mygupiaoDao;

    @Autowired
    private GupiaoEventService gupiaoEventService;

    @Autowired
    private CommandRouter commandRouter;

    public Mygupiao getByUsernameAndCode(String username, String gupiaoCode) {
        return mygupiaoDao.queryByUsernameAndCode(username, gupiaoCode);
    }

    public int removeByUsernameAndCode(String username, String gupiaoCode) {
        return mygupiaoDao.removeMyGupiao(username, gupiaoCode);
    }

    /**
     * 获取我的所有自选股
     * @param username
     * @return
     */
    public List<Mygupiao> getList(String username) {
        return mygupiaoDao.listMyGupiao(username);
    }

    /**
     * 增加我的自选股
     * @param username
     * @param code
     * @return
     */
    public int addMygupiao(String username, String code) {

        SinaUtil.GupiaoInfo info = SinaUtil.getGupiaoPrice(code);
        if (info == null) {
            return -1;
        }
        String  gupiaoName = info.getName();
        double  money = info.getPrice();

        Mygupiao mygupiao = new Mygupiao();
        mygupiao.setUsername(username);
        mygupiao.setGupiaoCode(code);
        mygupiao.setGupiaoName(gupiaoName);
        mygupiao.setMoney(money);
        return mygupiaoDao.addMyGupiao(mygupiao);
    }
}