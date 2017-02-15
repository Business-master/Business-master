package com.bus.business.di.module;

import android.app.Activity;
import android.content.Context;

import com.bus.business.di.scope.ContextLife;
import com.bus.business.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/24
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context ProvideActivityContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    public Activity ProvideActivity() {
        return mActivity;
    }
}