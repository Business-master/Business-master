package com.bus.business.mvp.ui.activities;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.common.ApiConstants;
import com.bus.business.mvp.ui.activities.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bus.business.mvp.entity.WanBean.WEBURL;

/**
 * 调查问卷
 */
public class ExamsActivity extends BaseActivity {

    @BindView(R.id.img_act)
    ImageView mActImg;
    @BindView(R.id.img_topic)
    ImageView mTopicImg;
    @BindView(R.id.tv_title)
    TextView mTv;

    private String url;

    @Override
    public int getLayoutId() {
        return R.layout.activity_exams;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        setCustomTitle("调查问卷");
        showOrGoneSearchRl(View.GONE);
        mActImg.setVisibility(View.GONE);
        mTopicImg.setVisibility(View.VISIBLE);
        mTv.setText("调查问卷");
    }

//    @OnClick({R.id.rl_1, R.id.rl_2})
    @OnClick({R.id.rl_1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_1:
                url = ApiConstants.NETEAST_HOST+"dcwj-admin/dcwj/backSubject";
                break;
//            case R.id.rl_2:
//                url = ApiConstants.NETEAST_HOST+"dcwj-admin/dcwj/backYDYLSubject";
//                break;
        }
        Intent intent = new Intent(ExamsActivity.this, WebActivity.class);
        intent.putExtra(WEBURL, url);
        startActivity(intent);
    }
}
