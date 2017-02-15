package com.bus.business.mvp.entity;

/**
 * Created by ATRSnail on 2017/1/19.
 */

public class AssisBean {
    /**
     * phoneNo : 13520388357
     * id : 0Qq4sesdRckiVhwjMFOEiEIenvV4KwyQ
     * userName : fhz12
     * isAssistant : 2 //1不是助理，2:助理，3:开始代理
     */

    private String phoneNo;
    private String id;
    private String userName;
    private int isAssistant;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIsAssistant() {
        return isAssistant;
    }

    public void setIsAssistant(int isAssistant) {
        this.isAssistant = isAssistant;
    }


    @Override
    public String toString() {
        return "AssisBean{"+"id="+id
                +"userName="+userName
                +"phoneNo="+phoneNo
                +"isAssistant="+isAssistant+"}";


    }



}
