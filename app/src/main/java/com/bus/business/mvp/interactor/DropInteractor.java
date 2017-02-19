package com.bus.business.mvp.interactor;

import com.bus.business.listener.RequestCallBack;
import com.bus.business.mvp.entity.CashBean;

import rx.Subscription;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public interface DropInteractor<T> {
    Subscription loadNews(RequestCallBack<T> listener, int pageNum, int numPerPage, CashBean cashBean);
}
