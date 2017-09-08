package com.ristone.businessasso.mvp.entity;

import java.util.List;

/**
 * Created by ATRSnail on 2017/3/9.
 */

public class UserInfoBeans {
    UserInfoBean  list;

    public UserInfoBean getList() {
        return list;
    }

    public void setList(UserInfoBean list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "UserInfoBeans{" +
                "list=" + list +
                '}';
    }
}
