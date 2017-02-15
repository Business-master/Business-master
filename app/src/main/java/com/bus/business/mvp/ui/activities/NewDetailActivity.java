package com.bus.business.mvp.ui.activities;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.common.Constants;
import com.bus.business.mvp.entity.BusDetailBean;
import com.bus.business.mvp.entity.response.RspBusDetailBean;
import com.bus.business.mvp.entity.response.RspNewDetailBean;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.repository.network.RetrofitManager;
import com.bus.business.utils.DateUtil;
import com.bus.business.utils.TransformUtils;
import com.bus.business.widget.URLImageGetter;
import com.socks.library.KLog;

import java.text.DecimalFormat;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Subscriber;

public class NewDetailActivity extends BaseActivity {

    private String newsId;
    private String newsType;

    @Inject
    Activity mActivity;

    @BindView(R.id.news_detail_title_tv)
    TextView mTitle;
    @BindView(R.id.news_detail_from_tv)
    TextView mFrom;
    @BindView(R.id.news_detail_body_tv)
    TextView mNewsDetailBodyTv;
    @BindView(R.id.news_detail_fund_tv)
    TextView mFundTv;
    @BindView(R.id.news_detail_date_tv)
    TextView mDateTv;
    @BindView(R.id.tv_landline)
    TextView mLandline;
    @BindView(R.id.tv_phone)
    TextView mPhone;
    @BindView(R.id.tv_tag)
    TextView mTag;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private URLImageGetter mUrlImageGetter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_detail;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        newsId = getIntent().getStringExtra(Constants.NEWS_POST_ID);
        newsType = getIntent().getStringExtra(Constants.NEWS_TYPE);

        setCustomTitle(newsType.equals("1") ? "新闻详情" : "商讯详情");
        showOrGoneSearchRl(View.GONE);
        mFundTv.setVisibility(newsType.equals("1") ? View.GONE : View.VISIBLE);
        mPhone.setVisibility(newsType.equals("1") ? View.GONE : View.VISIBLE);
        mLandline.setVisibility(newsType.equals("1") ? View.GONE : View.VISIBLE);
        mTag.setVisibility(newsType.equals("1") ? View.GONE : View.VISIBLE);
        loadNewDetail();
    }

    private void loadNewDetail() {
        if (newsType.equals("1")) {
            RetrofitManager.getInstance(1).getNewDetailObservable(newsId)
                    .compose(TransformUtils.<RspNewDetailBean>defaultSchedulers())
                    .subscribe(new Subscriber<RspNewDetailBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(RspNewDetailBean rspNewDetailBean) {
                            mProgressBar.setVisibility(View.GONE);
                            KLog.d(rspNewDetailBean.toString());
                            fillData(rspNewDetailBean.getBody().getNews());
                        }
                    });
        } else {
            RetrofitManager.getInstance(1).getBusDetailObservable(newsId)
                    .compose(TransformUtils.<RspBusDetailBean>defaultSchedulers())
                    .subscribe(new Subscriber<RspBusDetailBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(RspBusDetailBean rspNewDetailBean) {
                            mProgressBar.setVisibility(View.GONE);
                            KLog.d(rspNewDetailBean.toString());
                            fillData(rspNewDetailBean.getBody().getBusiness());
                        }
                    });
        }
    }

    private void fillData(BusDetailBean bean) {
        mTitle.setText(bean.getTitle());
        mDateTv.setText("发表时间 : " + DateUtil.getCurGroupDay(bean.getCtime()));
        mFrom.setText("北京工商联");
        mFundTv.setText("项目总投资" + formAmount(bean.getInAmount()) + "元");
        mPhone.setText("联系电话 : " + bean.getPhoneNo());
        mLandline.setText("座机号码 : " + bean.getPlane());
        mUrlImageGetter = new URLImageGetter(mNewsDetailBodyTv, bean.getContentS(), 2);
        mNewsDetailBodyTv.setText(Html.fromHtml(bean.getContentS(), mUrlImageGetter, null));
    }

    private String formAmount(double num) {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,###.00");
        return myformat.format(num);
    }
}
