package com.ristone.businessasso.mvp.entity;

/**
 * Created by ATRSnail on 2017/5/10.
 */

public class QuestionnaireBean {

    /**
     * utime : null
     * name : 百度
     * ctime : null
     * id : 1
     * url : www.baidu.com1
     */

    private Object utime;
    private String name;
    private Object ctime;
    private int id;
    private String url;

    public Object getUtime() {
        return utime;
    }

    public void setUtime(Object utime) {
        this.utime = utime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getCtime() {
        return ctime;
    }

    public void setCtime(Object ctime) {
        this.ctime = ctime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "QuestionnaireBean{" +
                "utime=" + utime +
                ", name='" + name + '\'' +
                ", ctime=" + ctime +
                ", id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}
