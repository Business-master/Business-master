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
import com.ristone.businessasso.mvp.entity.AreaSeaBean;
import com.ristone.businessasso.mvp.entity.WeatherBean;
import com.ristone.businessasso.mvp.entity.response.base.BaseNewBean;
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
import com.ristone.businessasso.widget.RecyclerViewDivider;
import com.ristone.businessasso.widget.autofittextview.AutofitTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 首页  基层-组织   碎片
 */
//public class NewsFragment_new extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener
//        , NewsView<List<BaseNewBean>>
//        , BusinessView,AreaSeaView
//        , BaseQuickAdapter.RequestLoadMoreListener
//        , BaseQuickAdapter.OnRecyclerViewItemClickListener
//        , BaseSliderView.OnSliderClickListener {

    public class NewsFragment_new extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener
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

    private View weatherView;
    private View areaView;//区域
    private TextView area_tv;//区域选择按钮
    private TextView area_show;//区域展示按钮
    private View mTitleHeader;
    private View mSlideHeader;
    private SliderLayout sliderLayout;
    private PagerIndicator pagerIndicator;
    private AutofitTextView tv_maxW;
    private TextView tv_dayTxt;
    private TextView tv_carNoLimit;
    private TextView tv_pmten;
    private TextView tv_times;
    private TextView tv_no_date;
    private TextView tv_week;
    private ImageView img_weather;

    private WeatherBean weatherBean;
    @Inject
    Activity mActivity;
    @Inject
    NewsPresenterImpl mNewsPresenter;
    @Inject
    BusinessPresenterImpl mBusinessPresenter;
    @Inject
    AreaSeaPresenterImpl mAreaSeaPresenterImpl;

    private BaseQuickAdapter mNewsListAdapter;
    private BaseQuickAdapter mAreaAdapter;
    private List<BaseNewBean> likeBeanList;
    private List<AreaSeaBean>  areaSeaBeanList;

    private int pageNum = 1;
//    private boolean isXunFrg;//true是讯息页,false是协会页
    private int  isXunFrg;//1是讯息页,2是协会页,4区域选择

//    private  String code="0001";
    private  String code="";
    private String chamCode="";
//    String area="";
    String area="所有地区";
    public static NewsFragment_new getInstance(@NewsType.checker int checker) {
        NewsFragment_new newsFragment = new NewsFragment_new();
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
//        area_tv.setText(area);
        area_show.setText(area);

        area_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, AreaActivity.class));
            }
        });
//        initHeadView();
//        initSlider();
        initSwipeRefreshLayout();
        initRecyclerView();
        initPresenter(code, chamCode);
//        loadBannerData();
//        loadWeather();
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
//        area_tv.setText(area+" >>");
        area_show.setText(area);

        initPresenter(code,chamCode);
    }

//    boolean flag = true;
//    @Subscribe
//    public void onEventMainThread(AreaFirstEvent event){
//        //基层组织 页面 获得---初始化地区---第一个地区名
//        area = event.getAreaSeaBean().getAreaCode()+" >>";
//        if (flag){
//            area_tv.setText(area);
//            flag=false;
//        }
//
//    }






    private void initIntentData() {
        if (getArguments() == null){
            isXunFrg=4;
        }else {
            isXunFrg=getArguments().getInt(NEW_TYPE);
        }

    }


//    private void initHeadView() {
////        if (!isXunFrg) return;
//        if (isXunFrg!=1)return;
//        weatherView = View.inflate(mActivity, R.layout.layout_weather, null);
//        mTitleHeader = View.inflate(mActivity, R.layout.layout_head_text, null);
//        mSlideHeader = View.inflate(mActivity, R.layout.layout_autoloop_viewpage, null);
//        sliderLayout = (SliderLayout) mSlideHeader.findViewById(R.id.slider);
//        pagerIndicator = (PagerIndicator) mSlideHeader.findViewById(R.id.custom_indicator);
//        tv_maxW = (AutofitTextView) weatherView.findViewById(R.id.tv_maxW);
//        tv_dayTxt = (TextView) weatherView.findViewById(R.id.tv_dayTxt);
//        tv_carNoLimit = (TextView) weatherView.findViewById(R.id.tv_carNoLimit);
//        tv_pmten = (TextView) weatherView.findViewById(R.id.tv_pmten);
//        tv_times = (TextView) weatherView.findViewById(R.id.tv_times);
//        tv_no_date = (TextView) weatherView.findViewById(R.id.tv_no_date);
//        tv_week = (TextView) weatherView.findViewById(R.id.tv_week);
//        img_weather = (ImageView) weatherView.findViewById(R.id.img_weather);
//    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getIntArray(R.array.gplus_colors));
    }

    private void initPresenter(String code, String chamCode) {
//        if (isXunFrg==1) {
//            mNewsPresenter.setNewsTypeAndId(pageNum, Constants.numPerPage, "",-1);
//            mNewsPresenter.attachView(this);
//            mPresenter = mNewsPresenter;
//            mPresenter.onCreate();
//        } else  if (isXunFrg==2){
//            mBusinessPresenter.setNewsTypeAndId(pageNum, Constants.numPerPage, "",-1);
//            mBusinessPresenter.attachView(this);
//            mPresenter = mBusinessPresenter;
//            mPresenter.onCreate();
//        }else

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

//        likeBeanList = new ArrayList<>();
//        mNewsListAdapter = new NewsAdapter(R.layout.item_news, likeBeanList);
//        mNewsListAdapter.setOnLoadMoreListener(this);
//        if (isXunFrg==1) {
//            mNewsListAdapter.addHeaderView(weatherView);
//            mNewsListAdapter.addHeaderView(mSlideHeader);
//            mNewsListAdapter.addHeaderView(mTitleHeader);
//        }
//        mNewsListAdapter.setOnRecyclerViewItemClickListener(this);
//        mNewsListAdapter.openLoadMore(Constants.numPerPage, true);
//        mNewsRV.setAdapter(mNewsListAdapter);

        areaSeaBeanList = new ArrayList<>();
        //区域适配器
        if (isXunFrg==4){
            mAreaAdapter = new AreaAdapter(R.layout.item_news, areaSeaBeanList);
            mAreaAdapter.addHeaderView(areaView);
            mAreaAdapter.setOnLoadMoreListener(this);
            mAreaAdapter.setOnRecyclerViewItemClickListener(this);
            mAreaAdapter.openLoadMore(Constants.numPerPage, true);
            mNewsRV.setAdapter(mAreaAdapter);
        }




    }

