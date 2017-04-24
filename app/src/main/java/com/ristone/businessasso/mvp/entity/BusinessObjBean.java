package com.ristone.businessasso.mvp.entity;

import com.ristone.businessasso.mvp.entity.response.base.BaseNewBean;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
public class BusinessObjBean {

    private List<BaseNewBean> businessList;

    public List<BaseNewBean> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(List<BaseNewBean> businessList) {
        this.businessList = businessList;
    }

    @Override
    public String toString() {
        return "BusinessObjBean{" +
                "businessList=" + businessList +
                '}';
    }
}
