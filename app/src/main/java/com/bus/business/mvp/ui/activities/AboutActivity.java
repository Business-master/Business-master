package com.bus.business.mvp.ui.activities;

import android.view.View;

import com.bus.business.R;
import com.bus.business.mvp.ui.activities.base.BaseActivity;

/**
 * 我的-----关于页面
 */
public class AboutActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_about1;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        setCustomTitle("关于我们");
        showOrGoneSearchRl(View.GONE);//去掉搜索
    }
}
