package com.bus.business.mvp.entity;

import com.bus.business.mvp.entity.response.base.BaseNewBean;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
@Deprecated
public class BusinessBean extends BaseNewBean{


    /**
     * phoneNo : 1234123412
     * id : 1
     * utime : 1482473380000
     * title : 商讯标题
     * inAmount : 11
     * plane : 1234123
     * status : 0
     * contactsS : 联系人
     * fmImg : 123.123.123.123:1111
     * emailS : 321@11.com
     * ctime : 1482459183000
     * areaId : 1
     * faxS : 111-1111
     */

    private String phoneNo;
    private int id;
    private long utime;
    private int inAmount;
    private String plane;
    private String status;
    private String contactsS;
    private String emailS;
    private String faxS;

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

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public int getInAmount() {
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

    public String getEmailS() {
        return emailS;
    }

    public void setEmailS(String emailS) {
        this.emailS = emailS;
    }

    public String getFaxS() {
        return faxS;
    }

    public void setFaxS(String faxS) {
        this.faxS = faxS;
    }

    @Override
    public String toString() {
        return "BusinessBean{" +
                "phoneNo='" + phoneNo + '\'' +
                ", id=" + id +
                ", utime=" + utime +
                ", inAmount=" + inAmount +
                ", plane='" + plane + '\'' +
                ", status='" + status + '\'' +
                ", contactsS='" + contactsS + '\'' +
                ", emailS='" + emailS + '\'' +
                ", faxS='" + faxS + '\'' +
                '}';
    }
}
