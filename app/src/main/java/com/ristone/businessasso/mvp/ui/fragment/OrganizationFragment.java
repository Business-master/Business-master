package com.ristone.businessasso.mvp.ui.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ristone.businessasso.R;
import com.ristone.businessasso.common.Constants;
import com.ristone.businessasso.common.LoadNewsType;
import com.ristone.businessasso.common.NewsType;
import com.ristone.businessasso.mvp.entity.AreaSeaBean;
import com.ristone.businessasso.mvp.event.AreaCodeEvent;
import com.ristone.businessasso.mvp.presenter.impl.AreaSeaPresenterImpl;
import com.ristone.businessasso.mvp.presenter.impl.BusinessPresenterImpl;
import com.ristone.businessasso.mvp.presenter.impl.NewsPresenterImpl;
import com.ristone.businessasso.mvp.ui.activities.AreaActivity;
import com.ristone.businessasso.mvp.ui.activities.NewDetailActivity;
import com.ristone.businessasso.mvp.ui.adapter.AreaAdapter;
import com.ristone.businessasso.mvp.ui.fragment.base.BaseLazyFragment;
import com.ristone.businessasso.mvp.view.AreaSeaView;
import com.ristone.businessasso.utils.CustomUtils;
import com.ristone.businessasso.utils.NetUtil;
import com.ristone.businessasso.utils.VideoVisibleUtils;
import com.ristone.businessasso.widget.RecyclerViewDivider;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.VideoEvent;

