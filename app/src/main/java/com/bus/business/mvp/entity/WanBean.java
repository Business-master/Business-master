package com.bus.business.mvp.entity;

import android.content.Context;
import android.content.Intent;

import com.bus.business.mvp.ui.activities.WebActivity;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/11
 */

public class WanBean {

    public static final String WEBURL = "web_url";
    private int imgSrc;
    private String url;

    String title;
    String detail;

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
