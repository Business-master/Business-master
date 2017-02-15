package com.bus.business.mvp.interactor.impl;

import com.bus.business.listener.RequestCallBack;
import com.bus.business.mvp.entity.response.RspNewsBean;
import com.bus.business.mvp.interactor.NewsInteractor;
import com.bus.business.repository.network.RetrofitManager;
import com.bus.business.utils.MyUtils;
import com.bus.business.utils.TransformUtils;
import com.socks.library.KLog;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public class NewsInteractorImpl implements NewsInteractor<RspNewsBean> {

    @Inject
    public NewsInteractorImpl() {

    }

    @Override
    public Subscription loadNews(final RequestCallBack<RspNewsBean> listener, int pageNum, int numPerPage,String title,int status) {

        return RetrofitManager.getInstance(1).getNewsListObservable(pageNum, numPerPage,title)
                .compose(TransformUtils.<RspNewsBean>defaultSchedulers())
                .subscribe(new Subscriber<RspNewsBean>() {
                    @Override
                    public void onCompleted() {
                        KLog.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                        listener.onError(MyUtils.analyzeNetworkError(e));
                    }

                    @Override
                    public void onNext(RspNewsBean newsBean) {
                        KLog.d(newsBean.toString());
                        listener.success(newsBean);
                    }
                });
    }

    @Override
    public Subscription loadNews(RequestCallBack<RspNewsBean> listener) {
        return null;
    }

    @Override
    public Subscription loadNews(RequestCallBack<RspNewsBean> listener, int pageNum, int numPerPage, String title, String areaCode, String chambreCode) {
        return null;
    }


}
