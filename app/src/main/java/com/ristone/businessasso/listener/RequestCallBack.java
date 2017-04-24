package com.ristone.businessasso.listener;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public interface RequestCallBack<T> {
    void beforeRequest();

    void success(T data);

    void onError(String errorMsg);
}
