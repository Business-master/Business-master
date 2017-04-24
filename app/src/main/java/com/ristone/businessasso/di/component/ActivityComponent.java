package com.ristone.businessasso.di.component;

import android.app.Activity;
import android.content.Context;

import com.ristone.businessasso.di.module.ActivityModule;
import com.ristone.businessasso.di.scope.ContextLife;
import com.ristone.businessasso.di.scope.PerActivity;
import com.ristone.businessasso.mvp.ui.activities.AddressListActivity;
import com.ristone.businessasso.mvp.ui.activities.ApplyActivity;
import com.ristone.businessasso.mvp.ui.activities.AreaActivity;
import com.ristone.businessasso.mvp.ui.activities.AssisManActivity;
import com.ristone.businessasso.mvp.ui.activities.ManagerActivity;
import com.ristone.businessasso.mvp.ui.activities.MeetingDetailActivity;
import com.ristone.businessasso.mvp.ui.activities.NewDetailActivity;
import com.ristone.businessasso.mvp.ui.activities.NewsManActivity;
import com.ristone.businessasso.mvp.ui.activities.PlaceActivity;
import com.ristone.businessasso.mvp.ui.activities.QzybActivity;
import com.ristone.businessasso.mvp.ui.activities.SearchActivity;

import com.ristone.businessasso.mvp.ui.activities.TopicListActivity;

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
    void inject(AreaActivity areaActivity);
    void inject(TopicListActivity topicListActivity);

    void inject(QzybActivity qzybActivity);
}
