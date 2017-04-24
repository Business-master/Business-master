package com.ristone.businessasso.mvp.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.ristone.businessasso.R;
import com.ristone.businessasso.mvp.entity.UserBean;
import com.ristone.businessasso.mvp.ui.activities.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 账号管理 页面
 */
public class ManagerActivity extends BaseActivity {

    @BindView(R.id.tv_phone)
    TextView mPhone;
    @BindView(R.id.tv_email)
    TextView mEmail;


    @Inject
    Activity mActivity;
    private UserBean userBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_manager;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        setCustomTitle("账号管理");
        showOrGoneSearchRl(View.GONE);
        userBean = (UserBean) getIntent().getSerializableExtra(UserBean.USER_BEAN);
        mPhone.setText(userBean.getPhoneNo());
        mEmail.setText(userBean.getUserEmail());
    }

    @OnClick(R.id.rl_ret)
    public void intentRetPassw(View view) {
        startActivity(new Intent(mActivity, RetPasswordActivity.class));
    }
}

