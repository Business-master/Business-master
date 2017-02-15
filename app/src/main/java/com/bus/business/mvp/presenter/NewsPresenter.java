package com.bus.business.mvp.presenter;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public interface NewsPresenter {

    void setNewsTypeAndId(int pageNum, int numPerPage,String title,int status);

    void refreshData();

    void loadMore();
}