//    /**
//     * 初始化导航图
//     */
//    private void initSlider() {
//        if (isXunFrg!=1 || sliderLayout == null || pagerIndicator == null) return;
//        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
//        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//        sliderLayout.setDuration(4000);
//        sliderLayout.setCustomIndicator(pagerIndicator);
//    }

//    /**
//     * 初始化轮播图
//     *
//     * @param mList
//     */
//    private void initSlider(List<BannerBean> mList) {
//        if (isXunFrg!=1) return;
//        if (mList!=null&&mList.size()>0){
//            mSlideHeader.setVisibility(VISIBLE);
//        }else {
//            mSlideHeader.setVisibility(View.GONE);
//            return;
//        }
//
//        for (BannerBean pageIconBean : mList) {
////            AutoSliderView textSliderView = new AutoSliderView(this.getActivity(), pageIconBean);
//            TextSliderView textSliderView = new TextSliderView(this.getActivity());
//            // initialize a SliderLayout
//            textSliderView
//                    .description(pageIconBean.getTitle())
//                    .image(ApiConstants.NETEAST_HOST + pageIconBean.getFmImg())
//                    .setScaleType(BaseSliderView.ScaleType.Fit)
//                    .setOnSliderClickListener(this);
//
//            //add your extra information
//            Bundle bundle = new Bundle();
//            bundle.putString("id", pageIconBean.getId() + "");
//            textSliderView.bundle(bundle);
//            sliderLayout.addSlider(textSliderView);
//        }
//    }

//    private void initWeatherData(WeathersBean weathersBean) {
//        weatherBean = weathersBean.getWeather();
//        tv_maxW.setText(weatherBean.getMaxW());
//        tv_dayTxt.setText(weatherBean.getDayTxt() + weatherBean.getMinW() + "/" + weatherBean.getMaxW() + "℃");
//
//        String singleStr = weathersBean.getType() ? judgeSingle() ? "单号" : "双号" : weathersBean.getCarNoLimit();
//
//        tv_carNoLimit.setText(singleStr);
//        tv_pmten.setText(weatherBean.getPmten());
//        tv_times.setText(weatherBean.getTimes());
//        tv_no_date.setText(DateUtil.getLunarMonth() + DateUtil.getLunarDay());
//        tv_week.setText(DateUtil.getWeek());
//        Glide.with(App.getAppContext()).load(String.format(ApiConstants.NETEAST_IMG_HOST, weatherBean.getCode())).asBitmap() // gif格式有时会导致整体图片不显示，貌似有冲突
//                .format(DecodeFormat.PREFER_ARGB_8888)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.color.image_place_holder)
//                .error(R.drawable.ic_load_fail)
//                .into(img_weather);
//    }

//    private boolean judgeSingle() {
//        SimpleDateFormat sDateFormat = new SimpleDateFormat("dd");
//        String date = sDateFormat.format(new java.util.Date());
//        KLog.a("date--->" + date);
//        return (Integer.parseInt(date) % 2) == 1;
//    }

//    private void loadBannerData() {
//        if (isXunFrg!=1) return;
//        RetrofitManager.getInstance(1).getBannersObservable()
//                .compose(TransformUtils.<RspBannerBean>defaultSchedulers())
//                .subscribe(new Subscriber<RspBannerBean>() {
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
//                    public void onNext(RspBannerBean rspBannerBean) {
//                        initSlider(rspBannerBean.getBody().getNewsBannerList());
//                    }
//                });
//    }

