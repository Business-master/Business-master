package com.ristone.businessasso.mvp.interactor;

import com.ristone.businessasso.listener.RequestCallBack;

import rx.Subscription;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public interface NewsInteractor<T> {
    Subscription loadNews(RequestCallBack<T> listener,int pageNum, int numPerPage,String title,int status);

    //地区行业字典查询
    Subscription loadNews(RequestCallBack<T> listener);

    //根据区域和行业查询新闻商讯
    Subscription loadNews(RequestCallBack<T> listener,int pageNum, int numPerPage,String title,String areaCode,String chambreCode);
}
