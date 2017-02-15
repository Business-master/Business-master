package com.bus.business.mvp.entity;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/26
 */
public class BannerBean {


    /**
     * id : 3
     * utime : 1482477376000
     * title : 测试新闻
     * status : 1
     * fmImg : /gsl/image/news/f95e9685430043e4a2d9ef17cb272382.jpg
     * types : 1
     * ctime : 1482473334000
     * areaId : null
     */

    private int id;
    private long utime;
    private String title;
    private String status;
    private String fmImg;
    private String types;
    private long ctime;
    private Object areaId;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Object getAreaId() {
        return areaId;
    }

    public void setAreaId(Object areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "id=" + id +
                ", utime=" + utime +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", fmImg='" + fmImg + '\'' +
                ", types='" + types + '\'' +
                ", ctime=" + ctime +
                ", areaId=" + areaId +
                '}';
    }
}
