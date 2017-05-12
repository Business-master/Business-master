package com.ristone.businessasso.mvp.interactor.impl;

import com.ristone.businessasso.listener.RequestCallBack;
import com.ristone.businessasso.mvp.entity.response.RspNewsBean;
import com.ristone.businessasso.mvp.entity.response.RspQuestionnaireBean;
import com.ristone.businessasso.mvp.interactor.PolicyInteractor;
import com.ristone.businessasso.repository.network.RetrofitManager;
import com.ristone.businessasso.utils.MyUtils;
import com.ristone.businessasso.utils.TransformUtils;
import com.socks.library.KLog;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;


public class QuestionnaireInteractorImpl implements PolicyInteractor<RspQuestionnaireBean> {

    @Inject
    public QuestionnaireInteractorImpl() {

    }

    @Override
    public Subscription loadNews(final RequestCallBack<RspQuestionnaireBean> listener, int pageNum, int numPerPage,String title,String types) {

        return RetrofitManager.getInstance(1).getAllQuestionnaire(pageNum, numPerPage)
                .compose(TransformUtils.<RspQuestionnaireBean>defaultSchedulers())
                .subscribe(new Subscriber<RspQuestionnaireBean>() {
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
                    public void onNext(RspQuestionnaireBean newsBean) {
                        KLog.d(newsBean.toString());
                        listener.success(newsBean);
                    }
                });
    }

}
