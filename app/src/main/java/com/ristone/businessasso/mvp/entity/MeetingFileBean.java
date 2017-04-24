package com.ristone.businessasso.mvp.entity;

/**
 * Created by ATRSnail on 2017/2/24.
 */

public class MeetingFileBean {


    /**
     * fileSize : 47.6
     * id : 8
     * utime : 1487840559000
     * trueFileName : d6bcf58c12c84247a69cc8b7d480ea85.jpg
     * meetingId : 68
     * fileUnit : kB
     * ctime : 1487840559000
     * showFileName : 11.jpg
     */

    private String fileSize;
    private int id;
    private long utime;
    private String trueFileName;
    private int meetingId;
    private String fileUnit;
    private long ctime;
    private String showFileName;

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public String getTrueFileName() {
        return trueFileName;
    }

    public void setTrueFileName(String trueFileName) {
        this.trueFileName = trueFileName;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getFileUnit() {
        return fileUnit;
    }

    public void setFileUnit(String fileUnit) {
        this.fileUnit = fileUnit;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getShowFileName() {
        return showFileName;
    }

    public void setShowFileName(String showFileName) {
        this.showFileName = showFileName;
    }
}
