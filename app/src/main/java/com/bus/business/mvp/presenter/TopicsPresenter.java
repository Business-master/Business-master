package com.bus.business.mvp.presenter;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public interface TopicsPresenter {

    void setNewsTypeAndId(int pageNum, int numPerPage, String title);

    void refreshData();

    void loadMore();
}
