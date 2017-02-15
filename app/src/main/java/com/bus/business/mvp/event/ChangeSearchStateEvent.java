package com.bus.business.mvp.event;

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
    }
    public int getMsg(){
        return mMsg;
    }
}
