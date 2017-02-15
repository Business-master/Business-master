package com.bus.business.mvp.interactor.impl;


import android.util.Log;

import com.bus.business.listener.RequestCallBack;
import com.bus.business.mvp.entity.response.RspAssisBean;
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
 * @create_date 16/12/28
 */
public class AssisInteractorImpl implements NewsInteractor<RspAssisBean> {

    @Inject
    public AssisInteractorImpl() {

    }


    @Override
    public Subscription loadNews(final RequestCallBack<RspAssisBean> listener, int pageNum, int numPerPage, String title,int status) {
        return RetrofitManager.getInstance(1).getAssissListObservable()
                .compose(TransformUtils.<RspAssisBean>defaultSchedulers())
                .subscribe(new Subscriber<RspAssisBean>() {
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
                    public void onNext(RspAssisBean rspAssisBean) {
                        KLog.d(rspAssisBean.toString());
                        listener.success(rspAssisBean);
                    }
                });
    }

    @Override
    public Subscription loadNews(RequestCallBack<RspAssisBean> listener) {
        return null;
    }

    @Override
    public Subscription loadNews(RequestCallBack<RspAssisBean> listener, int pageNum, int numPerPage, String title, String areaCode, String chambreCode) {
        return null;
    }


}
