package com.ristone.businessasso.mvp.ui.activities;

import android.view.View;

import com.ristone.businessasso.R;
import com.ristone.businessasso.mvp.ui.activities.base.BaseActivity;
import com.ristone.businessasso.utils.UT;

import butterknife.OnClick;

/**
 * 新闻管理 页面
 */
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
