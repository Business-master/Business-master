package com.bus.business.mvp.entity;

import java.util.List;

/**
 * Created by ATRSnail on 2017/2/15.
 */

public class AreasBean {
    List<AreaBean>  areaList;

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
                '}';
    }
}
