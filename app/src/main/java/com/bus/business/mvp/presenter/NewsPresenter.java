package com.bus.business.mvp.presenter;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public interface NewsPresenter {

    //status如果不用传值-1
    void setNewsTypeAndId(int pageNum, int numPerPage,String title,int status);

    //areaCode,chambreCode如果不用传值""
    void setNewsTypeAndId(int pageNum, int numPerPage,String title,String areaCode,String  chambreCode);

    void refreshData();

    void loadMore();
}
