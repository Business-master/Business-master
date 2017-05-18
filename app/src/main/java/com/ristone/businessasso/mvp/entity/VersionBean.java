package com.ristone.businessasso.mvp.entity;

/**
 * Created by ATRSnail on 2017/5/18.
 */

public class VersionBean {


    /**
     * isDelete : 0
     * platform : 2
     * createTime : 1495071035000
     * updateTime : 1495036800000
     * versionId : 6
     * versionCode : 2
     * modiTime : 1495071035000
     * versionName : 测试安卓222
     * updateType : 2
     * url : http://172.16.10.15:9300/gsl/download/android/app-release2.apk
     * introduction : 测试安卓222
     */

    private int isDelete;
    private String platform;
    private long createTime;
    private long updateTime;
    private int versionId;
    private String versionCode;
    private long modiTime;
    private String versionName;
    private String updateType;
    private String url;
    private String introduction;

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public long getModiTime() {
        return modiTime;
    }

    public void setModiTime(long modiTime) {
        this.modiTime = modiTime;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "VersionBean{" +
                "isDelete=" + isDelete +
                ", platform='" + platform + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", versionId=" + versionId +
                ", versionCode='" + versionCode + '\'' +
                ", modiTime=" + modiTime +
                ", versionName='" + versionName + '\'' +
                ", updateType='" + updateType + '\'' +
                ", url='" + url + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
