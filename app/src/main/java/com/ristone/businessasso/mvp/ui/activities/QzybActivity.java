package com.ristone.businessasso.mvp.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ristone.businessasso.R;
import com.ristone.businessasso.mvp.entity.WanBean;
import com.ristone.businessasso.mvp.ui.activities.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 奇珍异宝详情页面
 */

public class QzybActivity extends BaseActivity {

    @BindView(R.id.icon_qzyb)
    ImageView icon_qzyb;
    @BindView(R.id.banner_qzyb)
    ImageView banner_qzyb;
    @BindView(R.id.content_qzyb)
    TextView content_qzyb;
//    @BindView(R.id.empty_qzyb)
//    TextView empty_qzyb;
    @BindView(R.id.ll_qzyb)
    LinearLayout ll_qzyb;

    private WanBean wanBean ;
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
        Bundle bundle = getIntent().getExtras();
        wanBean = (WanBean) bundle.getSerializable("wanBean");

        setCustomTitle("产品详情");
        showOrGoneSearchRl(View.GONE);
        ll_qzyb.setVisibility(View.VISIBLE);
        content_qzyb.setText(wanBean.getDetail());
        icon_qzyb.setImageResource(wanBean.getIcon());
        Glide.with(this).load(wanBean.getBanner()).into(banner_qzyb);

//        if ("-1".equals(wanBean.getDetail())){
//            ll_qzyb.setVisibility(View.GONE);
//            empty_qzyb.setVisibility(View.VISIBLE);
//        }else {
//            empty_qzyb.setVisibility(View.GONE);
//        }
    }


    @OnClick({R.id.ensure_qzyb})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ensure_qzyb:
                wanBean.intentToWebAct(this);
                break;
        }
    }


}
