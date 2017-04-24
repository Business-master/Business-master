package com.ristone.businessasso.mvp.entity;

import java.util.List;

/**
 * Created by ATRSnail on 2017/2/28.
 */

public class NationBeans {
    List<NationBean> list;

    public List<NationBean> getList() {
        return list;
    }

    public void setList(List<NationBean> list) {
        this.list = list;
    }


    @Override
    public String toString() {
        return "NationBeans{" +
                "list=" + list +
                '}';
    }
}
