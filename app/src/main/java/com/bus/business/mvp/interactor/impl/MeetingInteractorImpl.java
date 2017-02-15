package com.bus.business.mvp.interactor.impl;

import com.bus.business.listener.RequestCallBack;
import com.bus.business.mvp.entity.response.RspMeetingBean;
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
}

