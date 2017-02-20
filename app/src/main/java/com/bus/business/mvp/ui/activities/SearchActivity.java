package com.bus.business.mvp.ui.activities;

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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.common.Constants;
import com.bus.business.common.LoadNewsType;
import com.bus.business.common.NewsType;
import com.bus.business.mvp.entity.AreaSeaBean;
import com.bus.business.mvp.entity.MeetingBean;
import com.bus.business.mvp.entity.response.base.BaseNewBean;
import com.bus.business.mvp.event.JoinToMeetingEvent;
import com.bus.business.mvp.presenter.impl.AreaSeaPresenterImpl;
import com.bus.business.mvp.presenter.impl.BusinessPresenterImpl;
import com.bus.business.mvp.presenter.impl.MeetingPresenterImpl;
import com.bus.business.mvp.presenter.impl.NewsPresenterImpl;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.mvp.ui.adapter.AreaAdapter;
import com.bus.business.mvp.ui.adapter.MeetingsAdapter;
import com.bus.business.mvp.ui.adapter.NewsAdapter;
import com.bus.business.mvp.view.AreaSeaView;
import com.bus.business.mvp.view.BusinessView;
import com.bus.business.mvp.view.MeetingView;
import com.bus.business.mvp.view.NewsView;
import com.bus.business.utils.NetUtil;
import com.bus.business.utils.UT;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener
        , NewsView<List<BaseNewBean>>
        , MeetingView
        , BusinessView
        ,AreaSeaView
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseQuickAdapter.OnRecyclerViewItemClickListener
        , TextView.OnEditorActionListener{

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.news_rv)
    RecyclerView mNewsRV;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.search_edit)
    EditText mSearchEdit;

    private int searchIndex;
    private BaseQuickAdapter mNewsListAdapter;