//    private void loadWeather() {
//        if (isXunFrg!=1) return;
//        RetrofitManager.getInstance(1).getWeatherObservable()
//                .compose(TransformUtils.<RspWeatherBean>defaultSchedulers())
//                .subscribe(new Subscriber<RspWeatherBean>() {
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
//                    public void onNext(RspWeatherBean rspBannerBean) {
//                        if (rspBannerBean.getHead().getRspCode().equals("0"))
//                            initWeatherData(rspBannerBean.getBody());
//                    }
//                });
//    }


//    @Override
//    public void setNewsList(List<BaseNewBean> newsBean, @LoadNewsType.checker int loadType) {
////        checkIsEmpty(newsBean.getLikeList());
//        switch (loadType) {
//            case LoadNewsType.TYPE_REFRESH_SUCCESS:
//                mSwipeRefreshLayout.setRefreshing(false);
//                mNewsListAdapter.setNewData(newsBean);
//                checkIsEmpty(newsBean);
//                break;
//            case LoadNewsType.TYPE_REFRESH_ERROR:
//                mSwipeRefreshLayout.setRefreshing(false);
//                checkIsEmpty(newsBean);
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
//    }

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
//        if (isXunFrg==1) {
//            mNewsPresenter.refreshData();
//        } else  if (isXunFrg==2){
//            mBusinessPresenter.refreshData();
//        }else

        if (isXunFrg==4){
            mAreaSeaPresenterImpl.refreshData();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (isXunFrg==1) {
//            mNewsPresenter.onDestory();
//        } else   if (isXunFrg==2){
//            mBusinessPresenter.onDestory();
//        } else

        if (isXunFrg==4){
            EventBus.getDefault().unregister(this);
            mAreaSeaPresenterImpl.onDestory();
        }

    }

    @Override
    public void onLoadMoreRequested() {
//        if (isXunFrg==1) {
//            mNewsPresenter.loadMore();
//        } else  if (isXunFrg==2) {
//            mBusinessPresenter.loadMore();
//        } else

        if (isXunFrg==4) {
            mAreaSeaPresenterImpl.loadMore();
        }

    }

//    private void checkIsEmpty(List<BaseNewBean> newsSummary) {
//        if (newsSummary != null && mNewsListAdapter.getData().size()>0 ) {
//            mNewsRV.setVisibility(View.VISIBLE);
//            mEmptyView.setVisibility(View.GONE);
//        } else {
//            mNewsRV.setVisibility(View.GONE);
//            mEmptyView.setVisibility(View.VISIBLE);
//        }
//    }

    private void checkEmpty(List<AreaSeaBean> newsSummary) {
        if (newsSummary != null && mAreaAdapter.getData().size()>0) {
            mNewsRV.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        } else {
//            mNewsRV.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
            mEmptyView.setText("暂无数据");
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
        Intent intent = new Intent(mActivity, NewDetailActivity.class);
//        if (isXunFrg==1||isXunFrg==2){
//            List<BaseNewBean> newsSummaryList = mNewsListAdapter.getData();
//            intent.putExtra(Constants.NEWS_POST_ID, newsSummaryList.get(position).getId() + "");
//            intent.putExtra(Constants.NEWS_TYPE, isXunFrg);
//        }else

        if (isXunFrg==4){
            List<AreaSeaBean> newsSummaryList = mAreaAdapter.getData();
            intent.putExtra(Constants.NEWS_POST_ID, newsSummaryList.get(position).getId() + "");
            String type = newsSummaryList.get(position).getType();
            intent.putExtra(Constants.NEWS_TYPE, Integer.valueOf(type));
        }
//        List<BaseNewBean> newsSummaryList = mNewsListAdapter.getData();
//        Intent intent = new Intent(mActivity, NewDetailActivity.class);
//        intent.putExtra(Constants.NEWS_POST_ID, newsSummaryList.get(position).getId() + "");
//        intent.putExtra(Constants.NEWS_TYPE, isXunFrg ? "1" : "2");


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

//    @Override
//    public void onSliderClick(BaseSliderView slider) {
//        Bundle bundle = slider.getBundle();
//        if (bundle == null) {
//            return;
//        }
//        Intent intent = new Intent(mActivity, NewDetailActivity.class);
//        intent.putExtra(Constants.NEWS_POST_ID, bundle.getString("id"));
//        intent.putExtra(Constants.NEWS_TYPE, "1");
//        startActivity(sliderLayout, intent);
//
//    }

//    @Override
//    public void setBusinessList(List<BaseNewBean> newsBean, @LoadNewsType.checker int loadType) {
//        switch (loadType) {
//            case LoadNewsType.TYPE_REFRESH_SUCCESS:
//                mSwipeRefreshLayout.setRefreshing(false);
//                mNewsListAdapter.setNewData(newsBean);
//                checkIsEmpty(newsBean);
//                break;
//            case LoadNewsType.TYPE_REFRESH_ERROR:
//                mSwipeRefreshLayout.setRefreshing(false);
//                checkIsEmpty(newsBean);
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
//    }

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
//                    Snackbar.make(mNewsRV, getString(R.string.no_more), Snackbar.LENGTH_SHORT).show();
                }
                break;
            case LoadNewsType.TYPE_LOAD_MORE_ERROR:

                break;
        }
    }

}