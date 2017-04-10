package com.bus.business.mvp.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bus.business.R;
import com.bus.business.common.Constants;
import com.bus.business.common.DropsType;
import com.bus.business.common.LoadNewsType;
import com.bus.business.mvp.entity.CashBean;
import com.bus.business.mvp.entity.DropBean;
import com.bus.business.mvp.presenter.impl.DropPresenterImpl;
import com.bus.business.mvp.ui.adapter.DropsAdapter;
import com.bus.business.mvp.ui.fragment.base.BaseFragment;
import com.bus.business.mvp.view.DropdownButton;
import com.bus.business.mvp.view.DropdownItemObject;
import com.bus.business.mvp.view.DropdownListView;
import com.bus.business.mvp.view.NewsView;
import com.bus.business.utils.CustomUtils;
import com.bus.business.utils.NetUtil;
import com.bus.business.utils.UT;
import com.bus.business.widget.RecyclerViewDivider;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.socks.library.KLog;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bus.business.common.DropsType.DROP_TYPE;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/2/17
 * 专家---银行类
 */

public class DropDownFragment extends BaseFragment implements DropdownListView.Container
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseQuickAdapter.OnRecyclerViewItemClickListener
        , NewsView<List<DropBean>>,SwipeRefreshLayout.OnRefreshListener {
    public static final int CALL_OK = 0X110;
    public static final String[] TYPE_NAME = new String[]{"pledgeCode@", "replayCode@", "loanCode@","loanLimit@"};

    private DropdownButton drop_chooseType;
    private DropdownButton drop_chooseLanguage;
    private DropdownButton drop_chooseDate;
    private DropdownButton drop_chooseLimit;

    @BindView(R.id.chooseType)
    DropdownButton chooseType;
    @BindView(R.id.chooseLanguage)
    DropdownButton chooseLanguage;
    @BindView(R.id.chooseDate)
    DropdownButton chooseDate;
     @BindView(R.id.chooseLimit)
    DropdownButton chooseLimit;


    @BindView(R.id.dropdownType)
    DropdownListView dropdownType;
    @BindView(R.id.dropdownLanguage)
    DropdownListView dropdownLanguage;
    @BindView(R.id.dropdownDate)
    DropdownListView dropdownDate;
    @BindView(R.id.dropdownLimit)
    DropdownListView dropdownLimit;


    @BindView(R.id.mRecyclerView)
    RecyclerView mNewsRV;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    private ImageView bannerImg;
    private View imgView;
    private View dropView;
    @BindView(R.id.drop_header)
    View drop_header;

    private BaseQuickAdapter mNewsListAdapter;
    private List<DropBean> likeBeanList;
    private LinearLayoutManager mLayoutManager;

    private DropdownListView currentDropdownList;
    Animation dropdown_in, dropdown_out, dropdown_mask_out;
    @BindView(R.id.mask)
    View mask;
    @Inject
    DropPresenterImpl mNewsPresenter;
    private List<DropdownItemObject> chooseTypeData = new ArrayList<>();//选择抵押类型
    private List<DropdownItemObject> chooseLanguageData = new ArrayList<>();//选择还款方式
    private List<DropdownItemObject> chooseDateData = new ArrayList<>();//选择贷款期限
    private List<DropdownItemObject> chooseLimitData = new ArrayList<>();//选择贷款额度


    @Inject
    Activity mActivity;
    private int pageNum = 1;
    private CashBean cashBean;
    private int dropType;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private void initView() {
        drop_header.setVisibility(View.GONE);
        bannerImg.setImageResource(dropType == 1 ? R.mipmap.banner01 : R.mipmap.banner02);

        dropdown_in = AnimationUtils.loadAnimation(mActivity, R.anim.dropdown_in);
        dropdown_out = AnimationUtils.loadAnimation(mActivity, R.anim.dropdown_out);
        dropdown_mask_out = AnimationUtils.loadAnimation(mActivity, R.anim.dropdown_mask_out);
        init();
        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
    }

    public static DropDownFragment getInstance(@DropsType.checker int checker) {
        DropDownFragment newsFragment = new DropDownFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DROP_TYPE, checker);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    private void initIntentData() {
        dropType = getArguments().getInt(DROP_TYPE);
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getIntArray(R.array.gplus_colors)
        );
    }

    @Override
    public void initViews(View view) {
        initIntentData();
        initHeaderView();
        initView();
        initSwipeRefreshLayout();
        initRecyclerView();
        initPresenter();
    }

    //点击咨询详情 拨打电话
    @OnClick({R.id.call_drop_down})
    public void onClick(View v){
         switch (v.getId()){
             case R.id.call_drop_down :

                 if (ActivityCompat.checkSelfPermission(mActivity,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED)
                 {
                         // 不需要解释为何需要该权限，直接请求授权
                         requestPermissions(new String[]{Manifest.permission.CALL_PHONE},CALL_OK);
                 }else {

                     startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:15810864710")));
                 }
                 break;
         }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CALL_OK:
                if (grantResults!=null&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //点击咨询详情 拨打电话
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:15810864710")));
                }else {
                    // 授权失败！
                    UT.show("请手动打开电话权限");
                }
                break;
        }
    }

    private void initHeaderView() {
        imgView = View.inflate(mActivity, R.layout.item_img_header, null);
        bannerImg = (ImageView) imgView.findViewById(R.id.img_banner);
        dropView = View.inflate(mActivity, R.layout.item_drop_header, null);
        //       mask = dropView.findViewById(R.id.mask);
//        dropdownType = (DropdownListView) dropView.findViewById(R.id.dropdownType);
//        dropdownLanguage = (DropdownListView) dropView.findViewById(R.id.dropdownLanguage);
//        dropdownDate = (DropdownListView) dropView.findViewById(R.id.dropdownDate);
        drop_chooseType = (DropdownButton) dropView.findViewById(R.id.drop_chooseType);
        drop_chooseLanguage = (DropdownButton) dropView.findViewById(R.id.drop_chooseLanguage);
        drop_chooseDate = (DropdownButton) dropView.findViewById(R.id.drop_chooseDate);
        drop_chooseLimit = (DropdownButton) dropView.findViewById(R.id.drop_chooseLimit);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_drop_down;
    }

    private int visibleItemCount, totalItemCount, firstVisiblesItem, lastVisibleItemCount;

    private void initRecyclerView() {
        mNewsRV.setHasFixedSize(true);
        mNewsRV.addItemDecoration(new RecyclerViewDivider(mActivity,
                LinearLayoutManager.VERTICAL, 2, getResources().getColor(R.color.red)));
        mLayoutManager = new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false);
        mNewsRV.setLayoutManager(mLayoutManager);
        mNewsRV.setItemAnimator(new DefaultItemAnimator());
        likeBeanList = new ArrayList<>();
        mNewsListAdapter = new DropsAdapter(R.layout.item_drop, likeBeanList);
        mNewsListAdapter.addHeaderView(imgView);
        mNewsListAdapter.addHeaderView(dropView);
        mNewsListAdapter.setOnLoadMoreListener(this);
        mNewsListAdapter.setOnRecyclerViewItemClickListener(this);
        mNewsListAdapter.openLoadMore(Constants.numPerPage, true);
        mNewsRV.setAdapter(mNewsListAdapter);
        mNewsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                visibleItemCount = mNewsRV.getChildCount();
