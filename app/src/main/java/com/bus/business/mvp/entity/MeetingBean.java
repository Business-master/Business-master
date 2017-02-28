package com.bus.business.mvp.entity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bus.business.mvp.ui.activities.MeetingDetailActivity;

import java.io.Serializable;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/24
 */
public class MeetingBean implements Serializable{
    public static final String MEETINGBEAN = "meeting_bean";
    public static final String MEETINGPOS = "meeting_pos";
    /**
     * id : 1
     * utime : 1482723070000
     * meetingLoc : ioc
     * duration : 1
     * meetingName : meetName
     * status : 1
     * meetingTime : 1470627036000
     * qrImg :
     * meetingContent : context
     * ctime : 1482722354000
     * areaId : 2
     * hasReaded 1 会议信息未读 2 已读
     */

    private int hasReaded;

    public int getHasReaded() {
        return hasReaded;
    }

    public void setHasReaded(int hasReaded) {
        this.hasReaded = hasReaded;
    }

    private int id;
    private long utime;
    private String meetingLoc;
    private int duration;
    private String meetingName;
    private String status; //状态 0未召开 1正在召开 2会议结束
    private long meetingTime;
    private String qrImg;
    private String meetingContent;
    private long ctime;
    private int areaId;
    private String checkType;// 签到状态 0未签到 1已签到
    private int joinType;// 参会状态  0未报名 1未签到 2已签到 3本人参加 4助理参加  5请假   6取消报名 7未参加 8已过期
private String longitude;
    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public MeetingBean(int id) {
        this.id = id;
    }

    public int getJoinType() {
        return joinType;
    }

    public void setJoinType(int joinType) {
        this.joinType = joinType;
    }

    public void intentToDetail(Context context, int pos){
        Intent intent = new Intent(context,MeetingDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MEETINGBEAN,this);
        bundle.putInt(MEETINGPOS,pos);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

//    public boolean getJoinType() {
//        return joinType != null && joinType.equals("1");
//    }

//    public void setJoinType(boolean joinType) {
//        this.joinType = joinType ? "1" : "0";
//    }

    public boolean getCheckType() {
        return checkType != null && checkType.equals("1");
    }

    public void setCheckType(boolean checkType) {
        this.checkType = checkType ? "1" : "0";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public String getMeetingLoc() {
        return meetingLoc;
    }

    public void setMeetingLoc(String meetingLoc) {
        this.meetingLoc = meetingLoc;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(long meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getQrImg() {
        return qrImg;
    }

    public void setQrImg(String qrImg) {
        this.qrImg = qrImg;
    }

    public String getMeetingContent() {
        return meetingContent;
    }

    public void setMeetingContent(String meetingContent) {
        this.meetingContent = meetingContent;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "MeetingBean{" +
                "hasReaded=" + hasReaded +
                ", id=" + id +
                ", utime=" + utime +
                ", meetingLoc='" + meetingLoc + '\'' +
                ", duration=" + duration +
                ", meetingName='" + meetingName + '\'' +
                ", status='" + status + '\'' +
                ", meetingTime=" + meetingTime +
                ", qrImg='" + qrImg + '\'' +
                ", meetingContent='" + meetingContent + '\'' +
                ", ctime=" + ctime +
                ", areaId=" + areaId +
                ", checkType='" + checkType + '\'' +
                ", joinType=" + joinType +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }


}
