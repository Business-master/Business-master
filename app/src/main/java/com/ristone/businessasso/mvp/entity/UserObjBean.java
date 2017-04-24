package com.ristone.businessasso.mvp.entity;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
public class UserObjBean {

    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserObjBean{" +
                "user=" + user +
                '}';
    }
}
