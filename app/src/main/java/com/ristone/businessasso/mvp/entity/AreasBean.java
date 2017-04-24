package com.ristone.businessasso.mvp.entity;

import java.util.List;

/**
 * Created by ATRSnail on 2017/2/15.
 */

public class AreasBean {
    List<AreaBean>  areaList;

    List<AreaBean>  chambrelist;

    public List<AreaBean> getChambrelist() {
        return chambrelist;
    }

    public void setChambrelist(List<AreaBean> chambrelist) {
        this.chambrelist = chambrelist;
    }

    public List<AreaBean> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaBean> areaList) {
        this.areaList = areaList;
    }

    @Override
    public String toString() {
        return "AreasBean{" +
                "areaList=" + areaList +
                ", chambrelist=" + chambrelist +
                '}';
    }
}
