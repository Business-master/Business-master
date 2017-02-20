package com.bus.business.mvp.entity;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
public class BusDetailBean {

    private String phoneNo;
    private int id;
    private String title;
    private double inAmount;
    private String plane;
    private String status;
    private String contactsS;
    private String contentS;
    private String fmImg;
    private String emailS;
    private long ctime;
    private int areaId;
    private String faxS;
    private long utime;
    private String types;
    private String areaCode;

    public void setInAmount(double inAmount) {
        this.inAmount = inAmount;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

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

    public double getInAmount() {
        return inAmount;
    }

    public void setInAmount(int inAmount) {
        this.inAmount = inAmount;
    }

    public String getPlane() {
        return plane;
    }

    public void setPlane(String plane) {
        this.plane = plane;
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

    public String getContentS() {
        return contentS;
    }

    public void setContentS(String contentS) {
        this.contentS = contentS;
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

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getFaxS() {
        return faxS;
    }

    public void setFaxS(String faxS) {
        this.faxS = faxS;
    }

    @Override
    public String toString() {
        return "BusDetailBean{" +
                "phoneNo='" + phoneNo + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", inAmount=" + inAmount +
                ", plane='" + plane + '\'' +
                ", status='" + status + '\'' +
                ", contactsS='" + contactsS + '\'' +
                ", contentS='" + contentS + '\'' +
                ", fmImg='" + fmImg + '\'' +
                ", emailS='" + emailS + '\'' +
                ", ctime=" + ctime +
                ", areaId=" + areaId +
                ", faxS='" + faxS + '\'' +
                ", utime=" + utime +
                ", types='" + types + '\'' +
                ", areaCode='" + areaCode + '\'' +
                '}';
    }
}
