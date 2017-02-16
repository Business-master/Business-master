package com.bus.business.mvp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bus.business.R;
import com.bus.business.common.LoadNewsType;
import com.bus.business.mvp.entity.AreaBean;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.mvp.view.AreaView;

import java.util.List;

public class AreaActivity extends BaseActivity implements AreaView{

    @Override
    public int getLayoutId() {
        return R.layout.activity_area2;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        setCustomTitle("选择地区");
        showOrGoneSearchRl(View.GONE);
    }


    @Override
    public void setAreaBeanList(List<AreaBean> areaBeanList, @LoadNewsType.checker int loadType) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String msg) {

    }
}
