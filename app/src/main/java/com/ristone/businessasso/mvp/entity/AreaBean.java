package com.ristone.businessasso.mvp.entity;

/**
 * Created by ATRSnail on 2017/2/15.
 */

public class AreaBean {


    /**
     * id : 288
     * utime : 1486970681000
     * name : 东城区工商联
     * seq : 1
     * parent : 287
     * code : 0001
     * ctime : 1482908838000
     */

    private int id;
    private long utime;
    private String name;
    private int seq;
    private int parent;
    private String code;
    private long ctime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    @Override
    public String toString() {
        return "AreaBean{" +
                "id=" + id +
                ", utime=" + utime +
                ", name='" + name + '\'' +
                ", seq=" + seq +
                ", parent=" + parent +
                ", code='" + code + '\'' +
                ", ctime=" + ctime +
                '}';
    }
}
