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
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ristone.businessasso.R;
import com.ristone.businessasso.common.Constants;
import com.ristone.businessasso.common.LoadNewsType;
import com.ristone.businessasso.common.NewsType;
import com.ristone.businessasso.mvp.entity.BusinessBean;
import com.ristone.businessasso.mvp.presenter.impl.BusinessPresenterImpl;
import com.ristone.businessasso.mvp.ui.activities.NewDetailActivity;
import com.ristone.businessasso.mvp.ui.adapter.BusinessAdapter;
import com.ristone.businessasso.mvp.ui.fragment.base.BaseLazyFragment;
import com.ristone.businessasso.mvp.view.BusinessView;
import com.ristone.businessasso.utils.CustomUtils;
import com.ristone.businessasso.utils.NetUtil;
import com.ristone.businessasso.widget.RecyclerViewDivider;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/23
 * 首页  商情   碎片
 */
public class
BusinessFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener
        , BusinessView
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseQuickAdapter.OnRecyclerViewItemClickListener{

    public static final String NEW_TYPE = "new_type";
    @BindView(R.id.news_rv)
    RecyclerView mNewsRV;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.empty_view)
    TextView mEmptyView;




    @Inject
    Activity mActivity;

    @Inject
    BusinessPresenterImpl mBusinessPresenter;
    private BaseQuickAdapter mNewsListAdapter;
    private List<BusinessBean> likeBeanList;

    private int pageNum = 1;
    private boolean isXunFrg;//true是讯息页,false是协会页

    public static BusinessFragment getInstance(@NewsType.checker int checker) {
        BusinessFragment businessFragment = new BusinessFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(NEW_TYPE, checker);
        businessFragment.setArguments(bundle);
        return businessFragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void onFirstUserVisible() {
        initViews();
    }

    private void initViews() {
        initIntentData();
        initSwipeRefreshLayout();
        initRecyclerView();
        initPresenter();
    }

    private void initIntentData() {
        isXunFrg = getArguments() == null || getArguments().getInt(NEW_TYPE) == 1;
    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getIntArray(R.array.gplus_colors));
    }

    private void initPresenter() {

            mBusinessPresenter.setNewsTypeAndId(pageNum, Constants.numPerPage, "",0);
            mBusinessPresenter.attachView(this);
            mPresenter = mBusinessPresenter;
            mPresenter.onCreate();

    }

    private void initRecyclerView() {
        mNewsRV.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false);
        mNewsRV.addItemDecoration(new RecyclerViewDivider(mActivity,
                LinearLayoutManager.VERTICAL, 2, getResources().getColor(R.color.gray)));
        mNewsRV.setLayoutManager(mLayoutManager);
        mNewsRV.setItemAnimator(new DefaultItemAnimator());

        likeBeanList = new ArrayList<>();
        mNewsListAdapter = new BusinessAdapter(R.layout.item_news, likeBeanList);
        mNewsListAdapter.setOnLoadMoreListener(this);

        mNewsListAdapter.setOnRecyclerViewItemClickListener(this);
        mNewsListAdapter.openLoadMore(Constants.numPerPage, true);
        mNewsRV.setAdapter(mNewsListAdapter);

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

            mBusinessPresenter.refreshData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
            mBusinessPresenter.onDestory();
    }

    @Override
    public void onLoadMoreRequested() {
            mBusinessPresenter.loadMore();
    }

    private void checkIsEmpty(List<BusinessBean> newsSummary) {
        if (newsSummary == null && mNewsListAdapter.getData() == null) {
            mNewsRV.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mNewsRV.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        goToNewsDetailActivity(view, position);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void goToNewsDetailActivity(View view, int position) {
        Intent intent = setIntent(position);
        startActivity(view, intent);
    }

    @NonNull
    private Intent setIntent(int position) {
        List<BusinessBean> newsSummaryList = mNewsListAdapter.getData();
        Intent intent = new Intent(mActivity, NewDetailActivity.class);
        intent.putExtra(Constants.NEWS_POST_ID, newsSummaryList.get(position).getId() + "");
        intent.putExtra(Constants.NEWS_TYPE, Constants.DETAIL_XIE_TYPE);
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
    public void setBusinessList(List<BusinessBean> newsBean, @LoadNewsType.checker int loadType) {
        switch (loadType) {
            case LoadNewsType.TYPE_REFRESH_SUCCESS:
                mSwipeRefreshLayout.setRefreshing(false);
                mNewsListAdapter.setNewData(newsBean);
                checkIsEmpty(newsBean);
                break;
            case LoadNewsType.TYPE_REFRESH_ERROR:
                mSwipeRefreshLayout.setRefreshing(false);
                checkIsEmpty(newsBean);
                break;
            case LoadNewsType.TYPE_LOAD_MORE_SUCCESS:
                if (newsBean == null){
                    return;
                }
                if (newsBean.size() == Constants.numPerPage){
                    mNewsListAdapter.notifyDataChangedAfterLoadMore(newsBean, true);
                }else {
                    mNewsListAdapter.notifyDataChangedAfterLoadMore(newsBean, false);
                    new CustomUtils(mActivity).showNoMore(mNewsRV);
                }
                break;
            case LoadNewsType.TYPE_LOAD_MORE_ERROR:

                break;
        }
    }
}
