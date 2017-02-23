package com.bus.business.mvp.event;

import com.bus.business.mvp.entity.AreaBean;
import com.bus.business.mvp.entity.AreaSeaBean;

/**
 * Created by ATRSnail on 2017/2/16.
 */

public class AreaFirstEvent {
   AreaSeaBean areaSeaBean;

    public AreaFirstEvent(AreaSeaBean areaSeaBean) {
        this.areaSeaBean = areaSeaBean;
    }

    public AreaSeaBean getAreaSeaBean() {
        return areaSeaBean;
    }
}
