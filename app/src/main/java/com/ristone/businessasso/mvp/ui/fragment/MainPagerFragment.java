package com.ristone.businessasso.mvp.ui.fragment;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ristone.businessasso.R;
import com.ristone.businessasso.common.NewsType;
import com.ristone.businessasso.mvp.ui.adapter.ViewPageAdapter;
import com.ristone.businessasso.mvp.ui.fragment.base.BaseFragment;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 * 注释为以前首页，新的添加了区域选择
 */
public class MainPagerFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

//    private static final String[] TITLE = {"新闻 • 简讯","商情 • 资讯"};
    String customArea="";
    private static final String[] TITLE = {"新闻 • 简讯","商情 • 资讯","基层 • 组织"};

    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.vp_view)
    ViewPager mViewPager;

    private ViewPageAdapter mViewPageAdapter;
    private List<String> mTitles = new ArrayList<String>();
    private List<Fragment> mFragments = new ArrayList<Fragment>();


    @Inject
    Activity mActivity;



    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }



    @Override
    public void initViews(View view) {


        mViewPager.addOnPageChangeListener(this);

        mTitles.add(TITLE[0]);
        mTitles.add(TITLE[1]);
        mTitles.add(TITLE[2]);

        mFragments.add(NewsFragment.getInstance(NewsType.TYPE_REFRESH_XUNXI));
        mFragments.add(BusinessFragment.getInstance(NewsType.TYPE_REFRESH_XIEHUI));
        mFragments.add(OrganizationFragment.getInstance(NewsType.TYPE_REFRESH_AREA));

        mViewPageAdapter = new ViewPageAdapter(getActivity().getSupportFragmentManager(), mTitles, mFragments);


        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(mViewPageAdapter);
        //为TabLayout设置ViewPager
        mTabLayout.setupWithViewPager(mViewPager);
        //使用ViewPager的适配器
        mTabLayout.setTabsFromPagerAdapter(mViewPageAdapter);

    }





    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_page;
    }

//    @OnClick(R.id.img_plus)
//    public void choiceCity(View v){
//      startActivity(new Intent(mActivity, PlaceActivity.class));
//    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        KLog.a("position--->"+position);
//        EventBus.getDefault().post(new ChangeSearchStateEvent(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



}
