package com.ristone.businessasso.mvp.interactor;

import com.ristone.businessasso.listener.RequestCallBack;
import com.ristone.businessasso.mvp.entity.CashBean;
import com.ristone.businessasso.mvp.entity.response.RspDropBean;
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
