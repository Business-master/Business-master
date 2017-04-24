package com.ristone.businessasso.mvp.event;

/**
 *会议详情下方 按钮事件
 */
public class MeetingDetailEvent {
    /**
     *1 报名成功 2请假成功   3签到成功
     */
    private int pos;
    public MeetingDetailEvent(int pos){
        this.pos = pos;
    }

    public MeetingDetailEvent(){

    }

    public int getPos() {
        return pos;
    }
}