//                totalItemCount = mLayoutManager.getItemCount();
                firstVisiblesItem = mLayoutManager.findFirstVisibleItemPosition();
//                lastVisibleItemCount = mLayoutManager.findLastVisibleItemPosition();
                KLog.a("top---->" + visibleItemCount + "---" + firstVisiblesItem);
                if (firstVisiblesItem >= 1 && drop_header.getVisibility() == View.GONE)
                {  drop_header.setVisibility(View.VISIBLE);
                }
               else if (firstVisiblesItem < 1 && drop_header.getVisibility() == View.VISIBLE)
                {  drop_header.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initPresenter() {
        cashBean = new CashBean();
        cashBean.setCashType(dropType + "");

        mNewsPresenter.setNewsTypeAndId(pageNum, Constants.numPerPage, cashBean);
        mNewsPresenter.attachView(this);
        mPresenter = mNewsPresenter;
        mPresenter.onCreate();
    }

    @Override
    public void show(DropdownListView view) {
        if (currentDropdownList != null) {
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_out);
            currentDropdownList.setVisibility(View.GONE);
            currentDropdownList.button.setChecked(false);
        }
        currentDropdownList = view;
        mask.clearAnimation();
        mask.setVisibility(View.VISIBLE);
        currentDropdownList.clearAnimation();
        currentDropdownList.startAnimation(dropdown_in);
        currentDropdownList.setVisibility(View.VISIBLE);
        currentDropdownList.button.setChecked(true);
    }

    @Override
    public void hide() {
        if (currentDropdownList != null) {
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_out);
            currentDropdownList.button.setChecked(false);
            mask.clearAnimation();
            mask.startAnimation(dropdown_mask_out);
        }
        currentDropdownList = null;
    }

    @Override
    public void onSelectionChanged(DropdownListView view) {
        KLog.a("value--->" + view.current.getValue());
        subStr(view.current.getValue());
        mNewsPresenter.dropData(cashBean);
//        UT.show(cashBean.toString());

    }

    @Override
    public void onTouchToTop() {
        mLayoutManager.scrollToPositionWithOffset(1, 0);
        mLayoutManager.setStackFromEnd(false);
    }

    private void subStr(String str) {
        String result = str.substring(0, str.indexOf("@")) + "@";
        KLog.a("result--->" + result);
        for (int i = 0; i < 4; i++) {
            if (result.equals(TYPE_NAME[i])) {
                result = str.substring(str.indexOf("@") + 1, str.length());
                switch (i) {
                    case 0:
                        cashBean.setPledgeCode(result);
                        break;
                    case 1:
                        cashBean.setReplayCode(result);
                        break;
                    case 2:
                        cashBean.setLoanCode(result);
                        break;
                    case 3:
                        cashBean.setLoanLimit(result);
                        break;
                }
                break;
            }
        }
    }

    void reset() {
        chooseType.setChecked(false);
        chooseLanguage.setChecked(false);
        chooseDate.setChecked(false);
        chooseLimit.setChecked(false);

        dropdownType.setVisibility(View.GONE);
        dropdownLanguage.setVisibility(View.GONE);
        dropdownDate.setVisibility(View.GONE);
        mask.setVisibility(View.GONE);
        dropdownLimit.setVisibility(View.GONE);

        dropdownType.clearAnimation();
        dropdownLanguage.clearAnimation();
        dropdownDate.clearAnimation();
        dropdownLimit.clearAnimation();
        mask.clearAnimation();

    }

    void init() {
        KLog.a("*****init");
        reset();
        chooseTypeData.add(new DropdownItemObject("全部", 0, TYPE_NAME[0]));
        chooseTypeData.add(new DropdownItemObject("信用贷款", 1, TYPE_NAME[0] + "0001"));
        chooseTypeData.add(new DropdownItemObject("抵押贷款", 2, TYPE_NAME[0] + "0002"));
//        chooseTypeData.add(new DropdownItemObject("联保贷", 3, TYPE_NAME[0] + "0003"));
//        chooseTypeData.add(new DropdownItemObject("无需抵押", 4, TYPE_NAME[0] + "0004"));
//        chooseTypeData.add(new DropdownItemObject("车辆抵押", 5, TYPE_NAME[0] + "0005"));
        dropdownType.bindList(chooseTypeData, chooseType, drop_chooseType, this, 0);

        chooseLanguageData.add(new DropdownItemObject("全部", 0, TYPE_NAME[1] + ""));
        chooseLanguageData.add(new DropdownItemObject("分期还款", 1, TYPE_NAME[1] + "0001"));
        chooseLanguageData.add(new DropdownItemObject("到期还款", 2, TYPE_NAME[1] + "0002"));
        chooseLanguageData.add(new DropdownItemObject("随借随还", 3, TYPE_NAME[1] + "0003"));
        dropdownLanguage.bindList(chooseLanguageData, chooseLanguage, drop_chooseLanguage, this, 0);

        chooseDateData.add(new DropdownItemObject("全部", 0, TYPE_NAME[2] + ""));
        chooseDateData.add(new DropdownItemObject("5年以下", 1, TYPE_NAME[2] + "5"));
        chooseDateData.add(new DropdownItemObject("15年以下", 2, TYPE_NAME[2] + "15"));
        chooseDateData.add(new DropdownItemObject("25年以下", 3, TYPE_NAME[2] + "25"));
        dropdownDate.bindList(chooseDateData, chooseDate, drop_chooseDate, this, 0);


        chooseLimitData.add(new DropdownItemObject("全部", 0, TYPE_NAME[3] + ""));
        chooseLimitData.add(new DropdownItemObject("100万以下", 1, TYPE_NAME[3] + "100"));
        chooseLimitData.add(new DropdownItemObject("500万以下", 1, TYPE_NAME[3] + "500"));
        chooseLimitData.add(new DropdownItemObject("1000万以下", 1, TYPE_NAME[3] + "1000"));
        dropdownLimit.bindList(chooseLimitData, chooseLimit, drop_chooseLimit, this, 0);


        dropdown_mask_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (currentDropdownList == null) {
                    reset();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onItemClick(View view, int i) {
//        mLayoutManager.scrollToPositionWithOffset(1, 0);
//        mLayoutManager.setStackFromEnd(true);
        ((DropBean) mNewsListAdapter.getData().get(i)).intentToDetail(mActivity);
    }

    @Override
    public void setNewsList(List<DropBean> newsBean, @LoadNewsType.checker int loadType) {
        switch (loadType) {
            case LoadNewsType.TYPE_REFRESH_SUCCESS:
                mSwipeRefreshLayout.setRefreshing(false);
                if (newsBean==null||newsBean.size()<=0){
                    UT.show("暂无数据");
                }
                mNewsListAdapter.setNewData(newsBean);
                break;
            case LoadNewsType.TYPE_REFRESH_ERROR:
                mSwipeRefreshLayout.setRefreshing(false);
                if (newsBean==null||newsBean.size()<=0){
                    UT.show("暂无数据");
                }
                break;
            case LoadNewsType.TYPE_LOAD_MORE_SUCCESS:

                if (newsBean == null) {
                    return;
                }
                if (newsBean.size() == Constants.numPerPage) {
                    mNewsListAdapter.notifyDataChangedAfterLoadMore(newsBean, true);
                } else {
                    mNewsListAdapter.notifyDataChangedAfterLoadMore(newsBean, false);
                    new CustomUtils(mActivity).showNoMore(mNewsRV);
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
    public void onDestroy() {
        super.onDestroy();
        mNewsPresenter.onDestory();

    }

    @Override
    public void onLoadMoreRequested() {
        mNewsPresenter.loadMore();
    }

    @Override
    public void onRefresh() {
        mNewsPresenter.refreshData();
    }
}
