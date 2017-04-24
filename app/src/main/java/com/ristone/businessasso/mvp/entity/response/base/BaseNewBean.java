package com.ristone.businessasso.mvp.entity.response.base;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
public class BaseNewBean {

    private int id;
    private String fmImg;
    private long ctime;
    private Object areaId;
    private String title;
    private String areaCode;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Object getAreaId() {
        return areaId;
    }

    public void setAreaId(Object areaId) {
        this.areaId = areaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BaseNewBean{" +
                "id=" + id +
                ", fmImg='" + fmImg + '\'' +
                ", ctime=" + ctime +
                ", areaId=" + areaId +
                ", title='" + title + '\'' +
                ", areaCode='" + areaCode + '\'' +
                '}';
    }
}
