package com.bus.business.mvp.event;

/**
 * Created by ATRSnail on 2017/4/19.
 * 助理报名 是否 取消 事件
 */

public class AssisApplyEvent {

    boolean flag;//true  助理弹出框，点击了 x（取消） 通知跳转 到本人（参会人）

    public AssisApplyEvent(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }
}
