package com.ristone.businessasso.mvp.interactor.impl;

import com.ristone.businessasso.listener.RequestCallBack;
import com.ristone.businessasso.mvp.entity.response.RspNewsBean;
import com.ristone.businessasso.mvp.interactor.NewsInteractor;
import com.ristone.businessasso.mvp.interactor.PolicyInteractor;
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
 * @create_date 16/12/22
 */
public class NewsInteractorImpl implements PolicyInteractor<RspNewsBean> {

    @Inject
    public NewsInteractorImpl() {

    }

    @Override
    public Subscription loadNews(final RequestCallBack<RspNewsBean> listener, int pageNum, int numPerPage,String title,String types) {

        return RetrofitManager.getInstance(1).getNewsListObservable(pageNum, numPerPage,title,types)
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

}
