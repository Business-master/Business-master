package com.ristone.businessasso.mvp.entity;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
public class NewDetailsBean {

    private BusDetailBean news;

    public BusDetailBean getNews() {
        return news;
    }

    public void setNews(BusDetailBean news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "NewDetailsBean{" +
                "news=" + news +
                '}';
    }
}
