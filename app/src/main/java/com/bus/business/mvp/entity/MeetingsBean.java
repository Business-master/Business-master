package com.bus.business.mvp.entity;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
public class MeetingsBean {
    private List<MeetingBean> meetingList;

    public List<MeetingBean> getMeetingList() {
        return meetingList;
    }

    public void setMeetingList(List<MeetingBean> meetingList) {
        this.meetingList = meetingList;
    }

    @Override
    public String toString() {
        return "MeetingsBean{" +
                "meetingList=" + meetingList +
                '}';
    }
}
