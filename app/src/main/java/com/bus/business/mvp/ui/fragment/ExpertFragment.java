package com.bus.business.mvp.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bus.business.R;
import com.bus.business.mvp.ui.adapter.ViewPageAdapter;
import com.bus.business.mvp.ui.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.bus.business.common.DropsType.TYPE_BANK;
import static com.bus.business.common.DropsType.TYPE_NO_BANK;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/13
 */

public class ExpertFragment extends BaseFragment{


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
        titles.add("银行");
        titles.add("非银行");
        titles.add("历史");

        list.add(DropDownFragment.getInstance(TYPE_BANK));
        list.add(DropDownFragment.getInstance(TYPE_NO_BANK));
        list.add(DropDownFragment.getInstance(TYPE_BANK));

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
