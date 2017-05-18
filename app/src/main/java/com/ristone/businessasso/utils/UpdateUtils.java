package com.ristone.businessasso.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ristone.businessasso.App;
import com.ristone.businessasso.R;
import com.ristone.businessasso.common.ApiConstants;
import com.ristone.businessasso.mvp.entity.VersionBean;
import com.ristone.businessasso.mvp.entity.response.RspVersion;
import com.ristone.businessasso.repository.network.RetrofitManager;
import com.socks.library.KLog;



import rx.Subscriber;

/**
 * Created by ATRSnail on 2017/5/18.
 * 更新App
 */

public class UpdateUtils {
    private static  final String platform="2";//1:IOS  2:ANDROID
    private  static int isUpdate=0;//0最新，1，更新
    private  static String updateType="1";//1，非强制2，强制


    public static void checkUpdate(final Context cotext) {
        RetrofitManager.getInstance(1).getVersionInfo(String.valueOf(getAppVersionCode()),platform)
        .compose(TransformUtils.<RspVersion>defaultSchedulers())
        .subscribe(new Subscriber<RspVersion>() {

            @Override
            public void onCompleted() {
                KLog.d();
            }

            @Override
            public void onError(Throwable e) {
                KLog.e(e.toString());
            }

            @Override
            public void onNext(RspVersion rspVersion) {
                KLog.a("-----------" + rspVersion.toString());
                //0请求成功
                if ("0".equals(rspVersion.getHead().getRspCode())){
                    isUpdate = rspVersion.getBody().getIsUpdate();
                    //1版本更新
                    if (1==isUpdate){
                        VersionBean versionBean = rspVersion.getBody().getLatestVersion();
                        if (versionBean!=null){
                            createDialog(cotext,versionBean);
                        }

                    }
                }
            }
        });
    }

    private static void createDialog(final Context cotext, final VersionBean versionBean) {
        final String wantVersion = versionBean.getVersionCode();
        updateType = versionBean.getUpdateType();
        String introduction = versionBean.getIntroduction();

        View view = LayoutInflater.from(cotext).inflate(R.layout.versionintro,null,false);
        TextView textView = (TextView) view.findViewById(R.id.tv_version);
        if (!TextUtils.isEmpty(introduction))
        textView.setText(introduction);

          new AlertDialog.Builder(cotext)
                  .setView(view)
                  .setCancelable(false)
                  .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       String url = ApiConstants.NETEAST_HOST+ApiConstants.DownVersion+"?wantVersion="+wantVersion+"&platform=2";
                       Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                      cotext.startActivity(intent);
                       dialog.dismiss();
                   }
               })
                  .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //2强制更新,点击确定更新，点击取消退出程序
                        if ("2".equals(updateType)){
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(0);
                        }else {
                            dialog.dismiss();
                        }

                    }
                }).create().show();

    }


    public static int getAppVersionCode(){
        int versionCode = 0;
        try {
            versionCode = App.getAppContext()
                    .getPackageManager()
                    .getPackageInfo(App.getAppContext().getPackageName(),
                            0).versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }
}
