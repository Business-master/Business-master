package com.ristone.businessasso.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/23
 */
public class TransformUtils {
    public static <T> Observable.Transformer<T, T> defaultSchedulers() {
        return new Observable.Transformer<T, T>() {

            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        .unsubscribeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
