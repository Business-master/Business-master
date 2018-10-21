package com.ristone.businessasso.mvp.ui.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ristone.businessasso.App;
import com.ristone.businessasso.R;
import com.ristone.businessasso.common.ApiConstants;
import com.ristone.businessasso.common.Constants;
import com.ristone.businessasso.common.LoadNewsType;
import com.ristone.businessasso.mvp.entity.TopicBean;

import com.ristone.businessasso.mvp.presenter.impl.TopicsPresenterImpl;
import com.ristone.businessasso.mvp.ui.activities.base.BaseActivity;
import com.ristone.businessasso.mvp.ui.adapter.TopicsAdapter;
import com.ristone.businessasso.mvp.view.NewsView;
import com.ristone.businessasso.utils.NetUtil;
import com.ristone.businessasso.widget.RecyclerViewDivider;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 专题列表 页面
 */
public class TopicListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener
        , NewsView<List<TopicBean>>
        , BaseQuickAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.news_rv)
    RecyclerView mNewsRV;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.empty_view)
    TextView mEmptyView;
    private TextView mTv;
    private ImageView mActImg;
    private ImageView mTopicImg;

    @Inject
    Activity mActivity;
    @Inject
    TopicsPresenterImpl mNewsPresenter;

    private View mTitleHeader;
    private TopicsAdapter mTopicsAdapter;
    private List<TopicBean> likeBeanList;

    private int pageNum = 1;
    private String newsId;
    private String imgUrl;


    @Override
    public int getLayoutId() {
        return R.layout.activity_topic_list;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {

        setCustomTitle("专题列表");
        showOrGoneSearchRl(View.GONE);

        initIntent();
        initHeadView();
        initSwipeRefreshLayout();
        initRecyclerView();
        initPresenter();
    }

    private void initIntent() {
        newsId = getIntent().getStringExtra(Constants.NEWS_POST_ID);
        imgUrl = getIntent().getStringExtra(Constants.NEWS_IMG);
    }

    private void initHeadView() {
        KLog.a("url---->"+imgUrl);
        mTitleHeader = View.inflate(mActivity, R.layout.layout_head_text, null);
        mActImg = (ImageView) mTitleHeader.findViewById(R.id.img_act);
        mTopicImg = (ImageView) mTitleHeader.findViewById(R.id.img_topic);
        mTv = (TextView) mTitleHeader.findViewById(R.id.tv_title);
        mActImg.setVisibility(View.GONE);
        mTopicImg.setVisibility(View.VISIBLE);
        mTv.setText("最新专题");
        Glide.with(App.getAppContext()).load(ApiConstants.NETEAST_HOST +imgUrl).asBitmap() // gif格式有时会导致整体图片不显示，貌似有冲突
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.image_place_holder)
                .error(R.drawable.ic_load_fail)
                .into(mTopicImg);
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.gplus_colors));
    }

    private void initRecyclerView() {
        mNewsRV.setHasFixedSize(true);
        mNewsRV.addItemDecoration(new RecyclerViewDivider(mActivity,
                LinearLayoutManager.VERTICAL, 2, getResources().getColor(R.color.gray)));
        mNewsRV.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        mNewsRV.setItemAnimator(new DefaultItemAnimator());

        likeBeanList = new ArrayList<>();
        mTopicsAdapter = new TopicsAdapter(R.layout.item_news, likeBeanList);
        mTopicsAdapter.setOnLoadMoreListener(this);

        mTopicsAdapter.addHeaderView(mTitleHeader);

        mTopicsAdapter.setOnRecyclerViewItemClickListener(this);
        mTopicsAdapter.openLoadMore(Constants.numPerPage, true);
        mNewsRV.setAdapter(mTopicsAdapter);

    }

    private void initPresenter() {
        mNewsPresenter.setNewsTypeAndId(pageNum, Constants.numPerPage, newsId);
        mNewsPresenter.attachView(this);
        mPresenter = mNewsPresenter;
        mPresenter.onCreate();

    }

    @Override
    public void setNewsList(List<TopicBean> newsBean, @LoadNewsType.checker int loadType) {
        switch (loadType) {
            case LoadNewsType.TYPE_REFRESH_SUCCESS:
                mSwipeRefreshLayout.setRefreshing(false);
                mTopicsAdapter.setNewData(newsBean);
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
                    mTopicsAdapter.notifyDataChangedAfterLoadMore(newsBean, true);
                }else {
                    mTopicsAdapter.notifyDataChangedAfterLoadMore(newsBean, false);
                    Snackbar.make(mNewsRV, getString(R.string.no_more), Snackbar.LENGTH_SHORT).show();
                }
                break;
            case LoadNewsType.TYPE_LOAD_MORE_ERROR:

                break;
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
        mNewsPresenter.refreshData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mNewsPresenter.onDestory();
    }

    @Override
    public void onLoadMoreRequested() {
        mNewsPresenter.loadMore();
    }

    private void checkIsEmpty(List<TopicBean> newsSummary) {
        if (newsSummary == null && mTopicsAdapter.getData() == null) {
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
        List<TopicBean> newsSummaryList = mTopicsAdapter.getData();
        Intent intent = new Intent(mActivity, NewDetailActivity.class);
        intent.putExtra(Constants.NEWS_POST_ID, newsSummaryList.get(position).getId() + "");
        intent.putExtra(Constants.NEWS_TYPE, Constants.DETAIL_TOP_TYPE);
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

}
