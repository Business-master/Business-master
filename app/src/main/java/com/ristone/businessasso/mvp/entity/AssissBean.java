package com.ristone.businessasso.mvp.entity;

import java.util.List;

/**
 * Created by ATRSnail on 2017/1/19.
 */

public class AssissBean  {
    private List<AssisBean> list;

    public List<AssisBean> getList() {
        return list;
    }

    public void setList(List<AssisBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "AssissBean{" +
                "list=" + list +
                '}';
    }
}
