package com.ristone.businessasso.mvp.event;

/**
 * Created by ATRSnail on 2017/6/15.
 * 桌面角标 数量
 */
@Deprecated
public class BadgeEvent {

    int notReadCount;

    public BadgeEvent(int notReadCount) {
        this.notReadCount = notReadCount;
    }

    public int getNotReadCount() {
        return notReadCount;
    }
}
