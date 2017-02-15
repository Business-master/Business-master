package com.bus.business.mvp.entity.response;

import com.bus.business.mvp.entity.PhoneBookBean;

import java.util.ArrayList;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/2/15
 */

public class PhoneBooksBean {

    private ArrayList<PhoneBookBean> phoneBookList;

    public ArrayList<PhoneBookBean> getPhoneBookList() {
        return phoneBookList;
    }

    public void setPhoneBookList(ArrayList<PhoneBookBean> phoneBookList) {
        this.phoneBookList = phoneBookList;
    }

    @Override
    public String toString() {
        return "PhoneBooksBean{" +
                "phoneBookList=" + phoneBookList +
                '}';
    }
}
