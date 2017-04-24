package com.ristone.businessasso.mvp.entity;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
public class BannersBean {

    private List<BannerBean> newsBannerList;

    public List<BannerBean> getNewsBannerList() {
        return newsBannerList;
    }

    public void setNewsBannerList(List<BannerBean> newsBannerList) {
        this.newsBannerList = newsBannerList;
    }

    @Override
    public String toString() {
        return "BannersBean{" +
                "newsBannerList=" + newsBannerList +
                '}';
    }
}
