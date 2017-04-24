package com.ristone.businessasso.mvp.ui.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.ristone.businessasso.R;
import com.ristone.businessasso.common.Constants;
import com.ristone.businessasso.common.LoadNewsType;
import com.ristone.businessasso.common.NewsType;
import com.ristone.businessasso.mvp.entity.AreaSeaBean;
import com.ristone.businessasso.mvp.entity.MeetingBean;
import com.ristone.businessasso.mvp.entity.response.base.BaseNewBean;
import com.ristone.businessasso.mvp.event.JoinToMeetingEvent;
import com.ristone.businessasso.mvp.presenter.impl.AreaSeaPresenterImpl;
import com.ristone.businessasso.mvp.presenter.impl.BusinessPresenterImpl;
import com.ristone.businessasso.mvp.presenter.impl.MeetingPresenterImpl;
import com.ristone.businessasso.mvp.presenter.impl.NewsPresenterImpl;
import com.ristone.businessasso.mvp.ui.activities.base.BaseActivity;
import com.ristone.businessasso.mvp.ui.adapter.AreaAdapter;
import com.ristone.businessasso.mvp.ui.adapter.MeetingsAdapter;
import com.ristone.businessasso.mvp.view.AreaSeaView;
import com.ristone.businessasso.mvp.view.BusinessView;
import com.ristone.businessasso.mvp.view.CustomListView;
import com.ristone.businessasso.mvp.view.MeetingView;
import com.ristone.businessasso.mvp.view.NewsView;
import com.ristone.businessasso.utils.DBUtils;
import com.ristone.businessasso.utils.NetUtil;
import com.ristone.businessasso.utils.UT;
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
 * 首页搜索页
 */
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

    /*搜索界面--UI组件*/
    @BindView(R.id.search_edit)
    EditText mSearchEdit;
    @BindView(R.id.tv_clear)
     TextView tv_clear;
    @BindView(R.id.tv_tip)
     TextView tv_tip;
    @BindView(R.id.search_history)
    ScrollView search_history;
    /*列表及其适配器*/
    @BindView(R.id.listView)
    CustomListView listView;
    private BaseAdapter adapter;
    /*数据库工具*/
    private DBUtils dbUtils;


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
        initSearchBox();
    }

    /*初始化搜索框*/
    private void initSearchBox(){
        //实例化数据库
        dbUtils = new DBUtils(mActivity);

        // 第一次进入时查询所有的历史记录
        showData(dbUtils.queryData(""));


        //"清空搜索历史"按钮
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //清空数据库
                dbUtils.deleteData();
                showData(dbUtils.queryData(""));
            }
        });

        //搜索框的文本变化实时监听
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //输入后调用该方法
            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().trim().length() == 0) {
                    //若搜索框为空,则模糊搜索空字符,即显示所有的搜索历史
                    tv_tip.setText("搜索历史");
                } else {
                    tv_tip.setText("搜索结果");
                }

                //每次输入后都查询数据库并显示
                //根据输入的值去模糊查询数据库中有没有数据
                String tempName = mSearchEdit.getText().toString();
                showData(dbUtils.queryData(tempName));

            }
        });

        //列表监听
        //即当用户点击搜索历史里的字段后,会直接将结果当作搜索字段进行搜索
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //获取到用户点击列表里的文字,并自动填充到搜索框内
                TextView textView = (TextView) view;
                String name = textView.getText().toString();
                mSearchEdit.setText(name);

                ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                // 当用户点击搜索历史里的字段后,立即查询关键字
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
            }
        });

        mSearchEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_history.setVisibility(View.VISIBLE);
            }
        });

    }

    private void showData(Cursor cursor) {
        // 创建adapter适配器对象,装入模糊搜索的结果,展示搜索历史
        adapter = new SimpleCursorAdapter(mActivity, android.R.layout.simple_list_item_1, cursor, new String[] { "name" },
                new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
                mNewsListAdapter = new MeetingsAdapter(R.layout.layout_newmeeting_item3,meetList);
//                mNewsListAdapter = new MeetingsAdapter(R.layout.layout_newmeeting_item, meetList);
//                mNewsListAdapter = new MeetingsAdapter(R.layout.layout_meeting_item, meetList);
                break;
        }

        mNewsListAdapter.setOnLoadMoreListener(this);
        mNewsListAdapter.setOnRecyclerViewItemClickListener(this);
        mNewsRV.setAdapter(mNewsListAdapter);
    }


    @OnClick({R.id.search_cancel})
    public void touchSearch(View v) {
        switch (v.getId()){
            case R.id.search_cancel :
                this.finish();
                break;
        }

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
                if (newsBean==null||newsBean.size() == 0){
                    UT.show("暂无数据");
                    if (mNewsListAdapter.getData().size() == 0) return;
                }
                search_history.setVisibility(View.GONE);
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
                if ( areaSeaBeanList==null||areaSeaBeanList.size() == 0){
                    UT.show("暂无数据");
                    if (mNewsListAdapter.getData().size() == 0) return;
                }
                search_history.setVisibility(View.GONE);
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
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            String tempName=mSearchEdit.getText().toString().trim();
            if (TextUtils.isEmpty(tempName)&"".equals(tempName)){
                UT.show("请输入关键字");
                return false;
            }

            // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
            boolean hasData = dbUtils.hasData(tempName);
            if (!hasData) {
                dbUtils.insertData(tempName);
                showData(dbUtils.queryData(""));
            }


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
        //会务--搜索 会出现正在召开的会议，有报名请假按钮，用到 JoinToMeetingEvent
//        ((MeetingBean)mNewsListAdapter.getData().get(event.getPos())).setJoinType(true);
        if (event.getPos()>0){
            onRefresh();
        }
        mNewsListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        onRefresh();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


}
