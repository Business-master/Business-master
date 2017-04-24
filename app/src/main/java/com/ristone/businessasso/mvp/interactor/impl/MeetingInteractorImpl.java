package com.ristone.businessasso.mvp.interactor.impl;

import com.ristone.businessasso.listener.RequestCallBack;
import com.ristone.businessasso.mvp.entity.response.RspMeetingBean;
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
 * @create_date 16/12/24
 */
public class MeetingInteractorImpl implements NewsInteractor<RspMeetingBean> {

    @Inject
    public MeetingInteractorImpl() {

    }



    @Override
    public Subscription loadNews(final RequestCallBack<RspMeetingBean> listener, int pageNum, int numPerPage,String title,int status) {
        return RetrofitManager.getInstance(1).getMeetingsListObservable(pageNum, numPerPage,title,status)
                .compose(TransformUtils.<RspMeetingBean>defaultSchedulers())
                .subscribe(new Subscriber<RspMeetingBean>() {
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
                    public void onNext(RspMeetingBean newsBean) {
                        KLog.d(newsBean.toString());
                        listener.success(newsBean);
                    }
                });
    }

    @Override
    public Subscription loadNews(RequestCallBack<RspMeetingBean> listener) {
        return null;
    }

    @Override
    public Subscription loadNews(RequestCallBack<RspMeetingBean> listener, int pageNum, int numPerPage, String title, String areaCode, String chambreCode) {
        return null;
    }


}

