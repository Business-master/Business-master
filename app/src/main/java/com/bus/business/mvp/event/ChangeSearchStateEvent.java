package com.bus.business.mvp.event;

import com.socks.library.KLog;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/29
 */
public class ChangeSearchStateEvent {
    private int mMsg;
    public ChangeSearchStateEvent(int msg) {
        // TODO Auto-generated constructor stub
        mMsg = msg;
        KLog.a("有消息推送------------");
    }
    public int getMsg(){
        return mMsg;
    }
}
