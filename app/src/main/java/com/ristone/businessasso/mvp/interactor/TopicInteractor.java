package com.ristone.businessasso.mvp.interactor;

import com.ristone.businessasso.listener.RequestCallBack;

import rx.Subscription;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public interface TopicInteractor<T> {
    Subscription loadNews(RequestCallBack<T> listener, int pageNum, int numPerPage, String title);
}
