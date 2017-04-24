package com.ristone.businessasso.mvp.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ristone.businessasso.R;
import com.ristone.businessasso.mvp.ui.adapter.ViewPageAdapter;
import com.ristone.businessasso.mvp.ui.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.ristone.businessasso.common.DropsType.TYPE_BANK;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/13
 * 金融专家   碎片
 */

public class ExpertFragment extends BaseFragment{


    @BindView(R.id.vp_drop)
    ViewPager mViewPager;
    @BindView(R.id.tl_drop)
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
        titles.add("银行类");
//        titles.add("非银行类");
//        titles.add("优化专家");

        list.add(DropDownFragment.getInstance(TYPE_BANK));
//        list.add(DropDownFragment.getInstance(TYPE_NO_BANK));
//        list.add(new SubmitLoanFragment());

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_drop;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
