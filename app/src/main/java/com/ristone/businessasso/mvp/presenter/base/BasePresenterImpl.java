package com.ristone.businessasso.mvp.presenter.base;

import android.support.annotation.NonNull;

import com.ristone.businessasso.listener.RequestCallBack;
import com.ristone.businessasso.mvp.view.base.BaseView;
import com.ristone.businessasso.utils.MyUtils;

import rx.Subscription;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public class BasePresenterImpl<T extends BaseView, E> implements BasePresenter, RequestCallBack<E> {
    protected T mView;
    protected Subscription mSubscription;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(@NonNull BaseView view) {
        mView = (T) view;
    }

    @Override
    public void onDestory() {
        MyUtils.cancelSubscription(mSubscription);
    }

    @Override
    public void beforeRequest() {
        mView.showProgress();
    }

    @Override
    public void success(E data) {
        mView.hideProgress();
    }

    @Override
    public void onError(String errorMsg) {
        mView.hideProgress();
        mView.showMsg(errorMsg);
    }
}
