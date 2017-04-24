package com.ristone.businessasso.mvp.entity;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/2/9
 */

public class TopicsBean {

    private List<TopicBean> disList;

    public List<TopicBean> getDisList() {
        return disList;
    }

    public void setDisList(List<TopicBean> disList) {
        this.disList = disList;
    }

    @Override
    public String toString() {
        return "TopicsBean{" +
                "disList=" + disList +
                '}';
    }
}
