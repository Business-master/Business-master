package com.bus.business.mvp.event;

import com.bus.business.mvp.entity.AreaBean;
import com.bus.business.mvp.entity.AreaSeaBean;

/**
 * Created by ATRSnail on 2017/3/27.
 */

public class AreaFirstEvent {
    //基层组织 页面 获得---初始化地区---第一个地区名

    AreaSeaBean areaSeaBean;

    public AreaFirstEvent(AreaSeaBean areaSeaBean) {
        this.areaSeaBean = areaSeaBean;
    }

    public AreaSeaBean getAreaSeaBean() {
        return areaSeaBean;
    }
}
