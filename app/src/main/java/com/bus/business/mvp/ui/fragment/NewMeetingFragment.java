package com.bus.business.mvp.ui.fragment;

import android.app.Activity;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.view.View;


import com.bus.business.R;

import com.bus.business.mvp.event.CheckMeetingStateEvent;

import com.bus.business.mvp.ui.adapter.ViewPageAdapter;
import com.bus.business.mvp.ui.fragment.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;



import butterknife.BindView;

/**
 * 新的会务页面 碎片
 */
public class NewMeetingFragment extends BaseFragment  {

    @BindView(R.id.vp_newm)
    ViewPager mViewPager;
    @BindView(R.id.tl_newm)
    TabLayout mTabLayout;
    private List<String> titles= new ArrayList<>();
    private List<Fragment> list = new ArrayList<>();



    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        ininData();
        mViewPager.setAdapter(new ViewPageAdapter(getActivity().getSupportFragmentManager(),titles,list));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void ininData() {
        titles.add("正在召开会议");
        titles.add("即将召开会议");
        titles.add("历史会议");

        MeetingFragment m1 = new MeetingFragment();
        m1.getInstance(1);
        MeetingFragment m0 = new MeetingFragment();
        m0.getInstance(0);
        MeetingFragment m2 = new MeetingFragment();
        m2.getInstance(2);

        list.add(m1);
        list.add(m0);
        list.add(m2);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_newmeeting;
    }









    @Override
    public void onDestroy() {
        super.onDestroy();

    }




}
