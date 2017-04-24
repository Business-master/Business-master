package com.ristone.businessasso.mvp.entity;

import java.util.List;

/**
 * Created by ATRSnail on 2017/3/2.
 */

public class MeetingDetailBeans {
    List<MeetingDetailBean> meetingDatail;

    public List<MeetingDetailBean> getMeetingDatail() {
        return meetingDatail;
    }

    public void setMeetingDatail(List<MeetingDetailBean> meetingDatail) {
        this.meetingDatail = meetingDatail;
    }

    @Override
    public String toString() {
        return "MeetingDetailBeans{" +
                "meetingDatail=" + meetingDatail +
                '}';
    }
}
