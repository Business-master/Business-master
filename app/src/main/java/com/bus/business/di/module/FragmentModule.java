package com.bus.business.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.bus.business.di.scope.ContextLife;
import com.bus.business.di.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @PerFragment
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Activity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Fragment provideFragment() {
        return mFragment;
    }
}
