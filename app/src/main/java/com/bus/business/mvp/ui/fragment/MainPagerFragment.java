package com.bus.business.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bus.business.R;
import com.bus.business.common.LoadNewsType;
import com.bus.business.common.NewsType;
import com.bus.business.mvp.entity.AreaBean;
import com.bus.business.mvp.entity.response.RspAreaBean;
import com.bus.business.mvp.event.AreaCodeEvent;
import com.bus.business.mvp.event.ChangeSearchStateEvent;
import com.bus.business.mvp.presenter.impl.AreaPresentetImpl;
import com.bus.business.mvp.ui.activities.PlaceActivity;
import com.bus.business.mvp.ui.adapter.ViewPageAdapter;
import com.bus.business.mvp.ui.fragment.base.BaseFragment;
import com.bus.business.mvp.view.AreaView;
import com.bus.business.repository.network.RetrofitManager;
import com.bus.business.utils.TransformUtils;
import com.bus.business.utils.UT;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

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
        EventBus.getDefault().register(this);


        mViewPager.addOnPageChangeListener(this);

        mTitles.add(TITLE[0]);
        mTitles.add(TITLE[1]);
        mTitles.add(TITLE[2]);

        mFragments.add(NewsFragment.getInstance(NewsType.TYPE_REFRESH_XUNXI));
        mFragments.add(NewsFragment.getInstance(NewsType.TYPE_REFRESH_XIEHUI));
        mFragments.add(NewsFragment_new.getInstance(NewsType.TYPE_REFRESH_AREA));

        mViewPageAdapter = new ViewPageAdapter(getActivity().getSupportFragmentManager(), mTitles, mFragments);


        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(mViewPageAdapter);
        //为TabLayout设置ViewPager
        mTabLayout.setupWithViewPager(mViewPager);
        //使用ViewPager的适配器
        mTabLayout.setTabsFromPagerAdapter(mViewPageAdapter);

    }


//    private void initTitles() {
//        RetrofitManager.getInstance(1).getAreaListObservable()
//                .compose(TransformUtils.<RspAreaBean>defaultSchedulers())
//                .subscribe(new Subscriber<RspAreaBean>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(RspAreaBean rspAreaBean) {
//                        AreaBean areaBean =  rspAreaBean.getBody().getAreaList().get(0);
//                        String str =areaBean.getName();
////                        int i = str.indexOf("工商联");
////                        customArea=str.substring(0,i);
//                        mTitles.add(TITLE[0]);
//                        mTitles.add(TITLE[1]);
//                        mTitles.add(str);
//
//
//                        mViewPageAdapter = new ViewPageAdapter(getActivity().getSupportFragmentManager(),mTitles,mFragments);
//
//                        mViewPager.setAdapter(mViewPageAdapter);
//                        //为TabLayout设置ViewPager
//                        mTabLayout.setupWithViewPager(mViewPager);
//                        //使用ViewPager的适配器
//                        mTabLayout.setTabsFromPagerAdapter(mViewPageAdapter);
//
//                    }
//                });
//    }

    @Subscribe
    public void onEventMainThread(AreaCodeEvent event){
//        if (event.getArea()){
//            AreaBean areaBean= event.getAreaBean();
//            String string = areaBean.getName();
//            int i = string.indexOf("工商联");
//            mTabLayout.getTabAt(2).setText(string.substring(0,i));
//        }else {
//            mTabLayout.getTabAt(2).setText(event.getAreaBean().getName());
//        }
//        mTabLayout.getTabAt(2).setText(event.getAreaBean().getName());

    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
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
        EventBus.getDefault().post(new ChangeSearchStateEvent(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



}
