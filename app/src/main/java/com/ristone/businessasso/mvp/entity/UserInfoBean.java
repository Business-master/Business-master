package com.ristone.businessasso.mvp.entity;

/**
 * Created by ATRSnail on 2017/3/9.
 */

public class UserInfoBean {


    @Override
    public String toString() {
        return "UserInfoBean{" +
                "assistantedId='" + assistantedId + '\'' +
                ", passWord='" + passWord + '\'' +
                ", isAssistant=" + isAssistant +
                ", nation='" + nation + '\'' +
                ", utime=" + utime +
                ", companyName='" + companyName + '\'' +
                ", sex='" + sex + '\'' +
                ", hasAssiistant=" + hasAssiistant +
                ", sgslPosition='" + sgslPosition + '\'' +
                ", userName='" + userName + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", qgslPosition='" + qgslPosition + '\'' +
                ", organizationType='" + organizationType + '\'' +
                ", phoneModel='" + phoneModel + '\'' +
                ", organizationCode='" + organizationCode + '\'' +
                ", systemeType='" + systemeType + '\'' +
                ", ctime=" + ctime +
                ", imei='" + imei + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", id='" + id + '\'' +
                ", niceName='" + niceName + '\'' +
                ", position='" + position + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    /**
     * assistantedId : Ojmdu5iLVxWZhlzyPYWwfkiZj3CvCMEc
     * passWord : a555555
     * isAssistant : 2
     * nation : 回族
     * utime : null
     * companyName : 阿里音乐
     * sex : 男
     * hasAssiistant : null
     * sgslPosition :
     * userName : 猪猪猪
     * phoneNo : 13555555555
     * qgslPosition :
     * organizationType :
     * phoneModel :
     * organizationCode :
     * systemeType :
     * ctime : 1488959263000
     * imei :
     * userEmail :
     * id : UPXchxTtoy1rt12i7BxBTUxLEHxms8GB
     * niceName : 猪猪猪
     * position : 总裁
     * state : 1
     */


    private String assistantedId;
    private String passWord;
    private int isAssistant;
    private String nation;
    private Object utime;
    private String companyName;
    private String sex;
    private Object hasAssiistant;
    private String sgslPosition;
    private String userName;
    private String phoneNo;
    private String qgslPosition;
    private String organizationType;
    private String phoneModel;
    private String organizationCode;
    private String systemeType;
    private long ctime;
    private String imei;
    private String userEmail;
    private String id;
    private String niceName;
    private String position;
    private String state;

    public String getAssistantedId() {
        return assistantedId;
    }

    public void setAssistantedId(String assistantedId) {
        this.assistantedId = assistantedId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getIsAssistant() {
        return isAssistant;
    }

    public void setIsAssistant(int isAssistant) {
        this.isAssistant = isAssistant;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Object getUtime() {
        return utime;
    }

    public void setUtime(Object utime) {
        this.utime = utime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Object getHasAssiistant() {
        return hasAssiistant;
    }

    public void setHasAssiistant(Object hasAssiistant) {
        this.hasAssiistant = hasAssiistant;
    }

    public String getSgslPosition() {
        return sgslPosition;
    }

    public void setSgslPosition(String sgslPosition) {
        this.sgslPosition = sgslPosition;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getQgslPosition() {
        return qgslPosition;
    }

    public void setQgslPosition(String qgslPosition) {
        this.qgslPosition = qgslPosition;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getSystemeType() {
        return systemeType;
    }

    public void setSystemeType(String systemeType) {
        this.systemeType = systemeType;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
