package com.ristone.businessasso.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.socks.library.KLog;

/**
 * Created by ATRSnail on 2017/6/1.
 */

public class MarkUtils {

    private MarkUtils() {
    }

    private static MarkUtils markUtils = null;
    private static Context mContext;
    boolean isSupportedBade = false;

    public static MarkUtils getInstance(Context context) {
        mContext = context;
        if (markUtils == null) {
            synchronized (MarkUtils.class) {
                if (markUtils == null) {
                    markUtils = new MarkUtils();
                }
            }
        }
        return markUtils;
    }


    public void setMark(int num) {
        checkIsSupportedByVersion();
        KLog.a("添加角标-------------"+isSupportedBade+"数量-------"+num);
        if (isSupportedBade) {
            setBadgeNum(num);
        }
    }


    public void checkIsSupportedByVersion() {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    "com.huawei.android.launcher", 0);
            if (info.versionCode >= 63029) {
                isSupportedBade = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBadgeNum(int num) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("package",mContext.getPackageName());
            bundle.putString("class", "com.ristone.businessasso.mvp.ui.activities.SplashActivity");
            bundle.putInt("badgenumber", num);
            mContext.getContentResolver().call(
                    Uri.parse("content://com.huawei.android.launcher.settings/badge/"),
                    "change_badge", null, bundle);
        }catch (Exception e){
            isSupportedBade = false;
            e.printStackTrace();
        }
    }


}
