package com.ristone.businessasso.mvp.event;

/**
 * Created by ATRSnail on 2017/2/22.
 * 会议是否 读取 （查看详情）
 */

public class ReadMeeting {
    boolean read;

    public ReadMeeting(boolean read) {
        this.read = read;
    }

    public boolean isRead() {
        return read;
    }
}
