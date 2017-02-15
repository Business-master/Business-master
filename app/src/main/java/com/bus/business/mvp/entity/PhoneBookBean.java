package com.bus.business.mvp.entity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bus.business.mvp.ui.activities.PhoneDetailActivity;

import java.io.Serializable;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/2/15
 */

public class PhoneBookBean implements Serializable{

    public static final String PHONE_BEAN = "phone_bean";
    /**
     * phoneNo : 13312341233
     * niceName : 李彦宏
     * id : wriMtgNnVyeB8HPW4sxNV6w4xUlL4Zit
     * organizationArea :
     * organizationName : 百度集团
     * userEmail : 1@qq.com
     */

    private String phoneNo;
    private String niceName;
    private String id;
    private String organizationArea;
    private String organizationName;
    private String userEmail;

    public void intentToNext(Context context){
        Intent intent = new Intent(context,PhoneDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PHONE_BEAN,this);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationArea() {
        return organizationArea;
    }

    public void setOrganizationArea(String organizationArea) {
        this.organizationArea = organizationArea;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "PhoneBookBean{" +
                "phoneNo='" + phoneNo + '\'' +
                ", niceName='" + niceName + '\'' +
                ", id='" + id + '\'' +
                ", organizationArea='" + organizationArea + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
