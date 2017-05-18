package com.ristone.businessasso.mvp.ui.activities;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ristone.businessasso.R;
import com.ristone.businessasso.common.Constants;
import com.ristone.businessasso.mvp.entity.BusDetailBean;
import com.ristone.businessasso.mvp.entity.response.RspBusDetailBean;
import com.ristone.businessasso.mvp.entity.response.RspNewDetailBean;
import com.ristone.businessasso.mvp.ui.activities.base.BaseActivity;
import com.ristone.businessasso.repository.network.RetrofitManager;
import com.ristone.businessasso.utils.DateUtil;
import com.ristone.businessasso.utils.TransformUtils;
import com.ristone.businessasso.widget.URLImageGetter;
import com.socks.library.KLog;

import java.text.DecimalFormat;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Subscriber;

public class NewDetailActivity extends BaseActivity {

    private String newsId;
    private int newsType;

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
    @BindView(R.id.tv_phone)
    TextView mPhone;
    @BindView(R.id.tv_zuo_phone)
    TextView mZPhone;
    @BindView(R.id.tv_tag)
    TextView mTag;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.ll_detail_fund_tv)
    LinearLayout ll_detail_fund_tv;

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
        newsType = getIntent().getIntExtra(Constants.NEWS_TYPE,0);

        setCustomTitle(setTitle());
        switch (newsType){
            case Constants.DETAIL_XUN_TYPE:
                ll_detail_fund_tv.setVisibility(View.GONE);
                break;
            case Constants.DETAIL_XIE_TYPE:
                ll_detail_fund_tv.setVisibility(View.VISIBLE);
                break;
            case Constants.DETAIL_TOP_TYPE:
                ll_detail_fund_tv.setVisibility(View.GONE);
                break;
            case Constants.DETAIL_POLICY_TYPE:
                ll_detail_fund_tv.setVisibility(View.GONE);
                break;

        }
        showOrGoneSearchRl(View.GONE);
        mFundTv.setVisibility(setContentTitleVisible() ? View.GONE : View.VISIBLE);
        mPhone.setVisibility(setContentTitleVisible() ? View.GONE : View.VISIBLE);
        mTag.setVisibility(setContentTitleVisible() ? View.GONE : View.VISIBLE);
        mZPhone.setVisibility(setContentTitleVisible() ? View.GONE : View.VISIBLE);
        loadNewDetail();

    }

    private String setTitle(){
        switch (newsType){
            case Constants.DETAIL_XUN_TYPE:
                return "新闻详情";
            case Constants.DETAIL_XIE_TYPE:
                return "商讯详情";
            case Constants.DETAIL_TOP_TYPE:
                return "专题详情";
            case Constants.DETAIL_POLICY_TYPE:
                return "金融政策详情";

        }
        return "";
    }

    private boolean setContentTitleVisible(){
        return newsType == Constants.DETAIL_XUN_TYPE||
                newsType == Constants.DETAIL_TOP_TYPE||newsType == Constants.DETAIL_POLICY_TYPE;
    }

    private void loadNewDetail() {
        switch (newsType){
            case Constants.DETAIL_XUN_TYPE:
            case Constants.DETAIL_POLICY_TYPE:
                RetrofitManager.getInstance(1).getNewDetailObservable(newsId)
                        .compose(TransformUtils.<RspNewDetailBean>defaultSchedulers())
                        .subscribe(new Subscriber<RspNewDetailBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                KLog.d(e.toString());
                            }

                            @Override
                            public void onNext(RspNewDetailBean rspNewDetailBean) {
                                mProgressBar.setVisibility(View.GONE);
                                KLog.d(rspNewDetailBean.toString());
                                fillData(rspNewDetailBean.getBody().getNews());
                            }
                        });
                break;
            case Constants.DETAIL_XIE_TYPE:
                RetrofitManager.getInstance(1).getBusDetailObservable(newsId)
                        .compose(TransformUtils.<RspBusDetailBean>defaultSchedulers())
                        .subscribe(new Subscriber<RspBusDetailBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                KLog.d(e.toString());
                            }

                            @Override
                            public void onNext(RspBusDetailBean rspNewDetailBean) {
                                mProgressBar.setVisibility(View.GONE);
                                KLog.d(rspNewDetailBean.toString());
                                fillData(rspNewDetailBean.getBody().getBusiness());
                            }
                        });
                break;
            case Constants.DETAIL_TOP_TYPE:
                RetrofitManager.getInstance(1).getTopicDetailObservable(newsId)
                        .compose(TransformUtils.<RspNewDetailBean>defaultSchedulers())
                        .subscribe(new Subscriber<RspNewDetailBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                KLog.d(e.toString());
                            }

                            @Override
                            public void onNext(RspNewDetailBean rspNewDetailBean) {
                                mProgressBar.setVisibility(View.GONE);
                                KLog.d(rspNewDetailBean.toString());
                                fillData(rspNewDetailBean.getBody().getNews());
                            }
                        });

                break;

        }
    }

    private void fillData(BusDetailBean bean) {
        mTitle.setText(bean.getTitle());
        mDateTv.setText("发表时间 : " + DateUtil.getCurGroupDay(bean.getCtime()));
        mFrom.setText("来源 : "+bean.getAreaCode());



//        mFundTv.setText("项目总投资 : " + formAmount(bean.getInAmount()) + "元");
//        String str= "<font color='#999999'>项目总投资：</font>" + formMoney(bean.getInAmount()) + "元";
//        mFundTv.setText(Html.fromHtml(str));

        mFundTv.setText(formMoney(bean.getInAmount()) + "元");
        KLog.a("项目投资----------"+formMoney(bean.getInAmount())+"实际数据"+bean.getInAmount());
//        mFundTv.setText("项目总投资 : " +formMoney(bean.getInAmount()) + "元");
        mPhone.setText("联系电话 : " + bean.getPhoneNo());
        mZPhone.setText("座机电话 : "+bean.getPlane());
        KLog.a("详情内容：-----"+bean.getContentS());
        mUrlImageGetter = new URLImageGetter(mNewsDetailBodyTv, bean.getContentS(), 2);
        mNewsDetailBodyTv.setText(Html.fromHtml(bean.getContentS(), mUrlImageGetter, null));
    }

    private String formAmount(double num) {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,###");
        return myformat.format(num);
    }

    private String formMoney(double num){
        DecimalFormat df = new DecimalFormat("#.00");
        String str;
        if (num>=Math.pow(10,8)){
            num=num/Math.pow(10,8);
            str = df.format(num)+"亿";
        }else if (num>Math.pow(10,4)){
            num=num/Math.pow(10,4);
            str =df.format(num)+"万";
        }else {
            str =""+df.format(num);
        }
        return str;
    }
}
