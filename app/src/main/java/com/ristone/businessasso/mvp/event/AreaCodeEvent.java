package com.ristone.businessasso.mvp.event;

import com.ristone.businessasso.mvp.entity.AreaBean;

/**
 * Created by ATRSnail on 2017/2/16.
 * 选择区域时，回传 数据
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
