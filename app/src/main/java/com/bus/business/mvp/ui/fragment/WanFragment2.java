package com.bus.business.mvp.ui.fragment;

import android.app.Activity;
import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.mvp.entity.WanBean;
import com.bus.business.mvp.ui.adapter.ViewPageAdapter;
import com.bus.business.mvp.ui.adapter.WanAdapter;
import com.bus.business.mvp.ui.fragment.base.BaseFragment;
import com.bus.business.widget.DividerGridItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 新的万花筒碎片
 */
public class WanFragment2 extends BaseFragment {


    @BindView(R.id.tl_wan)
    TabLayout tabLayout;
    @BindView(R.id.vp_wan)
    ViewPager viewPager;

    private List<String> titles= new ArrayList<>();
    private List<Fragment> list = new ArrayList<>();

    @Inject
    Activity mActivity;



    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        ininData();
        viewPager.setAdapter(new ViewPageAdapter(getActivity().getSupportFragmentManager(),titles,list));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void ininData() {
        titles.add("奇珍异宝");
        titles.add("山珍海味");
        titles.add("翡翠玛瑙");


        list.add(new QzybFragment());
        list.add( SzhwFragment.getIntance(1));
        list.add(SzhwFragment.getIntance(2));

    }


    @Override
    public int getLayoutId() {
        return R.layout.wanfragment2;
    }





}
