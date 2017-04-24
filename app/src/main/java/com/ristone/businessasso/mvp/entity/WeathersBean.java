package com.ristone.businessasso.mvp.entity;

import android.text.TextUtils;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/12
 */

public class WeathersBean {

    private WeatherBean weather;
    private String carNoLimit;
    private String type;

    public boolean getType() {
        return TextUtils.isEmpty(type) || type.equals(2);
    }

    public void setType(String type) {
        this.type = type;
    }


    public WeatherBean getWeather() {
        return weather;
    }

    public void setWeather(WeatherBean weather) {
        this.weather = weather;
    }

    public String getCarNoLimit() {
        return carNoLimit;
    }

    public void setCarNoLimit(String carNoLimit) {
        this.carNoLimit = carNoLimit;
    }

    @Override
    public String toString() {
        return "WeathersBean{" +
                "weather=" + weather +
                ", carNoLimit='" + carNoLimit + '\'' +
                '}';
    }
}
