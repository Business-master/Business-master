package com.ristone.businessasso.mvp.entity;

/**
 * Created by ATRSnail on 2017/6/2.
 * 新闻列表子数据解析
 */

public class XinWenBean {

    @Override
    public String toString() {
        return "XinWenBean{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", utime=" + utime +
                ", title='" + title + '\'' +
                ", sort=" + sort +
                ", status='" + status + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", chambreCode='" + chambreCode + '\'' +
                ", fmImg='" + fmImg + '\'' +
                ", types='" + types + '\'' +
                ", ctime=" + ctime +
                '}';
    }

    /**
     * id : 110
     * parentId : null
     * utime : null
     * title : 测试新闻视频2222
     * sort : 107
     * status : 1
     * areaCode : 北京市
     * videoUrl : /vedio/bc49f1d64d19444bb0614293cd3f36ae.mp4
     * chambreCode : 住宅房地产商会
     * fmImg : /news/30b4ccfb2aa344f5b23608bb532dd61c.jpg
     * types : 5  视频新闻----2普通新闻
     * ctime : 1496382542000
     */

    private int id;
    private Object parentId;
    private Object utime;
    private String title;
    private int sort;
    private String status;
    private String areaCode;
    private String videoUrl;
    private String chambreCode;
    private String fmImg;
    private String types;
    private long ctime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public Object getUtime() {
        return utime;
    }

    public void setUtime(Object utime) {
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
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

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }
}
