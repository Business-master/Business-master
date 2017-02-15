package com.bus.business.mvp.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.mvp.entity.PhoneBookBean;
import com.bus.business.mvp.ui.activities.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bus.business.mvp.entity.PhoneBookBean.PHONE_BEAN;

public class PhoneDetailActivity extends BaseActivity {

    private PhoneBookBean phoneBookBean;

    @BindView(R.id.tv_name)
    TextView name;
    @BindView(R.id.tv_date)
    TextView phoneNum;

    @Override
    public int getLayoutId() {
        return R.layout.activity_phone_detail;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        setCustomTitle("信息");
        showOrGoneSearchRl(View.GONE);

        phoneBookBean = (PhoneBookBean) getIntent().getSerializableExtra(PHONE_BEAN);
        name.setText(phoneBookBean.getNiceName());
        phoneNum.setText(phoneBookBean.getPhoneNo());
    }

    @OnClick(R.id.img_add)
    public void onClick(View view){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        //url:统一资源定位符
        //uri:统一资源标示符（更广）
        intent.setData(Uri.parse("tel:" + phoneBookBean.getPhoneNo()));
        //开启系统拨号器
        startActivity(intent);
    }
}
