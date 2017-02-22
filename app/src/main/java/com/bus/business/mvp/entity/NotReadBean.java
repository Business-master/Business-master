package com.bus.business.mvp.entity;

import com.bus.business.mvp.entity.response.base.BaseRspObj;

/**
 * Created by ATRSnail on 2017/2/22.
 */

public class NotReadBean extends BaseRspObj<NotReadBean>{
    int notReaded;

    public int getNotReaded() {
        return notReaded;
    }

    public void setNotReaded(int notReaded) {
        this.notReaded = notReaded;
    }

    @Override
    public String toString() {
        return "NotReadBean{" +
                "notReaded=" + notReaded +
                '}';
    }
}
