package com.ristone.businessasso.mvp.entity;

import java.util.List;

/**
 * Created by ATRSnail on 2017/3/8.
 */

public class OrganBeans {
    List<OrganBean> list;

    public List<OrganBean> getList() {
        return list;
    }

    public void setList(List<OrganBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "OrganBeans{" +
                "list=" + list +
                '}';
    }
}
