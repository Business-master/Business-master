package com.bus.business.di.component;

import android.app.Activity;
import android.content.Context;

import com.bus.business.di.module.FragmentModule;
import com.bus.business.di.scope.ContextLife;
import com.bus.business.di.scope.PerFragment;
import com.bus.business.mvp.ui.fragment.MainPagerFragment;
import com.bus.business.mvp.ui.fragment.MeetingFragment;
import com.bus.business.mvp.ui.fragment.MineFragment;
import com.bus.business.mvp.ui.fragment.NewMeetingFragment;
import com.bus.business.mvp.ui.fragment.NewsFragment;
import com.bus.business.mvp.ui.fragment.WanFragment;

import dagger.Component;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(MainPagerFragment fragment);
    void inject(NewsFragment fragment);
    void inject(MeetingFragment fragment);
    void inject(NewMeetingFragment newMeetingFragment);

    void inject(WanFragment wanFragment);

    void inject(MineFragment mineFragment);
}