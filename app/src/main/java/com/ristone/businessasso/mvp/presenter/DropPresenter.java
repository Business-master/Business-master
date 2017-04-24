package com.ristone.businessasso.mvp.presenter;

import com.ristone.businessasso.mvp.entity.CashBean;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public interface DropPresenter {

    void setNewsTypeAndId(int pageNum, int numPerPage, CashBean cashBean);

    void dropData(CashBean cashBean);

    void refreshData();

    void loadMore();
}
