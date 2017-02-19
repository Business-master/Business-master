package com.bus.business.mvp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bus.business.R;
import com.bus.business.mvp.ui.activities.base.BaseActivity;

/**
 * 奇珍异宝详情页面
 */

public class QzybActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_qzyb;
    }

    @Override
    public void initInjector() {
     mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
          setCustomTitle("汉铭集团");
          showOrGoneSearchRl(View.GONE);
    }


}
