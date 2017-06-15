package com.ristone.businessasso.mvp.entity;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/2/9
 */

public class TopicBean {


    @Override
    public String toString() {
        return "TopicBean{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", utime=" + utime +
                ", title='" + title + '\'' +
                ", sort=" + sort +
                ", status='" + status + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", videoUrl=" + videoUrl +
                ", chambreCode='" + chambreCode + '\'' +
                ", fmImg='" + fmImg + '\'' +
                ", types=" + types +
                ", ctime=" + ctime +
                '}';
    }

    /**
     * id : 63
     * parentId : 47
     * utime : 1487036201000
     * title : 京津冀协同发展三大领域如何率先突破？
     * sort : 63
     * status : 1
     * areaCode : 0001
     * videoUrl : null
     * chambreCode : 0001
     * fmImg : /dissertation/8a7a19e84ccd4193a00c63ebf75f6ea5.jpg
     * types : null
     * ctime : 1486692379000
     */



    private int id;
    private int parentId;
    private long utime;
    private String title;
    private int sort;
    private String status;
    private String areaCode;
    private Object videoUrl;
    private String chambreCode;
    private String fmImg;
    private Object types;
    private long ctime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Object getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(Object videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getChambreCode() {
        return chambreCode;
    }

    public void setChambreCode(String chambreCode) {
        this.chambreCode = chambreCode;
    }

    public String getFmImg() {
        return fmImg;
    }

    public void setFmImg(String fmImg) {
        this.fmImg = fmImg;
    }

    public Object getTypes() {
        return types;
    }

    public void setTypes(Object types) {
        this.types = types;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }
}
