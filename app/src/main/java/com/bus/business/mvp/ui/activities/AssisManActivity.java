package com.bus.business.mvp.ui.activities;



import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.common.Constants;
import com.bus.business.common.LoadNewsType;
import com.bus.business.mvp.entity.AssisBean;
import com.bus.business.mvp.entity.response.RspAssisBean;
import com.bus.business.mvp.entity.response.base.BaseNewBean;
import com.bus.business.mvp.event.AddAssisEvent;
import com.bus.business.mvp.presenter.impl.AssisPresenterImpl;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.mvp.ui.adapter.AssisAdapter;
import com.bus.business.mvp.view.AssisView;
import com.bus.business.mvp.view.MeetingView;
import com.bus.business.repository.network.RetrofitManager;
import com.bus.business.utils.NetUtil;
import com.bus.business.utils.TransformUtils;
import com.bus.business.utils.UT;
import com.bus.business.widget.RecyclerViewDivider;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 助手助理页面
 */
public class AssisManActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener
        , AssisView, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnRecyclerViewItemClickListener {

    @Inject
    Activity mActivity;
    @BindView(R.id.rv_assis)
    RecyclerView rv_assis;
    private AssisAdapter assisAdapter;
    private List<AssisBean> assisBeanList;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.empty_view)
    TextView mEmptyView;

    @Inject
    AssisPresenterImpl assisPresenterImpl;
    private int pageNum=1;


    @Override
    public int getLayoutId() {
        return R.layout.activity_assis_man;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        setCustomTitle("助理管理");
        showOrGoneSearchRl(View.GONE);
        initSwipeRefreshLayout();
        initRecyclerView();
        initPresenter();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.gplus_colors));
    }

    private void initPresenter() {
        assisPresenterImpl.setNewsTypeAndId(pageNum, Constants.numPerPage, "",-1);
        mPresenter = assisPresenterImpl;
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }



    private void initRecyclerView() {
        assisBeanList = new ArrayList<>();

        rv_assis.setHasFixedSize(true);
        rv_assis.addItemDecoration(new RecyclerViewDivider(mActivity,
                LinearLayoutManager.VERTICAL, 2, getResources().getColor(R.color.gray)));
        rv_assis.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        rv_assis.setItemAnimator(new DefaultItemAnimator());


        assisAdapter = new AssisAdapter(R.layout.layout_assis_item,assisBeanList);
        assisAdapter.setOnLoadMoreListener(this);
        assisAdapter.setOnRecyclerViewItemClickListener(this);


        rv_assis.setAdapter(assisAdapter);
    }
    private void checkIsEmpty(List<AssisBean> newsSummary) {
        if (newsSummary != null && assisAdapter.getData().size()>0) {
            rv_assis.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        } else {
            rv_assis.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void setAssissList(List<AssisBean> assissList, @LoadNewsType.checker int loadType) {
        switch (loadType) {
            case LoadNewsType.TYPE_REFRESH_SUCCESS:
                mSwipeRefreshLayout.setRefreshing(false);
                assisAdapter.setNewData(assissList);
                checkIsEmpty(assissList);
                break;
            case LoadNewsType.TYPE_REFRESH_ERROR:
                mSwipeRefreshLayout.setRefreshing(false);
                checkIsEmpty(assissList);
                break;
            case LoadNewsType.TYPE_LOAD_MORE_SUCCESS:
                if (assissList == null){
                    return;
                }

                {
                    assisAdapter.notifyDataChangedAfterLoadMore(assissList, false);
                    Snackbar.make(rv_assis, getString(R.string.no_more), Snackbar.LENGTH_SHORT).show();
                }


                break;
            case LoadNewsType.TYPE_LOAD_MORE_ERROR:

                break;
        }
    }

    @OnClick({R.id.assis_btn,R.id.empty_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.assis_btn:
               startActivity(new Intent(mActivity,AddAssisActivity.class));
                break;
            case R.id.empty_view:
//                mEmptyView.setVisibility(View.GONE);
//                assisPresenterImpl.refreshData();
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
//        mEmptyView.setVisibility(View.VISIBLE);
        // 网络不可用状态在此之前已经显示了提示信息
        if (NetUtil.isNetworkAvailable()) {
            Snackbar.make(rv_assis, msg, Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void onEventMainThread(AddAssisEvent event){
        if (event.getAddAssis()==1){
            onRefresh();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        assisPresenterImpl.onDestory();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {
//        mEmptyView.setVisibility(View.GONE);
        assisPresenterImpl.refreshData();
    }

    @Override
    public void onLoadMoreRequested() {
        assisPresenterImpl.loadMore();
    }

    @Override
    public void onItemClick(View view, int i) {
//         UT.show("点击了");

    }


}
