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
     * utime : 1488254887000
     * foodId : null
     * stay :
     * status : 0   //状态 0未召开 1正在召开 2会议结束
     * hasReaded : null//1 会议信息未读 2 已读
     * userOrganization : 总工商联
     * qrImg : /metting/qrcode/201702281208065840.png
     * checkTime : 1488254927000
     * meetingContent : 333
     * ctime : 1488254887000
     * areaId : null
     * id : 78
     * meetingLoc : 33
     * isLate : 0
     * duration : 33
     * meetingName : 33
     * meetingTime : 1489119966000
     * longitude : 116.377215
     * joinType : 2  // 参会状态  0未报名 1未签到 2已签到 3本人参加 4助理参加  5请假   6取消报名 7未参加 8已过期
     * latitude : 39.993432
     * checkType : 0  // 签到状态 0未签到 1已签到
     * roomTypeId : null
     */

    private long utime;
    private Object foodId;
    private String stay;
    private String status;
    private int hasReaded;
    private String userOrganization;
    private String qrImg;
    private long checkTime;
    private String meetingContent;
    private long ctime;
    private int areaId;
    private int id;
    private String meetingLoc;
    private String isLate;
    private int duration;
    private String meetingName;
    private long meetingTime;
    private String longitude;
    private int joinType;
    private String latitude;
    private String checkType;
    private Object roomTypeId;

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public String getMeetingLoc() {
        return meetingLoc;
    }

    public void setFoodId(Object foodId) {
        this.foodId = foodId;
    }

    public String getStay() {
        return stay;
    }

    public void setStay(String stay) {
        this.stay = stay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHasReaded() {
        return hasReaded;
    }

    public void setHasReaded(int hasReaded) {
        this.hasReaded = hasReaded;
    }

    public String getUserOrganization() {
        return userOrganization;
    }

    public void setUserOrganization(String userOrganization) {
        this.userOrganization = userOrganization;
    }

    public String getQrImg() {
        return qrImg;
    }

    public void setQrImg(String qrImg) {
        this.qrImg = qrImg;
    }

    public long getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(long checkTime) {
        this.checkTime = checkTime;
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public void setMeetingLoc(String meetingLoc) {
        this.meetingLoc = meetingLoc;
    }

    public String getIsLate() {
        return isLate;
    }

    public void setIsLate(String isLate) {
        this.isLate = isLate;
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

    public long getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(long meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getJoinType() {
        return joinType;
    }

    public void setJoinType(int joinType) {
        this.joinType = joinType;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public Object getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Object roomTypeId) {
        this.roomTypeId = roomTypeId;
    }



    public void intentToDetail(Context context, int pos){
        Intent intent = new Intent(context,MeetingDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MEETINGBEAN,this);
        bundle.putInt(MEETINGPOS,pos);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


}