/**
 * 首页  基层-组织   碎片
 */


    public class OrganizationFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener
        ,AreaSeaView
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseQuickAdapter.OnRecyclerViewItemClickListener {

    public static final String NEW_TYPE = "new_type";
    @BindView(R.id.news_rv)
    RecyclerView mNewsRV;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.empty_view)
    TextView mEmptyView;

    private View areaView;//区域
    private TextView area_tv;//区域选择按钮
    private TextView area_show;//区域展示按钮


    @Inject
    Activity mActivity;

    @Inject
    AreaSeaPresenterImpl mAreaSeaPresenterImpl;

    private BaseQuickAdapter mAreaAdapter;
    private List<AreaSeaBean>  areaSeaBeanList;
    boolean flag = false;//是否为视频新闻
    VideoVisibleUtils videoVisibleUtils;
    private int pageNum = 1;
    private int  isXunFrg;//1是讯息页,2是协会页,4区域选择

    private  String code="";
    private String chamCode="";
    String area="所有地区";
    public static OrganizationFragment getInstance(@NewsType.checker int checker) {
        OrganizationFragment newsFragment = new OrganizationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(NEW_TYPE, checker);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onFirstUserVisible() {
        initViews();
    }

    private void initViews() {
        initIntentData();
        areaView = View.inflate(mActivity, R.layout.activity_area, null);
        area_tv = (TextView) areaView.findViewById(R.id.area_ch);
        area_show = (TextView) areaView.findViewById(R.id.area_show);
        area_show.setText(area);

        area_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, AreaActivity.class));
            }
        });
        initSwipeRefreshLayout();
        initRecyclerView();
        initPresenter(code, chamCode);
        videoVisibleUtils =    new VideoVisibleUtils(mActivity);
        videoVisibleUtils.judgeVisible(mNewsRV);
    }

    @Subscribe
    public void onEventMainThread(AreaCodeEvent event){


        if (event.getArea()){
            code = event.getAreaBean().getCode();
            chamCode="";
        }else {
            chamCode = event.getAreaBean().getCode();
            code="";
        }
         area= event.getAreaBean().getName();
        KLog.a("选择的区域***************"+area);
        area_show.setText(area);

        initPresenter(code,chamCode);
    }








    private void initIntentData() {
        if (getArguments() == null){
            isXunFrg=4;
        }else {
            isXunFrg=getArguments().getInt(NEW_TYPE);
        }

    }




    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getIntArray(R.array.gplus_colors));
    }

    private void initPresenter(String code, String chamCode) {

        if (isXunFrg==4){
            mAreaSeaPresenterImpl.setNewsTypeAndId(pageNum, Constants.numPerPage, "", code, chamCode);
            mAreaSeaPresenterImpl.attachView(this);
            mPresenter = mAreaSeaPresenterImpl;
            mPresenter.onCreate();
        }

    }

    private void initRecyclerView() {
        mNewsRV.setHasFixedSize(true);
        mNewsRV.addItemDecoration(new RecyclerViewDivider(mActivity,
                LinearLayoutManager.VERTICAL, 2, getResources().getColor(R.color.red)));
        mNewsRV.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        mNewsRV.setItemAnimator(new DefaultItemAnimator());


        areaSeaBeanList = new ArrayList<>();
        //区域适配器
        if (isXunFrg==4){
            mAreaAdapter = new AreaAdapter(R.layout.layout_new, areaSeaBeanList);
            mAreaAdapter.addHeaderView(areaView);
            mAreaAdapter.setOnLoadMoreListener(this);
            mAreaAdapter.setOnRecyclerViewItemClickListener(this);
            mAreaAdapter.openLoadMore(Constants.numPerPage, true);
            mNewsRV.setAdapter(mAreaAdapter);
        }




    }



    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMsg(String msg) {
        mProgressBar.setVisibility(View.GONE);
        // 网络不可用状态在此之前已经显示了提示信息
        if (NetUtil.isNetworkAvailable()) {
            Snackbar.make(mNewsRV, msg, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRefresh() {

        if (isXunFrg==4){
            mAreaSeaPresenterImpl.refreshData();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (isXunFrg==4){
            EventBus.getDefault().unregister(this);
            mAreaSeaPresenterImpl.onDestory();
        }

    }

    @Override
    public void onLoadMoreRequested() {

        if (isXunFrg==4) {
            mAreaSeaPresenterImpl.loadMore();
        }

    }


    private void checkEmpty(List<AreaSeaBean> newsSummary) {
        if (newsSummary != null && mAreaAdapter.getData().size()>0) {
            mNewsRV.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
            mEmptyView.setText("暂无数据");
        }
    }

    @Subscribe
    public void onVideoEventMainThread(final VideoEvent event){
        videoVisibleUtils.setPostion(event.getPosition());
    }

    @Override
    public void onItemClick(View view, int position) {
        List<AreaSeaBean>  list = mAreaAdapter.getData();
        if (list!=null&&list.size()>0){
            AreaSeaBean areaSeaBean=  list.get(position);
            if (areaSeaBean!=null&&!TextUtils.isEmpty(areaSeaBean.getType())){
                if (Constants.video_new.equals(areaSeaBean.getType())){
                    flag = true;
                }else {
                    flag = false;
                }
            }
        }



        if(!flag){
            goToNewsDetailActivity(view, position);
        }


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void goToNewsDetailActivity(View view, int position) {
        Intent intent = setIntent(position);
        startActivity(view, intent);
    }

    @NonNull
    private Intent setIntent(int position) {
        Intent intent = new Intent(mActivity, NewDetailActivity.class);

        if (isXunFrg==4){
            List<AreaSeaBean> newsSummaryList = mAreaAdapter.getData();
            intent.putExtra(Constants.NEWS_POST_ID, newsSummaryList.get(position).getId() + "");
            String type = newsSummaryList.get(position).getType();
            intent.putExtra(Constants.NEWS_TYPE, Integer.valueOf(type));
        }


        return intent;
    }

    private void startActivity(View view, Intent intent) {
        ImageView newsSummaryPhotoIv = (ImageView) view.findViewById(R.id.daimajia_slider_image);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(mActivity, newsSummaryPhotoIv, Constants.TRANSITION_ANIMATION_NEWS_PHOTOS);
            startActivity(intent, options.toBundle());
        } else {

            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity(mActivity, intent, options.toBundle());
        }
    }



    @Override
    public void setAreaSeaBeanList(List<AreaSeaBean> areaSeaBeanList, @LoadNewsType.checker int loadType) {
        switch (loadType) {
            case LoadNewsType.TYPE_REFRESH_SUCCESS:
                mSwipeRefreshLayout.setRefreshing(false);
                mAreaAdapter.setNewData(areaSeaBeanList);
                checkEmpty(areaSeaBeanList);
                break;
            case LoadNewsType.TYPE_REFRESH_ERROR:
                mSwipeRefreshLayout.setRefreshing(false);
                checkEmpty(areaSeaBeanList);
                break;
            case LoadNewsType.TYPE_LOAD_MORE_SUCCESS:
                if (areaSeaBeanList == null){
                    return;
                }
                if (areaSeaBeanList.size() == Constants.numPerPage){
                    mAreaAdapter.notifyDataChangedAfterLoadMore(areaSeaBeanList, true);
                }else {
                    mAreaAdapter.notifyDataChangedAfterLoadMore(areaSeaBeanList, false);
                    new CustomUtils(mActivity).showNoMore(mNewsRV);
                }
                break;
            case LoadNewsType.TYPE_LOAD_MORE_ERROR:

                break;
        }
    }

}
