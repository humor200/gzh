package com.zhw.weixin.dao;

/**
 * Created by zhanghongwei on 17/12/29.
 */
import com.zhw.weixin.entity.Book;
import com.zhw.weixin.entity.Mygupiao;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MygupiaoDao {

    /**
     * @return
     */
    Mygupiao queryByUsernameAndCode(@Param("username") String username, @Param("gupiao_code") String gupiaoCode);

    /**
     * 查询我的自选股列表
     * @return
     */
    List<Mygupiao> listMyGupiao(@Param("username") String username);

    /**
     * 增加自选股
     */
    int addMyGupiao(Mygupiao mygupiao);

    int removeMyGupiao(@Param("username") String username, @Param("gupiao_code") String gupiaoCode);
}