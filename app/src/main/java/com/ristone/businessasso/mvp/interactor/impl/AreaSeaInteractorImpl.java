package com.ristone.businessasso.mvp.interactor.impl;

import com.ristone.businessasso.listener.RequestCallBack;
import com.ristone.businessasso.mvp.entity.response.RspAreaSeaBean;
import com.ristone.businessasso.mvp.interactor.NewsInteractor;
import com.ristone.businessasso.repository.network.RetrofitManager;
import com.ristone.businessasso.utils.MyUtils;
import com.ristone.businessasso.utils.TransformUtils;
import com.socks.library.KLog;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by ATRSnail on 2017/2/15.
 */

public class AreaSeaInteractorImpl implements NewsInteractor<RspAreaSeaBean> {

    @Inject
    public AreaSeaInteractorImpl() {
    }

    @Override
    public Subscription loadNews(RequestCallBack<RspAreaSeaBean> listener, int pageNum, int numPerPage, String title, int status) {
        return null;
    }

    @Override
    public Subscription loadNews(RequestCallBack<RspAreaSeaBean> listener) {
        return null;
    }

    @Override
    public Subscription loadNews(final RequestCallBack<RspAreaSeaBean> listener, int pageNum, int numPerPage, String title, String areaCode, String chambreCode) {
        return RetrofitManager.getInstance(1).getAreaSeaListObservable(pageNum,numPerPage,areaCode,chambreCode,title)
                .compose(TransformUtils.<RspAreaSeaBean>defaultSchedulers())
                .subscribe(new Subscriber<RspAreaSeaBean>() {
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
                    public void onNext(RspAreaSeaBean rspAreaSeaBean) {
                        KLog.d(rspAreaSeaBean.toString());
                        listener.success(rspAreaSeaBean);
                    }
                });

    }
}
