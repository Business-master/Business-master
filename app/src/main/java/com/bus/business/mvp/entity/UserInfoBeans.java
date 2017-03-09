package com.bus.business.mvp.entity;

import java.util.List;

/**
 * Created by ATRSnail on 2017/3/9.
 */

public class UserInfoBeans {
    List<UserInfoBean>  list;

    public List<UserInfoBean> getList() {
        return list;
    }

    public void setList(List<UserInfoBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "UserInfoBeans{" +
                "list=" + list +
                '}';
    }
}
