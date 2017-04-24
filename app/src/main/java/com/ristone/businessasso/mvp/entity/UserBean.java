package com.ristone.businessasso.mvp.entity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ristone.businessasso.mvp.ui.activities.ManagerActivity;

import java.io.Serializable;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
public class UserBean implements Serializable{


    private String position;
    private String niceName;
    private long utime;
    private String sex;
    private String assistantedId;
    private String imei;
    private String state;
    private Object hasAssiistant;
    private String userEmail;
    private String companyName;
    private String qgslPosition;
    private long ctime;
    private String sgslPosition;
    private String nation;
    private String phoneModel;
    private Object organizationCode;
    private String id;
    private String phoneNo;
    private String systemeType;
    private String qShPosition;//区商会职务
    private String sShPosition;//市商会职务
    private String userName;
    private int isAssistant;
    private Object organizationType;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAssistantedId() {
        return assistantedId;
    }

    public void setAssistantedId(String assistantedId) {
        this.assistantedId = assistantedId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getHasAssiistant() {
        return hasAssiistant;
    }

    public void setHasAssiistant(Object hasAssiistant) {
        this.hasAssiistant = hasAssiistant;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getQgslPosition() {
        return qgslPosition;
    }

    public void setQgslPosition(String qgslPosition) {
        this.qgslPosition = qgslPosition;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getSgslPosition() {
        return sgslPosition;
    }

    public void setSgslPosition(String sgslPosition) {
        this.sgslPosition = sgslPosition;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public Object getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(Object organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getSystemeType() {
        return systemeType;
    }

    public void setSystemeType(String systemeType) {
        this.systemeType = systemeType;
    }

    public String getQShPosition() {
        return qShPosition;
    }

    public void setQShPosition(String qShPosition) {
        this.qShPosition = qShPosition;
    }

    public String getSShPosition() {
        return sShPosition;
    }

    public void setSShPosition(String sShPosition) {
        this.sShPosition = sShPosition;
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

    public Object getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(Object organizationType) {
        this.organizationType = organizationType;
    }

    /**
     * position : 董事长  //职位
     * niceName : 我是android
     * utime : 1486628868000
     * sex : 男
     * assistantedId :  //助理id
     * imei : 1234567  //手机串码
     * state : 1    //状态0停用1启用
     * hasAssiistant : null
     * userEmail : 123@qq.com  //用户邮箱
     * companyName : 测试公司 //公司名称
     * qgslPosition : null //区工商联职位
     * ctime : 1484281713000
     * sgslPosition : null //市工商联职位
     * nation : 汉族
     * phoneModel : 1234567      //手机型号
     * organizationCode : null
     * id : SSETJtMrdYRNmhkTLnFDBIRqXwwplVCa
     * phoneNo : 18500241615
     * systemeType : 2  // //用户手机系统 1是IOS 2是安卓
     * userName : xuchunhui-android  //用户名
     * isAssistant : 1  本人 2 助理  3助理被选取
     * organizationType : null  //组织类型
     */
//
//
//
    public static final String USER_BEAN = "user_bean";


    public void intentToClass(Context context){
        Intent intent = new Intent(context,ManagerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(USER_BEAN,this);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


}
