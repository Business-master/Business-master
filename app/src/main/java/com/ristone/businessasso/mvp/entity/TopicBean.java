package com.ristone.businessasso.mvp.entity;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/2/9
 */

public class TopicBean {


    /**
     * newId : 29
     * id : 5
     * utime : 1486606890000
     * dissertationName : 测试新闻
     * status : 1
     * createrId : 1
     * fmImg : /dissertation/68dcec21bf904af490ae086107ee7e77.jpg
     * ctime : 1486606631000
     */

    private int newId;
    private int id;
    private long utime;
    private String title;
    private String status;
    private String createrId;
    private String fmImg;
    private long ctime;

    public int getNewId() {
        return newId;
    }

    public void setNewId(int newId) {
        this.newId = newId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String dissertationName) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public String getFmImg() {
        return fmImg;
    }

    public void setFmImg(String fmImg) {
        this.fmImg = fmImg;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    @Override
    public String toString() {
        return "TopicBean{" +
                "newId=" + newId +
                ", id=" + id +
                ", utime=" + utime +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", createrId='" + createrId + '\'' +
                ", fmImg='" + fmImg + '\'' +
                ", ctime=" + ctime +
                '}';
    }
}
