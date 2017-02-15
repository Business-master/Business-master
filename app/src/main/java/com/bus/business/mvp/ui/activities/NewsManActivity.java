package com.bus.business.mvp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bus.business.R;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.utils.UT;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsManActivity extends BaseActivity {



    @Override
    public int getLayoutId() {
        return R.layout.activity_news_man;
    }

    @Override
    public void initInjector() {
           mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
      setCustomTitle("新消息通知");
        showOrGoneSearchRl(View.GONE);
    }

    @OnClick(R.id.push_news)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.push_news:
                UT.show("消息推送");
                break;
        }
    }
}
