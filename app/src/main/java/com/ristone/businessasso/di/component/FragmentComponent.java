package com.ristone.businessasso.di.component;

import android.app.Activity;
import android.content.Context;

import com.ristone.businessasso.di.module.FragmentModule;
import com.ristone.businessasso.di.scope.ContextLife;
import com.ristone.businessasso.di.scope.PerFragment;
import com.ristone.businessasso.mvp.ui.fragment.BusinessFragment;
import com.ristone.businessasso.mvp.ui.fragment.DropDownFragment;
import com.ristone.businessasso.mvp.ui.fragment.ExpertFragment;
import com.ristone.businessasso.mvp.ui.fragment.MainPagerFragment;
import com.ristone.businessasso.mvp.ui.fragment.MeetingFragment;
import com.ristone.businessasso.mvp.ui.fragment.MineFragment;
import com.ristone.businessasso.mvp.ui.fragment.NewMeetingFragment;
import com.ristone.businessasso.mvp.ui.fragment.NewsFragment;
import com.ristone.businessasso.mvp.ui.fragment.OrganizationFragment;
import com.ristone.businessasso.mvp.ui.fragment.PolicyFragment;
import com.ristone.businessasso.mvp.ui.fragment.QzybFragment;
import com.ristone.businessasso.mvp.ui.fragment.SzhwFragment;
import com.ristone.businessasso.mvp.ui.fragment.WanFragment;
import com.ristone.businessasso.mvp.ui.fragment.WanFragment2;

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
    void inject(BusinessFragment fragment);
    void inject(MeetingFragment fragment);
    void inject(NewMeetingFragment newMeetingFragment);

    void inject(WanFragment wanFragment);

    void inject(MineFragment mineFragment);

    void inject(OrganizationFragment organizationFragment);

    void inject(WanFragment2 wanFragment2);

    void inject(QzybFragment qzybFragment);

    void inject(SzhwFragment szhwFragment);

    void inject(ExpertFragment expertFragment);

    void inject(DropDownFragment dropDownFragment);

    void inject(PolicyFragment policyFragment);

    void inject(NewsFragment newsFragment_);
}