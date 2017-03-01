package com.bus.business.mvp.event;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/10
 */
public class JoinToMeetingEvent {
    /**
     *1 报名成功 2请假成功 3取消报名成功  4签到成功
     */
    private int pos;
    public JoinToMeetingEvent(int pos){
        this.pos = pos;
    }

    public JoinToMeetingEvent(){

    }

    public int getPos() {
        return pos;
    }
}
