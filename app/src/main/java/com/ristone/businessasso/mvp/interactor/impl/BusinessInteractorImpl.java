package com.ristone.businessasso.mvp.interactor.impl;

import com.ristone.businessasso.listener.RequestCallBack;
import com.ristone.businessasso.mvp.entity.response.RspBusinessBean;
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
public class BusinessInteractorImpl implements NewsInteractor<RspBusinessBean> {

    @Inject
    public BusinessInteractorImpl() {

    }
    @Override
    public Subscription loadNews(final RequestCallBack<RspBusinessBean> listener, int pageNum, int numPerPage, String title,int status) {
        return RetrofitManager.getInstance(1).getBusinessListObservable(pageNum, numPerPage, title)
                .compose(TransformUtils.<RspBusinessBean>defaultSchedulers())
                .subscribe(new Subscriber<RspBusinessBean>() {
                    @Override
                    public void onCompleted() {
                        KLog.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(MyUtils.analyzeNetworkError(e));
                    }

                    @Override
                    public void onNext(RspBusinessBean rspBusinessBean) {
                        KLog.d(rspBusinessBean.toString());
                        listener.success(rspBusinessBean);
                    }
                });
    }

    @Override
    public Subscription loadNews(RequestCallBack<RspBusinessBean> listener) {
        return null;
    }

    @Override
    public Subscription loadNews(RequestCallBack<RspBusinessBean> listener, int pageNum, int numPerPage, String title, String areaCode, String chambreCode) {
        return null;
    }


}
