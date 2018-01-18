package com.zhw.weixin.dao;

/**
 * Created by zhanghongwei on 17/12/29.
 */
import com.zhw.weixin.entity.GupiaoEvent;
import com.zhw.weixin.entity.Mygupiao;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface GupiaoEventDao {

    /**
     * 查询我的自选股列表
     * @return
     */
    List<GupiaoEvent> listEvent(@Param("mygupiao_id") long mygupiaoId);

    /**
     * 增加自选股
     */
    int addGupiaoEvent(GupiaoEvent gupiaoEvent);
}