//    private List<BaseNewBean> likeBeanList;
    private List<AreaSeaBean> likeBeanList;
    private List<MeetingBean> meetList;
    private int pageNum = 1;
    private int numPerPage = 20;
    @Inject
    Activity mActivity;
    @Inject
    NewsPresenterImpl mNewsPresenter;
    @Inject
    BusinessPresenterImpl mBusinessPresenter;
    @Inject
    MeetingPresenterImpl mMeetsPresenter;
    @Inject
    AreaSeaPresenterImpl mAreaSeaPresenterImpl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        setCustomTitle("搜索");
        showOrGoneSearchRl(View.GONE);
        searchIndex = getIntent().getIntExtra(NewsType.TYPE_SEARCH, 1);
        initSwipeRefreshLayout();
        initRecyclerView();
        initEdittext();
    }

    private void initEdittext() {
        this.hideProgress();
        switch (searchIndex) {
            case NewsType.TYPE_REFRESH_XUNXI:
//                mSearchEdit.setHint("搜索你所需要的-新闻信息");
//                break;
            case NewsType.TYPE_REFRESH_XIEHUI:
//                mSearchEdit.setHint("搜索你所需要的-商业信息");
                mSearchEdit.setHint("搜索你所需要的-新闻信息");
                break;
            case NewsType.TYPE_REFRESH_HUIWU:
                mSearchEdit.setHint("搜索你所需要的-会议信息");
                break;
        }
        mSearchEdit.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mSearchEdit.setOnEditorActionListener(this);
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(mActivity.getResources().getIntArray(R.array.gplus_colors));
    }

    private void initNewsPresenter() {
        mNewsPresenter.setNewsTypeAndId(pageNum, numPerPage, mSearchEdit.getText().toString().trim(),-1);
        mNewsPresenter.attachView(this);
        mPresenter = mNewsPresenter;
        mPresenter.onCreate();
    }

    private void initBusPresenter() {
        mBusinessPresenter.setNewsTypeAndId(pageNum, numPerPage, mSearchEdit.getText().toString().trim(),-1);
        mBusinessPresenter.attachView(this);
        mPresenter = mBusinessPresenter;
        mPresenter.onCreate();
    }
   private void initAreaSeaPresenter() {
       mAreaSeaPresenterImpl.setNewsTypeAndId(pageNum, numPerPage, mSearchEdit.getText().toString().trim(), "", "");
       mAreaSeaPresenterImpl.attachView(this);
       mPresenter = mAreaSeaPresenterImpl;
       mPresenter.onCreate();
    }

    private void initMeetPresenter() {
        mMeetsPresenter.setNewsTypeAndId(pageNum, numPerPage, mSearchEdit.getText().toString().trim(),-1);
        mMeetsPresenter.attachView(this);
        mPresenter = mMeetsPresenter;
        mPresenter.onCreate();
    }

    private void initRecyclerView() {
        mNewsRV.setHasFixedSize(true);
        mNewsRV.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        mNewsRV.setItemAnimator(new DefaultItemAnimator());
        mNewsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });

        switch (searchIndex) {
            case NewsType.TYPE_REFRESH_XUNXI:
            case NewsType.TYPE_REFRESH_XIEHUI:
                likeBeanList = new ArrayList<>();
//                mNewsListAdapter = new NewsAdapter(R.layout.item_news, likeBeanList);
                mNewsListAdapter = new AreaAdapter(R.layout.item_news, likeBeanList);
                break;
            case NewsType.TYPE_REFRESH_HUIWU:
                meetList = new ArrayList<>();
                mNewsListAdapter = new MeetingsAdapter(R.layout.layout_newmeeting_item2, meetList);
//                mNewsListAdapter = new MeetingsAdapter(R.layout.layout_newmeeting_item, meetList);
//                mNewsListAdapter = new MeetingsAdapter(R.layout.layout_meeting_item, meetList);
                break;
        }

        mNewsListAdapter.setOnLoadMoreListener(this);
        mNewsListAdapter.setOnRecyclerViewItemClickListener(this);
        mNewsRV.setAdapter(mNewsListAdapter);
    }


    @OnClick(R.id.search_cancel)
    public void touchSearch(View v) {
        this.finish();
    }

    @Override
    public void onRefresh() {
        switch (searchIndex) {
            case NewsType.TYPE_REFRESH_XUNXI:
//                mNewsPresenter.refreshData();
//                break;
            case NewsType.TYPE_REFRESH_XIEHUI:
//                mBusinessPresenter.refreshData();
                mAreaSeaPresenterImpl.refreshData();
                break;
            case NewsType.TYPE_REFRESH_HUIWU:
                mMeetsPresenter.refreshData();
                break;
        }
    }

    @Override
    public void setNewsList(List<BaseNewBean> newsBean, @LoadNewsType.checker int loadType) {
//        switch (loadType) {
//            case LoadNewsType.TYPE_REFRESH_SUCCESS:
//                mSwipeRefreshLayout.setRefreshing(false);
//                mNewsListAdapter.setNewData(newsBean);
//                if (mNewsListAdapter.getData().size() == 0 && (newsBean==null||newsBean.size() == 0)){
//                UT.show("暂无数据");
//                return;
//            }
//
//                break;
//            case LoadNewsType.TYPE_REFRESH_ERROR:
//                mSwipeRefreshLayout.setRefreshing(false);
//                break;
//            case LoadNewsType.TYPE_LOAD_MORE_SUCCESS:
//                if (newsBean == null || newsBean.size() == 0) {
//                    Snackbar.make(mNewsRV, getString(R.string.no_more), Snackbar.LENGTH_SHORT).show();
//                } else {
//                    mNewsListAdapter.notifyDataChangedAfterLoadMore(newsBean, true);
//                }
//                break;
//            case LoadNewsType.TYPE_LOAD_MORE_ERROR:
//
//                break;
//        }
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
    public void onItemClick(View view, int i) {
        switch (searchIndex) {
            case NewsType.TYPE_REFRESH_XUNXI:
            case NewsType.TYPE_REFRESH_XIEHUI:
                goToNewsDetailActivity(view,i);
                break;
            case NewsType.TYPE_REFRESH_HUIWU:
                ((MeetingBean)mNewsListAdapter.getData().get(i)).intentToDetail(mActivity,i);
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        switch (searchIndex) {
            case NewsType.TYPE_REFRESH_XUNXI:
//                mNewsPresenter.loadMore();
//                break;
            case NewsType.TYPE_REFRESH_XIEHUI:
//                mBusinessPresenter.loadMore();
                mAreaSeaPresenterImpl.loadMore();
                break;
            case NewsType.TYPE_REFRESH_HUIWU:
                mMeetsPresenter.loadMore();
                break;
        }
    }

    @Override
    public void setMeetingList(List<MeetingBean> newsBean, @LoadNewsType.checker int loadType) {
        switch (loadType) {
            case LoadNewsType.TYPE_REFRESH_SUCCESS:
                mSwipeRefreshLayout.setRefreshing(false);
                KLog.a(newsBean.toString());
                if (mNewsListAdapter.getData().size() == 0 && (newsBean==null||newsBean.size() == 0)){
                    UT.show("暂无数据");
                    return;
                }
                mNewsListAdapter.setNewData(newsBean);
                break;
            case LoadNewsType.TYPE_REFRESH_ERROR:
                mSwipeRefreshLayout.setRefreshing(false);
                break;
            case LoadNewsType.TYPE_LOAD_MORE_SUCCESS:
                if (newsBean == null || newsBean.size() == 0) {
                    Snackbar.make(mNewsRV, getString(R.string.no_more), Snackbar.LENGTH_SHORT).show();
                } else {
                    mNewsListAdapter.notifyDataChangedAfterLoadMore(newsBean, true);
                }
                break;
            case LoadNewsType.TYPE_LOAD_MORE_ERROR:

                break;
        }
    }

    @Override
    public void setBusinessList(List<BaseNewBean> newsBean, @LoadNewsType.checker int loadType) {
//        switch (loadType) {
//            case LoadNewsType.TYPE_REFRESH_SUCCESS:
//                mSwipeRefreshLayout.setRefreshing(false);
//                KLog.a(newsBean.toString());
//                if (mNewsListAdapter.getData().size() == 0 && (newsBean==null||newsBean.size() == 0)){
//                    UT.show("暂无数据");
//                    return;
//                }
//                mNewsListAdapter.setNewData(newsBean);
//                break;
//            case LoadNewsType.TYPE_REFRESH_ERROR:
//                mSwipeRefreshLayout.setRefreshing(false);
//                break;
//            case LoadNewsType.TYPE_LOAD_MORE_SUCCESS:
//                if (newsBean == null || newsBean.size() == 0) {
//                    Snackbar.make(mNewsRV, getString(R.string.no_more), Snackbar.LENGTH_SHORT).show();
//                } else {
//                    mNewsListAdapter.notifyDataChangedAfterLoadMore(newsBean, true);
//                }
//                break;
//            case LoadNewsType.TYPE_LOAD_MORE_ERROR:
//
//                break;
//        }
    }

    @Override
    public void setAreaSeaBeanList(List<AreaSeaBean> areaSeaBeanList, @LoadNewsType.checker int loadType) {
        switch (loadType) {
            case LoadNewsType.TYPE_REFRESH_SUCCESS:
                mSwipeRefreshLayout.setRefreshing(false);
                KLog.a(areaSeaBeanList.toString());
                if (mNewsListAdapter.getData().size() == 0 && (areaSeaBeanList==null||areaSeaBeanList.size() == 0)){
                    UT.show("暂无数据");
                    return;
                }
                mNewsListAdapter.setNewData(areaSeaBeanList);
                break;
            case LoadNewsType.TYPE_REFRESH_ERROR:
                mSwipeRefreshLayout.setRefreshing(false);
                break;
            case LoadNewsType.TYPE_LOAD_MORE_SUCCESS:
                if (areaSeaBeanList == null || areaSeaBeanList.size() == 0) {
                    Snackbar.make(mNewsRV, getString(R.string.no_more), Snackbar.LENGTH_SHORT).show();
                } else {
                    mNewsListAdapter.notifyDataChangedAfterLoadMore(areaSeaBeanList, true);
                }
                break;
            case LoadNewsType.TYPE_LOAD_MORE_ERROR:

                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void goToNewsDetailActivity(View view, int position) {
        Intent intent = setIntent(position);
        startActivity(view, intent);
    }

    @NonNull
    private Intent setIntent(int position) {
//        List<BaseNewBean> newsSummaryList = mNewsListAdapter.getData();
        List<AreaSeaBean> newsSummaryList = mNewsListAdapter.getData();
        Intent intent = new Intent(mActivity, NewDetailActivity.class);
        intent.putExtra(Constants.NEWS_POST_ID, newsSummaryList.get(position).getId()+"");
//        intent.putExtra(Constants.NEWS_TYPE,searchIndex+"");
        String type = newsSummaryList.get(position).getType();
        intent.putExtra(Constants.NEWS_TYPE,Integer.valueOf(type));
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
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId==EditorInfo.IME_ACTION_SEARCH ||(keyEvent!=null&&keyEvent.getKeyCode()== KeyEvent.KEYCODE_ENTER))
        {
           //TODO do something;
            switch (searchIndex) {
                case NewsType.TYPE_REFRESH_XUNXI:
//                    initNewsPresenter();
//                    break;
                case NewsType.TYPE_REFRESH_XIEHUI:
//                    initBusPresenter();
                    initAreaSeaPresenter();
                    break;
                case NewsType.TYPE_REFRESH_HUIWU:
                    initMeetPresenter();
                    break;
            }
            return true;
        }
        return false;

    }

    @Subscribe
    public void onEventMainThread(JoinToMeetingEvent event) {
//        ((MeetingBean)mNewsListAdapter.getData().get(event.getPos())).setJoinType(true);
        mNewsListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


}
