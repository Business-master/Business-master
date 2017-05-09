package com.ristone.businessasso.mvp.interactor;

import com.ristone.businessasso.listener.RequestCallBack;

import rx.Subscription;

/**
 * 金融政策  和 新闻   types  内容类型  2新闻  4金融政策
 */
public interface PolicyInteractor<T> {

    Subscription loadNews(RequestCallBack<T> listener, int pageNum, int numPerPage, String title, String types);
}
