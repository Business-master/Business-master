package com.bus.business;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.bus.business.di.component.ApplicationComponent;
import com.bus.business.di.component.DaggerApplicationComponent;
import com.bus.business.di.module.ApplicationModule;
import com.socks.library.KLog;

import cn.jpush.android.api.JPushInterface;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public class App extends Application{

    private static Context sAppContext;
    private ApplicationComponent mApplicationComponent;
    private static String jpushId;
//   mApplicationComponent private RefWatcher refWatcher;
//
//    public static RefWatcher getRefWatcher(Context context) {
//        App application = (App) context.getApplicationContext();
//        return application.refWatcher;
//    }

    @Override
    public void onCreate() {
        super.onCreate();

        sAppContext = this;
        initActivityLifecycleLogs();
        initApplicationComponent();
        KLog.init(BuildConfig.LOG_DEBUG);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        jpushId = JPushInterface.getRegistrationID(this);
        KLog.a("RegistrationID---->"+ JPushInterface.getRegistrationID(this));
    }


    public static String getJpushId(){
        return jpushId;
    }
//    private void initLeakCanary() {
//        if (BuildConfig.DEBUG) {
//            refWatcher = LeakCanary.install(this);
//        } else {
//            refWatcher = installLeakCanary();
//        }
//    }


    /**
     * release版本使用此方法
     */
//    protected RefWatcher installLeakCanary() {
//        return RefWatcher.DISABLED;
//    }

    private void initActivityLifecycleLogs() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                KLog.v("=========", activity + "  onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                KLog.v("=========", activity + "  onActivityStarted");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                KLog.v("=========", activity + "  onActivityResumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                KLog.v("=========", activity + "  onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                KLog.v("=========", activity + "  onActivityStopped");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                KLog.v("=========", activity + "  onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                KLog.v("=========", activity + "  onActivityDestroyed");
            }
        });
    }

    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static Context getAppContext() {
        return sAppContext;
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
}
