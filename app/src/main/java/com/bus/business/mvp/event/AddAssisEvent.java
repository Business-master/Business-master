package com.bus.business.mvp.event;

/**
 * Created by ATRSnail on 2017/3/3.
 * 添加助理事件
 */

public class AddAssisEvent {
    int addAssis;

    public AddAssisEvent(int addAssis) {
        this.addAssis = addAssis;
    }

    public int getAddAssis() {
        return addAssis;
    }
}
