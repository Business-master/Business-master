package com.bus.business.mvp.interactor.impl;

import com.bus.business.listener.RequestCallBack;
import com.bus.business.mvp.entity.response.RspAreaBean;
import com.bus.business.mvp.interactor.NewsInteractor;
import com.bus.business.repository.network.RetrofitManager;
import com.bus.business.utils.MyUtils;
import com.bus.business.utils.TransformUtils;
import com.socks.library.KLog;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by ATRSnail on 2017/2/15.
 */

public class AreaInteractorImpl implements NewsInteractor<RspAreaBean> {

    @Inject
    public AreaInteractorImpl() {
    }

    @Override
    public Subscription loadNews(RequestCallBack<RspAreaBean> listener, int pageNum, int numPerPage, String title, int status) {
        return null;
    }

    @Override
    public Subscription loadNews(final RequestCallBack<RspAreaBean> listener) {

        return RetrofitManager.getInstance(1).getAreaListObservable()
                .compose(TransformUtils.<RspAreaBean>defaultSchedulers())
                .subscribe(new Subscriber<RspAreaBean>() {
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
                    public void onNext(RspAreaBean rspAreaBean) {
                        KLog.d(rspAreaBean.toString());
                        listener.success(rspAreaBean);
                    }
                });
    }

    @Override
    public Subscription loadNews(RequestCallBack<RspAreaBean> listener, int pageNum, int numPerPage, String title, String areaCode, String chambreCode) {
        return null;
    }


}
