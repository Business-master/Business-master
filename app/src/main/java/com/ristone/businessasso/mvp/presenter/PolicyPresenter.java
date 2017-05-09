package com.ristone.businessasso.mvp.presenter;

/**
 */
public interface PolicyPresenter {


    void setNewsTypeAndId(int pageNum, int numPerPage, String title, String types);


    void refreshData();

    void loadMore();
}
