package com.ristone.businessasso.mvp.ui.activities.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ristone.businessasso.App;
import com.ristone.businessasso.R;
import com.ristone.businessasso.di.component.ActivityComponent;
import com.ristone.businessasso.di.component.DaggerActivityComponent;
import com.ristone.businessasso.di.module.ActivityModule;
import com.ristone.businessasso.mvp.presenter.base.BasePresenter;
import com.ristone.businessasso.utils.NetUtil;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public abstract class BaseActivity<T extends BasePresenter> extends ActionBarActivity {

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.rl_search)
    RelativeLayout searchRl;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.img_logo)
    ImageView img_logo;

    protected ActivityComponent mActivityComponent;

    protected T mPresenter;

    protected Subscription mSubscription;

    protected Bundle savedInstanceState;

    public abstract int getLayoutId();

    public abstract void initInjector();

    public abstract void initViews();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.i(getClass().getSimpleName());
        this.savedInstanceState = savedInstanceState;
        NetUtil.isNetworkErrThenShowMsg();
        initActivityComponent();
        int layoutId = getLayoutId();
        setContentView(layoutId);
        initInjector();
        ButterKnife.bind(this);

        if (mPresenter != null){
            mPresenter.onCreate();
        }

        if (mToolbar != null) {
            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        initViews();
    }

    public void setCustomTitle(String title) {
        if (mToolbarTitle != null)
        mToolbarTitle.setText(title);
    }

    public void showOrGoneSearchRl(int vis) {
        if (searchRl != null)
        searchRl.setVisibility(vis);
    }

    public void showOrGoneLogo(int vis) {
        if (img_logo != null)
            img_logo.setVisibility(vis);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null){
            mPresenter.onDestory();
        }
    }

    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((App) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }
}
