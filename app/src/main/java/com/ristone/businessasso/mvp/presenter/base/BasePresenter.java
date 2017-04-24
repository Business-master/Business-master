package com.ristone.businessasso.mvp.presenter.base;

import android.support.annotation.NonNull;

import com.ristone.businessasso.mvp.view.base.BaseView;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public interface BasePresenter {

    void onCreate();
    void attachView(@NonNull BaseView view);
    void onDestory();
}
