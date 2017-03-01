package com.bus.business.mvp.entity;

/**
 * Created by ATRSnail on 2017/2/28.
 */

public class NationBean {

    /**
     * parent : 318
     * code : 0002
     * utime : null
     * name : 壮族
     * ctime : 1487817361000
     * id : 383
     * seq : 2
     */

    private int parent;
    private String code;
    private Object utime;
    private String name;
    private long ctime;
    private int id;
    private int seq;

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

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "NationBean{" +
                "parent=" + parent +
                ", code='" + code + '\'' +
                ", utime=" + utime +
                ", name='" + name + '\'' +
                ", ctime=" + ctime +
                ", id=" + id +
                ", seq=" + seq +
                '}';
    }
}
