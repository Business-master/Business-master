package com.bus.business.di.component;

import android.app.Activity;
import android.content.Context;

import com.bus.business.di.module.ActivityModule;
import com.bus.business.di.scope.ContextLife;
import com.bus.business.di.scope.PerActivity;
import com.bus.business.mvp.ui.activities.AddressListActivity;
import com.bus.business.mvp.ui.activities.ApplyActivity;
import com.bus.business.mvp.ui.activities.AssisManActivity;
import com.bus.business.mvp.ui.activities.ManagerActivity;
import com.bus.business.mvp.ui.activities.MeetingDetailActivity;
import com.bus.business.mvp.ui.activities.NewDetailActivity;
import com.bus.business.mvp.ui.activities.NewsManActivity;
import com.bus.business.mvp.ui.activities.PlaceActivity;
import com.bus.business.mvp.ui.activities.SearchActivity;

import com.bus.business.mvp.ui.activities.TopicListActivity;

import dagger.Component;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/24
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(NewDetailActivity newsDetailActivity);
    void inject(PlaceActivity newsDetailActivity);
    void inject(SearchActivity newsDetailActivity);
    void inject(MeetingDetailActivity newsDetailActivity);

    void inject(ManagerActivity managerActivity);
    void inject(AssisManActivity assisManActivity);
    void inject(NewsManActivity newsManActivity);
    void inject(ApplyActivity applyActivity);

    void inject(AddressListActivity addressListActivity);
    void inject(TopicListActivity topicListActivity);
}
