package com.bus.business.mvp.event;

import com.bus.business.mvp.entity.AreaBean;

/**
 * Created by ATRSnail on 2017/2/16.
 */

public class AreaCodeEvent {
    AreaBean areaBean;
    boolean Area;//true 代表选择的是工商联  false商协会

    public boolean getArea() {
        return Area;
    }

    public AreaCodeEvent(AreaBean areaBean, boolean isArea) {
        this.areaBean = areaBean;
        this.Area=isArea;
    }

    public AreaBean getAreaBean() {
        return areaBean;
    }

}
