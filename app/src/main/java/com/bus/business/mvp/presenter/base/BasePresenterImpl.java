package com.bus.business.mvp.presenter.base;

import android.support.annotation.NonNull;

import com.bus.business.listener.RequestCallBack;
import com.bus.business.mvp.view.base.BaseView;
import com.bus.business.utils.MyUtils;

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
