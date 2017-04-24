package com.ristone.businessasso.mvp.interactor.impl;


import com.ristone.businessasso.listener.RequestCallBack;
import com.ristone.businessasso.mvp.entity.response.RspAssisBean;
import com.ristone.businessasso.mvp.interactor.NewsInteractor;
import com.ristone.businessasso.repository.network.RetrofitManager;
import com.ristone.businessasso.utils.MyUtils;
import com.ristone.businessasso.utils.TransformUtils;
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
