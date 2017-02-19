package com.bus.business.mvp.interactor;

import com.bus.business.listener.RequestCallBack;
import com.bus.business.mvp.entity.CashBean;
import com.bus.business.mvp.entity.response.RspDropBean;
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
public class DropInteractorImpl implements DropInteractor<RspDropBean> {

    @Inject
    public DropInteractorImpl() {

    }
    @Override
    public Subscription loadNews(final RequestCallBack<RspDropBean> listener, int pageNum, int numPerPage, CashBean cashBean) {
        return RetrofitManager.getInstance(1).getDropListObservable(pageNum, numPerPage, cashBean)
                .compose(TransformUtils.<RspDropBean>defaultSchedulers())
                .subscribe(new Subscriber<RspDropBean>() {
                    @Override
                    public void onCompleted() {
                        KLog.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(MyUtils.analyzeNetworkError(e));
                    }

                    @Override
                    public void onNext(RspDropBean rspBusinessBean) {
                        KLog.d(rspBusinessBean.toString());
                        listener.success(rspBusinessBean);
                    }
                });
    }

}
