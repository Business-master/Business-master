package com.bus.business.mvp.event;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/10
 */
public class JoinToMeetingEvent {

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
