package com.bus.business.mvp.entity;

import android.content.Context;
import android.content.Intent;

import com.bus.business.mvp.ui.activities.WebActivity;

import java.io.Serializable;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/11
 */

public class WanBean implements Serializable{

    public static final String WEBURL = "web_url";
    private int imgSrc;
    private String url;

    String title;
    String detail;
    private int icon;
    private int banner;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getBanner() {
        return banner;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void intentToWebAct(Context context){
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(WEBURL,url);
        context.startActivity(intent);
    }

    public int getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(int imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "WanBean{" +
                "imgSrc=" + imgSrc +
                ", url='" + url + '\'' +
                '}';
    }
}
