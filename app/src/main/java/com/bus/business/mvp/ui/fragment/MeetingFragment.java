package com.bus.business.mvp.ui.fragment;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.common.Constants;
import com.bus.business.common.LoadNewsType;
import com.bus.business.common.UsrMgr;
import com.bus.business.listener.JoinMeetingCallBack;
import com.bus.business.mvp.entity.MeetingBean;
import com.bus.business.mvp.entity.response.base.BaseRspObj;
import com.bus.business.mvp.event.CheckMeetingStateEvent;
import com.bus.business.mvp.event.JoinToMeetingEvent;
import com.bus.business.mvp.event.ReadMeeting;
import com.bus.business.mvp.presenter.impl.MeetingPresenterImpl;
import com.bus.business.mvp.ui.adapter.MeetingsAdapter;
import com.bus.business.mvp.ui.fragment.base.BaseFragment;
import com.bus.business.mvp.view.MeetingView;
import com.bus.business.repository.network.RetrofitManager;
import com.bus.business.utils.CustomUtils;
import com.bus.business.utils.NetUtil;
import com.bus.business.utils.TransformUtils;
import com.bus.business.widget.RecyclerViewDivider;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Subscriber;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/23
 */
public class MeetingFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener
        , MeetingView, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnRecyclerViewItemClickListener,JoinMeetingCallBack {

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
    MeetingPresenterImpl mNewsPresenter;
//    private BaseQuickAdapter<MeetingBean> mNewsListAdapter;
    private MeetingsAdapter mNewsListAdapter;
    private List<MeetingBean> likeBeanList;
    private MeetingBean meetingBean;







    private int pageNum = 1;

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }
    private  int index;

    public   void getInstance(int index){
     this.index = index;
    }

    @Override
    public void initViews(View view) {
        EventBus.getDefault().register(this);
        initSwipeRefreshLayout();
        initRecyclerView();
        initPresenter();

    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getIntArray(R.array.gplus_colors)
        );
    }

    private void initPresenter() {
        KLog.a("userInfo--->" + UsrMgr.getUseInfo().toString());
        mNewsPresenter.setNewsTypeAndId(pageNum, Constants.numPerPage, "",index);
        mPresenter = mNewsPresenter;
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }

    private void initRecyclerView() {
        mNewsRV.setHasFixedSize(true);
        mNewsRV.addItemDecoration(new RecyclerViewDivider(mActivity,
                LinearLayoutManager.VERTICAL, 2, getResources().getColor(R.color.gray)));
        mNewsRV.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        mNewsRV.setItemAnimator(new DefaultItemAnimator());
        likeBeanList = new ArrayList<>();

//        mNewsListAdapter = new MeetingsAdapter(R.layout.layout_newmeeting_item, likeBeanList);
        mNewsListAdapter = new MeetingsAdapter(R.layout.layout_newmeeting_item3, likeBeanList);
        mNewsListAdapter.setOnLoadMoreListener(this);
        mNewsListAdapter.openLoadMore(Constants.numPerPage, true);
        mNewsListAdapter.setOnRecyclerViewItemClickListener(this);

        mNewsListAdapter.setJoinMeetingCallBack(this);//给设置接口赋值
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

    private void checkIsEmpty(List<MeetingBean> newsSummary) {
        if (newsSummary != null && mNewsListAdapter.getData().size()>0) {
            mNewsRV.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        } else {
            mNewsRV.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
            mEmptyView.setText("暂无数据");
        }
    }

    @Override
    public void setMeetingList(final List<MeetingBean> newsBean, @LoadNewsType.checker int loadType) {

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
                    new CustomUtils(mActivity).showNoMore(mNewsRV);//展示没有更多
                }

                break;
            case LoadNewsType.TYPE_LOAD_MORE_ERROR:

                break;
        }
    }

    @Override
    public void onItemClick(View view, int i) {
        MeetingBean meetingBean1 =  mNewsListAdapter.getData().get(i);
        mNewsListAdapter.getData().get(i).intentToDetail(mActivity,i);
        if (meetingBean1.getHasReaded()==1){
            changeReadState(meetingBean1);
        }
    }

    private void changeReadState(final MeetingBean meetingBean1) {
        RetrofitManager.getInstance(1).changeReadState(String.valueOf(meetingBean1.getId()))
                .compose(TransformUtils.<BaseRspObj>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj>() {
                    @Override
                    public void onCompleted() {
                        KLog.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                    }

                    @Override
                    public void onNext(BaseRspObj responseBody) {
                        if ("0".equals(responseBody.getHead().getRspCode())){
                            KLog.d(responseBody.toString());
                            onRefresh();//跳转会议详情，刷新页面
                            if ("0".equals(meetingBean1.getStatus())){
                                EventBus.getDefault().post(new ReadMeeting(true));
                            }
                        }
                    }
                });
    }


//    @Subscribe
//    public void onEventMainThread(JoinToMeetingEvent event) {
//        if (event.getPos()>0){
//            onRefresh();
//            KLog.a("********"+"刷新了");
//        }
//        mNewsListAdapter.notifyDataSetChanged();
////        KLog.d("harvic", mNewsListAdapter.getData().get(event.getPos()).getJoinType());
////        mNewsListAdapter.getData().get(event.getPos()).setJoinType(true);
//    }

    //扫描二维码签到之后刷新会议列表以改变状态
    @Subscribe
    public void onCheckMeetingToRefresh(CheckMeetingStateEvent event){
        onRefresh();
    }

    @Override
    public void onResume() {
        onRefresh();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void getJoinResult(boolean flag) {
        if (flag){
            KLog.a("********"+"刷新了");
            onRefresh();
        }
    }
}
