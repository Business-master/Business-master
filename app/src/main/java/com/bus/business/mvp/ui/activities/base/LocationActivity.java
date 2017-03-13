package com.bus.business.mvp.ui.activities.base;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.bus.business.App;
import com.bus.business.utils.UT;
import com.socks.library.KLog;

import java.util.List;

/**
 * Created by ATRSnail on 2017/3/13.
 */

public class LocationActivity extends CheckPermissionsActivity {
    private static LocationManager locationManager;
    private static String locationProvider;
    private static Context mContext= App.getAppContext();

    Location location;


    public Location getLocation() {
        String locationStr = "维度：" + location.getLatitude() +"\n"
                + "经度：" + location.getLongitude();
        KLog.a(locationStr);
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */

    private  LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示
            setLocation(location);
        }
    };

    public static LocationActivity getInstance() {
       return new LocationActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //获取地理位置管理器
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            UT.show("没有可用的位置提供器");
            return;
        }

        // 用于进行网络定位 ACCESS_COARSE_LOCATION
        //  用于访问GPS定位 ACCESS_FINE_LOCATION
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        Location location = locationManager.getLastKnownLocation(locationProvider);

        //监视地理位置变化
        locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
        setLocation(location);
        super.onCreate(savedInstanceState);


    }

    //移除监听器
    public    void destroyLocation(Location location) {

      //   用于进行网络定位 ACCESS_COARSE_LOCATION
        //  用于访问GPS定位 ACCESS_FINE_LOCATION
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }






    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {

    }
}
