package com.bus.business.mvp.entity;

import java.util.List;

/**
 * Created by ATRSnail on 2017/2/24.
 */

public class MeetingFilesBean {
    List<MeetingFileBean> fileList;

    public List<MeetingFileBean> getFileList() {
        return fileList;
    }

    public void setFileList(List<MeetingFileBean> fileList) {
        this.fileList = fileList;
    }

    @Override
    public String toString() {
        return "MeetingFilesBean{" +
                "fileList=" + fileList +
                '}';
    }
}
