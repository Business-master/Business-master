package com.bus.business.mvp.ui.activities;

import android.view.View;

import com.bus.business.R;
import com.bus.business.mvp.ui.activities.base.BaseActivity;

public class DropDetailActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_drop_detail;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        setCustomTitle("账号管理");
        showOrGoneSearchRl(View.GONE);
    }

}
