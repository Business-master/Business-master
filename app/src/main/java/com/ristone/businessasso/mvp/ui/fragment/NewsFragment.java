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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.ristone.businessasso.App;
import com.ristone.businessasso.R;
import com.ristone.businessasso.common.ApiConstants;
import com.ristone.businessasso.common.Constants;
import com.ristone.businessasso.common.LoadNewsType;
import com.ristone.businessasso.common.NewsType;
import com.ristone.businessasso.mvp.entity.BannerBean;
import com.ristone.businessasso.mvp.entity.WeatherBean;
import com.ristone.businessasso.mvp.entity.WeathersBean;
import com.ristone.businessasso.mvp.entity.XinWenBean;
import com.ristone.businessasso.mvp.entity.response.RspBannerBean;
import com.ristone.businessasso.mvp.entity.response.RspWeatherBean;

import com.ristone.businessasso.mvp.presenter.impl.NewsPresenterImpl;
import com.ristone.businessasso.mvp.ui.activities.ExamsActivity;
import com.ristone.businessasso.mvp.ui.activities.NewDetailActivity;
import com.ristone.businessasso.mvp.ui.activities.TopicListActivity;

import com.ristone.businessasso.mvp.ui.adapter.NewsAdapter;
import com.ristone.businessasso.mvp.ui.fragment.base.BaseLazyFragment;
import com.ristone.businessasso.mvp.view.NewsView;
import com.ristone.businessasso.repository.network.RetrofitManager;
import com.ristone.businessasso.utils.CustomUtils;
import com.ristone.businessasso.utils.DateUtil;
import com.ristone.businessasso.utils.NetUtil;
import com.ristone.businessasso.utils.TransformUtils;
import com.ristone.businessasso.utils.VideoVisibleUtils;
import com.ristone.businessasso.widget.RecyclerViewDivider;
import com.ristone.businessasso.widget.autofittextview.AutofitTextView;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.VideoEvent;
import rx.Subscriber;

import static android.view.View.VISIBLE;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/23
 * 首页  新闻(含有视频)   碎片
 */
