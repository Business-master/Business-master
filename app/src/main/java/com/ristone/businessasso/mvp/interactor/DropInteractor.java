package com.ristone.businessasso.mvp.interactor;

import com.ristone.businessasso.listener.RequestCallBack;
import com.ristone.businessasso.mvp.entity.CashBean;

import rx.Subscription;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public interface DropInteractor<T> {
    Subscription loadNews(RequestCallBack<T> listener, int pageNum, int numPerPage, CashBean cashBean);
}
