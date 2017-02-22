package com.bus.business.mvp.event;

/**
 * Created by ATRSnail on 2017/2/22.
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