public class
NewsFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener
        , NewsView<List<XinWenBean>>
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseQuickAdapter.OnRecyclerViewItemClickListener
        , BaseSliderView.OnSliderClickListener {

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
    private ImageView mImgAct;//调查问卷点击图标

    private WeatherBean weatherBean;

    boolean flag = false;//是否为视频新闻

    @Inject
    Activity mActivity;
    @Inject
    NewsPresenterImpl mNewsPresenter;

    private BaseQuickAdapter mNewsListAdapter;
    private List<XinWenBean> likeBeanList;

    private int pageNum = 1;
    private boolean isXunFrg;//true是讯息页,false是协会页
    int videoposition =Integer.MAX_VALUE;//视频新闻的位置
    VideoVisibleUtils videoVisibleUtils;

    public static NewsFragment getInstance(@NewsType.checker int checker) {
        NewsFragment newsFragment = new NewsFragment();
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
        initHeadView();
        initSlider();
        initSwipeRefreshLayout();
        initRecyclerView();
        initPresenter();
        loadBannerData();
        loadWeather();
        videoVisibleUtils =    new VideoVisibleUtils(mActivity);
        videoVisibleUtils.judgeVisible(mNewsRV);
    }

    private void initIntentData() {
        isXunFrg = getArguments() == null || getArguments().getInt(NEW_TYPE) == 1;
    }


    private void initHeadView() {
        if (!isXunFrg) return;
        weatherView = View.inflate(mActivity, R.layout.layout_weather, null);
        mTitleHeader = View.inflate(mActivity, R.layout.layout_head_text, null);
        mImgAct = (ImageView) mTitleHeader.findViewById(R.id.img_act);
        mSlideHeader = View.inflate(mActivity, R.layout.layout_autoloop_viewpage, null);
        sliderLayout = (SliderLayout) mSlideHeader.findViewById(R.id.slider);
        pagerIndicator = (PagerIndicator) mSlideHeader.findViewById(R.id.custom_indicator);
        tv_maxW = (AutofitTextView) weatherView.findViewById(R.id.tv_maxW);
        tv_dayTxt = (TextView) weatherView.findViewById(R.id.tv_dayTxt);
        tv_carNoLimit = (TextView) weatherView.findViewById(R.id.tv_carNoLimit);
        tv_pmten = (TextView) weatherView.findViewById(R.id.tv_pmten);
        tv_times = (TextView) weatherView.findViewById(R.id.tv_times);
        tv_no_date = (TextView) weatherView.findViewById(R.id.tv_no_date);
        tv_week = (TextView) weatherView.findViewById(R.id.tv_week);
        img_weather = (ImageView) weatherView.findViewById(R.id.img_weather);
        mImgAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, ExamsActivity.class);
                mActivity.startActivity(intent);
            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
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
        if (isXunFrg) {
            mNewsPresenter.setNewsTypeAndId(pageNum, Constants.numPerPage, "",Constants.new_types);
            mNewsPresenter.attachView(this);
            mPresenter = mNewsPresenter;
            mPresenter.onCreate();
        }

    }

    private void initRecyclerView() {
        mNewsRV.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false);
        mNewsRV.addItemDecoration(new RecyclerViewDivider(mActivity,
                LinearLayoutManager.VERTICAL, 2, getResources().getColor(R.color.red)));
        mNewsRV.setLayoutManager(mLayoutManager);
        mNewsRV.setItemAnimator(new DefaultItemAnimator());
        likeBeanList = new ArrayList<>();

        mNewsListAdapter = new NewsAdapter(R.layout.layout_new, likeBeanList);
        mNewsListAdapter.setOnLoadMoreListener(this);
        if (isXunFrg) {
            mNewsListAdapter.addHeaderView(weatherView);
            mNewsListAdapter.addHeaderView(mSlideHeader);
            mNewsListAdapter.addHeaderView(mTitleHeader);
        }
        mNewsListAdapter.setOnRecyclerViewItemClickListener(this);
        mNewsListAdapter.openLoadMore(Constants.numPerPage, true);
        mNewsRV.setAdapter(mNewsListAdapter);

    }

    /**
     * 初始化导航图
     */
    private void initSlider() {
        if (!isXunFrg || sliderLayout == null || pagerIndicator == null) return;
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setDuration(4000);
        sliderLayout.setCustomIndicator(pagerIndicator);
    }

    /**
     * 初始化轮播图
     *
     * @param mList
     */
    private void initSlider(List<BannerBean> mList) {
        if (!isXunFrg) return;
        mSlideHeader.setVisibility(VISIBLE);
        for (BannerBean pageIconBean : mList) {
//            AutoSliderView textSliderView = new AutoSliderView(this.getActivity(), pageIconBean);
            TextSliderView textSliderView = new TextSliderView(this.getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(pageIconBean.getTitle())
                    .image(ApiConstants.NETEAST_HOST + pageIconBean.getFmImg())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            Bundle bundle = new Bundle();
            bundle.putString("id", pageIconBean.getId() + "");
            bundle.putString("img_url", pageIconBean.getFmImg());
            bundle.putBoolean("isTopci", (!TextUtils.isEmpty(pageIconBean.getTypes())) && pageIconBean.getTypes().equals("3"));
            textSliderView.bundle(bundle);
            sliderLayout.addSlider(textSliderView);
        }
    }

    private void initWeatherData(WeathersBean weathersBean) {
        weatherBean = weathersBean.getWeather();
        tv_maxW.setText(weatherBean.getMaxW());
        tv_dayTxt.setText(weatherBean.getDayTxt() + weatherBean.getMaxW() + "/" + weatherBean.getMinW() + "℃");

        String singleStr = weathersBean.getType() ? judgeSingle() ? "单号" : "双号" : weathersBean.getCarNoLimit();

        tv_carNoLimit.setText(singleStr);
        tv_pmten.setText(weatherBean.getPmten());
        tv_times.setText(weatherBean.getTimes());
        tv_no_date.setText(DateUtil.getLunarMonth() + DateUtil.getLunarDay());
        tv_week.setText(DateUtil.getWeek());
        Glide.with(App.getAppContext()).load(String.format(ApiConstants.NETEAST_IMG_HOST, weatherBean.getCode())).asBitmap() // gif格式有时会导致整体图片不显示，貌似有冲突
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.image_place_holder)
                .error(R.drawable.ic_load_fail)
                .into(img_weather);
    }

    private boolean judgeSingle() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("dd");
        String date = sDateFormat.format(new java.util.Date());
        KLog.a("date--->" + date);
        return (Integer.parseInt(date) % 2) == 1;
    }

    private void loadBannerData() {
        if (!isXunFrg) return;
        RetrofitManager.getInstance(1).getBannersObservable()
                .compose(TransformUtils.<RspBannerBean>defaultSchedulers())
                .subscribe(new Subscriber<RspBannerBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RspBannerBean rspBannerBean) {
                        initSlider(rspBannerBean.getBody().getNewsBannerList());
                    }
                });
    }

    private void loadWeather() {
        if (!isXunFrg) return;
        RetrofitManager.getInstance(1).getWeatherObservable()
                .compose(TransformUtils.<RspWeatherBean>defaultSchedulers())
                .subscribe(new Subscriber<RspWeatherBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RspWeatherBean rspBannerBean) {
                        if (rspBannerBean.getHead().getRspCode().equals("0"))
                            initWeatherData(rspBannerBean.getBody());
                    }
                });
    }


    @Override
    public void setNewsList(List<XinWenBean> newsBean, @LoadNewsType.checker int loadType) {
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
        if (isXunFrg) {
            mNewsPresenter.refreshData();
            loadBannerData();
            loadWeather();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isXunFrg) {
            mNewsPresenter.onDestory();
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLoadMoreRequested() {
        if (isXunFrg) {
            mNewsPresenter.loadMore();
        }

    }

    private void checkIsEmpty(List<XinWenBean> newsSummary) {
        if (newsSummary == null && mNewsListAdapter.getData() == null) {
            mNewsRV.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mNewsRV.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        }
    }


    @Subscribe
    public void onEventMainThread(final VideoEvent event){
        videoVisibleUtils.setPostion(event.getPosition());
    }

    @Override
    public void onItemClick(View view, int position) {

       List<XinWenBean>  xinWenBeanList = mNewsListAdapter.getData();
        if (xinWenBeanList!=null&&xinWenBeanList.size()>0){
            XinWenBean xinWenBean=  xinWenBeanList.get(position);
            if (xinWenBean!=null&&!TextUtils.isEmpty(xinWenBean.getTypes())){
                  if (Constants.video_new.equals(xinWenBean.getTypes())){
                      flag = true;
                      videoposition = position;
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
        List<XinWenBean> newsSummaryList = mNewsListAdapter.getData();
        Intent intent = new Intent(mActivity, NewDetailActivity.class);
        intent.putExtra(Constants.NEWS_POST_ID, newsSummaryList.get(position).getId() + "");
        intent.putExtra(Constants.NEWS_TYPE,  Constants.DETAIL_XUN_TYPE );
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
    public void onSliderClick(BaseSliderView slider) {
        Bundle bundle = slider.getBundle();
        if (bundle == null) {
            return;
        }
        Boolean isTopic = bundle.getBoolean("isTopci");
        Intent intent = new Intent(mActivity, isTopic ? TopicListActivity.class : NewDetailActivity.class);
        intent.putExtra(Constants.NEWS_POST_ID, bundle.getString("id"));
        intent.putExtra(Constants.NEWS_IMG, bundle.getString("img_url"));
        intent.putExtra(Constants.NEWS_TYPE, Constants.DETAIL_XUN_TYPE);
        startActivity(sliderLayout, intent);

    }

}
