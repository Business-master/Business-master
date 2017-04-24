package com.ristone.businessasso.mvp.entity;

/**
 * Created by ATRSnail on 2017/3/8.
 * 组织实体
 */

public class OrganBean {


    @Override
    public String toString() {
        return "OrganBean{" +
                "organizationLegalPerson='" + organizationLegalPerson + '\'' +
                ", organizationEmail='" + organizationEmail + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", utime=" + utime +
                ", organizationIndustry='" + organizationIndustry + '\'' +
                ", parentId=" + parentId +
                ", subOrgs=" + subOrgs +
                ", organizationArea='" + organizationArea + '\'' +
                ", organizationCode='" + organizationCode + '\'' +
                ", organizationTel='" + organizationTel + '\'' +
                ", ctime=" + ctime +
                ", id=" + id +
                ", organizationPhone='" + organizationPhone + '\'' +
                ", seq=" + seq +
                '}';
    }

    /**
     * organizationLegalPerson :
     * organizationEmail :
     * organizationName : 总工商联
     * utime : 1488436161000
     * organizationIndustry :
     * parentId : -1
     * subOrgs : null
     * organizationArea : 0013
     * organizationCode : 0001
     * organizationTel :
     * ctime : 1486539117000
     * id : 1
     * organizationPhone :
     * seq : 1
     */


    private String organizationLegalPerson;
    private String organizationEmail;
    private String organizationName;
    private long utime;
    private String organizationIndustry;
    private int parentId;
    private Object subOrgs;
    private String organizationArea;
    private String organizationCode;
    private String organizationTel;
    private long ctime;
    private int id;
    private String organizationPhone;
    private int seq;

    public String getOrganizationLegalPerson() {
        return organizationLegalPerson;
    }

    public void setOrganizationLegalPerson(String organizationLegalPerson) {
        this.organizationLegalPerson = organizationLegalPerson;
    }

    public String getOrganizationEmail() {
        return organizationEmail;
    }

    public void setOrganizationEmail(String organizationEmail) {
        this.organizationEmail = organizationEmail;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public String getOrganizationIndustry() {
        return organizationIndustry;
    }

    public void setOrganizationIndustry(String organizationIndustry) {
        this.organizationIndustry = organizationIndustry;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public Object getSubOrgs() {
        return subOrgs;
    }

    public void setSubOrgs(Object subOrgs) {
        this.subOrgs = subOrgs;
    }

    public String getOrganizationArea() {
        return organizationArea;
    }

    public void setOrganizationArea(String organizationArea) {
        this.organizationArea = organizationArea;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationTel() {
        return organizationTel;
    }

    public void setOrganizationTel(String organizationTel) {
        this.organizationTel = organizationTel;
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

    public String getOrganizationPhone() {
        return organizationPhone;
    }

    public void setOrganizationPhone(String organizationPhone) {
        this.organizationPhone = organizationPhone;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
