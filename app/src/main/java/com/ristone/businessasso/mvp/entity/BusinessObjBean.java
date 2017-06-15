package com.ristone.businessasso.mvp.entity;



import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
public class BusinessObjBean {

    private List<BusinessBean> businessList;

    public List<BusinessBean> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(List<BusinessBean> businessList) {
        this.businessList = businessList;
    }

    @Override
    public String toString() {
        return "BusinessObjBean{" +
                "businessList=" + businessList +
                '}';
    }
}
