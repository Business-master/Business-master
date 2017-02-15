package com.bus.business.mvp.entity;

import android.text.TextUtils;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/12
 */

public class WeatherBean {

    /**
     * maxW : 6
     * cityno : CN101010100
     * code : 100
     * cityname : 北京
     * dayTxt : 晴
     * dir : 北风
     * sc : 微风
     * ccTitle : 较适宜
     * ccTxt : 较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。
     * pmtwo :
     * times : 2017-01-12
     * qlty :
     * minW : -4
     * nightTxt : 晴
     * id : 6
     * pmten : 53
     * status : ok
     */

    private String maxW;
    private String cityno;
    private int code;
    private String cityname;
    private String dayTxt;
    private String dir;
    private String sc;
    private String ccTitle;
    private String ccTxt;
    private String pmtwo;
    private String times;
    private String qlty;
    private String minW;
    private String nightTxt;
    private int id;
    private String pmten;
    private String status;

    public String getMaxW() {
        return maxW;
    }

    public void setMaxW(String maxW) {
        this.maxW = maxW;
    }

    public String getCityno() {
        return cityno;
    }

    public void setCityno(String cityno) {
        this.cityno = cityno;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getDayTxt() {
        return dayTxt;
    }

    public void setDayTxt(String dayTxt) {
        this.dayTxt = dayTxt;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getCcTitle() {
        return ccTitle;
    }

    public void setCcTitle(String ccTitle) {
        this.ccTitle = ccTitle;
    }

    public String getCcTxt() {
        return ccTxt;
    }

    public void setCcTxt(String ccTxt) {
        this.ccTxt = ccTxt;
    }

    public String getPmtwo() {
        return pmtwo;
    }

    public void setPmtwo(String pmtwo) {
        this.pmtwo = pmtwo;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getQlty() {
        return qlty;
    }

    public void setQlty(String qlty) {
        this.qlty = qlty;
    }

    public String getMinW() {
        return minW;
    }

    public void setMinW(String minW) {
        this.minW = minW;
    }

    public String getNightTxt() {
        return nightTxt;
    }

    public void setNightTxt(String nightTxt) {
        this.nightTxt = nightTxt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPmten() {
        return pmten;
    }

    public void setPmten(String pmten) {
        this.pmten = pmten;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "WeatherBean{" +
                "maxW='" + maxW + '\'' +
                ", cityno='" + cityno + '\'' +
                ", code=" + code +
                ", cityname='" + cityname + '\'' +
                ", dayTxt='" + dayTxt + '\'' +
                ", dir='" + dir + '\'' +
                ", sc='" + sc + '\'' +
                ", ccTitle='" + ccTitle + '\'' +
                ", ccTxt='" + ccTxt + '\'' +
                ", pmtwo='" + pmtwo + '\'' +
                ", times='" + times + '\'' +
                ", qlty='" + qlty + '\'' +
                ", minW='" + minW + '\'' +
                ", nightTxt='" + nightTxt + '\'' +
                ", id=" + id +
                ", pmten='" + pmten + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
