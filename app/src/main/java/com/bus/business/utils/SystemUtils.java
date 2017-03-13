package com.bus.business.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.util.Log;

import com.bus.business.common.Constants;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/2/15
 */

public class SystemUtils {
    /**
     * 判断应用是否已经启动
     * @param context 一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }

    /**
     * 判断是否安装了某应用
     * @param packageName
     * @return
     */
    public static boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 打开百度地图
     * 协议规范 intent://product/[service/]action[?parameters]#Intent;scheme=bdapp;package=package;end
     //parameters功能参数定义，具体规范见功能协议说明
     * 文档地址：http://developer.baidu.com/map/uri-introandroid.htm
     * 现在使用的是高德地图是经纬度数据 火星坐标：gcj02
     * 百度地图的经纬度类型：默认为bd09经纬度坐标。允许的值为bd09ll、bd09mc、gcj02、wgs84。bd09ll表示百度经纬度坐标，gcj02表示经过国测局加密的坐标，wgs84表示gps获取的坐标。
     */
    public static void openBaiduMap(Activity activity,String title){

        if(isInstallByread(Constants.BAIDUMAP_PACKAGENAME)){
            Intent intent = new Intent();
             // 地址解析
            intent.setData(Uri.parse("baidumap://map/geocoder?src=openApiDemo&address="+title));
                    activity.startActivity(intent); //启动调用
            Log.e("GasStation", "百度地图客户端已经安装") ;
        }else{
            UT.show("没有安装百度地图客户端");
            Log.e("GasStation", "没有安装百度地图客户端") ;
        }
    }


    public static void openBaiduMap(String name,Activity activity){

        if(isInstallByread(Constants.BAIDUMAP_PACKAGENAME)){
            Intent intent = new Intent();

            intent.setData(Uri.parse("baidumap://map/direction?region=北京"+
                    "&destination=name:"+name+"&mode=driving"));
            activity.startActivity(intent); //启动调用
            Log.e("GasStation", "百度地图客户端已经安装") ;
        }else{
            UT.show("没有安装百度地图客户端");
            Log.e("GasStation", "没有安装百度地图客户端") ;
        }
    }






    /**
     * 打开高德地图
     * @param activity
     * @param name 目的地名称
     */
    public static void openGaoDeMap(Activity activity, String name)
    {

        Address address=  getJWD(activity,name);
        if (address==null){
            UT.show("查询出错！");
            return;
        }

        try {
            if(isInstallByread(Constants.AMAP_PACKAGENAME)) {
//                Intent intent = Intent.getIntent("androidamap://keywordNavi?sourceApplication=工商联;keyword="+name);
//                activity.startActivity(intent);
//                Intent intent = Intent.getIntent("androidamap://viewMap?sourceApplication=工商联&poiname="+name);
                Intent intent = Intent.getIntent("androidamap://viewMap?sourceApplication=工商联" +
                            "&lat="+address.getLatitude()+"&lon="+address.getLongitude() + "&poiname="+name);
                activity.startActivity(intent);
            }else {
                UT.show("没有安装高德地图客户端");
                Log.e("GasStation", "没有安装高德地图客户端") ;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    private static Address getJWD(Activity activity, String name) {
        Geocoder  geo = new Geocoder(activity, Locale.CHINA);
        List<Address> addressList=null;
        try {
            addressList = geo.getFromLocationName(name,1);
            if (addressList==null||addressList.size()==0){
                return null;
            }else {
                Address  address = addressList.get(0);
                String str = "纬度："+ address.getLatitude()+"经度："+address.getLongitude();
                KLog.a(str);
                return    address;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 打开腾讯地图
     * @param activity
     * @param name 目的地名称
     */
    public static void openTencentMap(Activity activity, String name)
    {
        Address address=  getJWD(activity,name);
        if (address==null){
            UT.show("查询出错！");
            return;
        }

            if(isInstallByread(Constants.TencentMAP_PACKAGENAME)) {
                Intent intent = new Intent();
                intent.setData(Uri.parse("qqmap://map/routeplan?type=drive&fromcoord=CurrentLocation" +
                        "&to=" +name+ "&tocoord="+address.getLatitude()+","+address.getLongitude()+"&referer=工商联"));
                activity.startActivity(intent);
            }else {
                UT.show("没有安装腾讯地图客户端");
                Log.e("GasStation", "没有安装腾讯地图客户端") ;
            }

    }

}
