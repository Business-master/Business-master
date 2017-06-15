package com.ristone.businessasso.mvp.entity;

/**
 * Created by ATRSnail on 2017/2/15.
 * 根据区域 请求的 商讯、新闻
 */

public class AreaSeaBean {

    /**
     * id : 45
     * title : 习近平：各级领导干部要学网、懂网、用网
     * areaCode : 东城区工商联
     * fmImg : /news/40dff87b1710425da98447e7a614078a.jpg
     * type :  //2：新闻；1：商讯 5:视频
     * ctime : 1486629049000
     *  "videoUrl":""/vedio/a0c29b1aa6f24454865951af93f48536.mp4"",  //视频新闻 中有视频地址  其他为空
     */

    private int id;
    private String title;
    private String areaCode;
    private String fmImg;
    private String type;
    private long ctime;
    private String videoUrl;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getFmImg() {
        return fmImg;
    }

    public void setFmImg(String fmImg) {
        this.fmImg = fmImg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    @Override
    public String toString() {
        return "AreaSeaBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", fmImg='" + fmImg + '\'' +
                ", type='" + type + '\'' +
                ", ctime=" + ctime +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
