package com.zhw.weixin.logic;

import com.zhw.weixin.enums.UserMode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghongwei on 17/12/30.
 */
@Component
public class UserModeCache {

    private Map<String, UserMode> userModeMap = new HashMap<String, UserMode>();

    /**
     * 获取我的模式
     * @param userName
     * @return
     */
    public UserMode getMyMode(String userName) {
        UserMode mode = userModeMap.get(userName);
        if (mode == null) {
            return UserMode.GUPIAO;
        }
        return mode;
    }

    /**
     * 修改模式
     * @param userName
     * @param mode
     * @return
     */
    public boolean changeMode(String userName, UserMode mode) {
        userModeMap.put(userName, mode);
        return true;
    }

}
