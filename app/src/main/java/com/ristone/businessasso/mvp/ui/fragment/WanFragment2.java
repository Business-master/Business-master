package com.ristone.businessasso.mvp.ui.fragment;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ristone.businessasso.R;
import com.ristone.businessasso.mvp.ui.adapter.ViewPageAdapter;
import com.ristone.businessasso.mvp.ui.fragment.base.BaseFragment;

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
