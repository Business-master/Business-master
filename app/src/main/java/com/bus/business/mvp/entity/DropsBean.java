package com.bus.business.mvp.entity;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/2/17
 */

public class DropsBean {
    private List<DropBean> cashProfessorList;

    public List<DropBean> getCashProfessorList() {
        return cashProfessorList;
    }

    public void setCashProfessorList(List<DropBean> cashProfessorList) {
        this.cashProfessorList = cashProfessorList;
    }

    @Override
    public String toString() {
        return "DropsBean{" +
                "cashProfessorList=" + cashProfessorList +
                '}';
    }
}
