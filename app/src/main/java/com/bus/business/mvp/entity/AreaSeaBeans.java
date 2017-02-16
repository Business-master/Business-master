package com.bus.business.mvp.entity;

import java.util.List;

/**
 * Created by ATRSnail on 2017/2/15.
 * 根据区域 请求的 商讯、新闻
 */

public class AreaSeaBeans {
  List<AreaSeaBean>  resourceList;

    public List<AreaSeaBean> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<AreaSeaBean> resourceList) {
        this.resourceList = resourceList;
    }

    @Override
    public String toString() {
        return "AreaSeaBeans{" +
                "resourceList=" + resourceList +
                '}';
    }
}
