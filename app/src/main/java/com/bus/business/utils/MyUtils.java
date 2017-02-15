package com.bus.business.utils;

import com.bus.business.App;
import com.bus.business.R;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscription;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public class MyUtils {

    public static void cancelSubscription(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public static String analyzeNetworkError(Throwable e) {
        String errMsg = App.getAppContext().getString(R.string.load_error);
        if (e instanceof HttpException) {
            int state = ((HttpException) e).code();
            if (state == 403) {
                errMsg = App.getAppContext().getString(R.string.retry_after);
            }
        }
        return errMsg;
    }

}
