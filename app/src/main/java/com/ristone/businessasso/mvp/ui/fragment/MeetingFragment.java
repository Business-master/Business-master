package com.ristone.businessasso.mvp.ui.fragment;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ristone.businessasso.R;
import com.ristone.businessasso.common.Constants;
import com.ristone.businessasso.common.LoadNewsType;
import com.ristone.businessasso.common.UsrMgr;
import com.ristone.businessasso.listener.JoinMeetingCallBack;
import com.ristone.businessasso.mvp.entity.MeetingBean;
import com.ristone.businessasso.mvp.entity.response.base.BaseRspObj;
import com.ristone.businessasso.mvp.event.CheckMeetingStateEvent;
import com.ristone.businessasso.mvp.event.MeetingDetailEvent;
import com.ristone.businessasso.mvp.event.ReadMeeting;
import com.ristone.businessasso.mvp.presenter.impl.MeetingPresenterImpl;
import com.ristone.businessasso.mvp.ui.adapter.MeetingsAdapter;
import com.ristone.businessasso.mvp.ui.fragment.base.BaseFragment;
import com.ristone.businessasso.mvp.view.MeetingView;
import com.ristone.businessasso.repository.network.RetrofitManager;
import com.ristone.businessasso.utils.CustomUtils;
import com.ristone.businessasso.utils.NetUtil;
import com.ristone.businessasso.utils.TransformUtils;
import com.ristone.businessasso.widget.RecyclerViewDivider;
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
        EventBus.getDefault().unregister(this);
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

    @Subscribe
    public void onEventMainThread(MeetingDetailEvent event){
        /**
         *1 报名 2请假 3签到
         */

        if(event.getPos()>0){
            onRefresh();
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        onRefresh();
//    }

    @Override
    public void getJoinResult(boolean flag) {
        if (flag){
            onRefresh();
        }
    }
}
