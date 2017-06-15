package com.ristone.businessasso.mvp.entity;



import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public class NewsBean {
    private List<XinWenBean> newsList;

    public List<XinWenBean> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<XinWenBean> newsList) {
        this.newsList = newsList;
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "newsList=" + newsList +
                '}';
    }
}
