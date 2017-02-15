package com.bus.business.mvp.entity;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
public class BusDetailObjBean {

    private BusDetailBean business;

    public BusDetailBean getBusiness() {
        return business;
    }

    public void setBusiness(BusDetailBean business) {
        this.business = business;
    }

    @Override
    public String toString() {
        return "BusDetailObjBean{" +
                "business=" + business +
                '}';
    }
}
