package com.bus.business.mvp.ui.activities;

import android.view.View;

import com.bus.business.R;
import com.bus.business.listener.WebPageFinishCallBack;
import com.bus.business.mvp.entity.WanBean;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.widget.ProgressWebView;

import butterknife.BindView;

public class WebActivity extends BaseActivity implements WebPageFinishCallBack,View.OnClickListener {


    @BindView(R.id.web_view)
    ProgressWebView mWebView;


    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        setCustomTitle("加载中...");
        showOrGoneSearchRl(View.GONE);

        mWebView.setFocusable(true);//设置有焦点
        mWebView.setFocusableInTouchMode(true);//设置可触摸
        mWebView.setWebPageFinishCallBack(this);
        mWebView.loadUrl(getIntent().getStringExtra(WanBean.WEBURL));//加载网址

        //页面返回键
        if (mToolbar != null){
            mToolbar.setNavigationOnClickListener(this);
        }
    }

    @Override
    public void onWebPageFinishCallBack(String title) {
        setCustomTitle(title);
    }

    @Override
    public void onClick(View view) {
//        if (mWebView.canGoBack()){
//            mWebView.goBack();
//            return;
//        }
        finish();
    }

    @Override
    public void onBackPressed() {
//        if (mWebView.canGoBack()){
//            mWebView.goBack();
//            return;
//        }
        super.onBackPressed();
    }
}
