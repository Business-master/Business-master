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
     * type : 1 新闻 2商讯
     * ctime : 1486629049000
     */

    private int id;
    private String title;
    private String areaCode;
    private String fmImg;
    private String type;
    private long ctime;

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
                '}';
    }
}
