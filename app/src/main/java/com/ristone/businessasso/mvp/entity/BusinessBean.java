package com.ristone.businessasso.mvp.entity;



/**
 * 首页-商情-子项目数据解析
 */

public class BusinessBean {
    @Override
    public String toString() {
        return "BusinessBean{" +
                "utime=" + utime +
                ", sort=" + sort +
                ", areaCode='" + areaCode + '\'' +
                ", status='" + status + '\'' +
                ", contactsS='" + contactsS + '\'' +
                ", fmImg='" + fmImg + '\'' +
                ", emailS='" + emailS + '\'' +
                ", ctime=" + ctime +
                ", id=" + id +
                ", phoneNo='" + phoneNo + '\'' +
                ", title='" + title + '\'' +
                ", inAmount=" + inAmount +
                ", plane='" + plane + '\'' +
                ", chambreCode='" + chambreCode + '\'' +
                ", faxS='" + faxS + '\'' +
                '}';
    }

    /**
     * utime : 1490343997000
     * sort : 43
     * areaCode : 大兴区
     * status : 通过
     * contactsS : 工商联
     * fmImg : /business/633b60e0211742b391dbd4442942720e.jpg
     * emailS : xuanjiaochu@bjgsl.org.cn
     * ctime : 1490343997000
     * id : 34
     * phoneNo : 13333333333
     * title : 北京新航城东区再生水厂
     * inAmount : 3.0E8
     * plane : 010-2222222
     * chambreCode : 住宅房地产商会
     * faxS : 010-2222222
     */

    private long utime;
    private int sort;
    private String areaCode;
    private String status;
    private String contactsS;
    private String fmImg;
    private String emailS;
    private long ctime;
    private int id;
    private String phoneNo;
    private String title;
    private double inAmount;
    private String plane;
    private String chambreCode;
    private String faxS;

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContactsS() {
        return contactsS;
    }

    public void setContactsS(String contactsS) {
        this.contactsS = contactsS;
    }

    public String getFmImg() {
        return fmImg;
    }

    public void setFmImg(String fmImg) {
        this.fmImg = fmImg;
    }

    public String getEmailS() {
        return emailS;
    }

    public void setEmailS(String emailS) {
        this.emailS = emailS;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getInAmount() {
        return inAmount;
    }

    public void setInAmount(double inAmount) {
        this.inAmount = inAmount;
    }

    public String getPlane() {
        return plane;
    }

    public void setPlane(String plane) {
        this.plane = plane;
    }

    public String getChambreCode() {
        return chambreCode;
    }

    public void setChambreCode(String chambreCode) {
        this.chambreCode = chambreCode;
    }

    public String getFaxS() {
        return faxS;
    }

    public void setFaxS(String faxS) {
        this.faxS = faxS;
    }
}
