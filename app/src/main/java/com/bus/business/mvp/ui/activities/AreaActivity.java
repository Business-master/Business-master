package com.bus.business.mvp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.bus.business.R;
import com.bus.business.common.Constants;
import com.bus.business.common.LoadNewsType;
import com.bus.business.mvp.entity.AreaBean;
import com.bus.business.mvp.presenter.impl.AreaPresentetImpl;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.mvp.ui.adapter.AreaChAdapter;
import com.bus.business.mvp.view.AreaView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

public class AreaActivity extends BaseActivity implements AreaView{

    @BindView(R.id.area_gv)
    GridView area_gv;

    private AreaChAdapter adapter;

    @Inject
    AreaPresentetImpl mAreaPresentetImpl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_area2;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }


    @Override
    public void setAreaBeanList(List<AreaBean> areaBeanList, @LoadNewsType.checker int loadType) {
//        if (areaBeanList!=null&&areaBeanList.size()>0){
            adapter.setList(areaBeanList);
            adapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void initViews() {
        setCustomTitle("分类选择");
        showOrGoneSearchRl(View.GONE);
        initPresenter();
        adapter = new AreaChAdapter(this);
        area_gv.setAdapter(adapter);
    }

    private void initPresenter() {
        mAreaPresentetImpl.setNewsTypeAndId(-1, -1, "",-1);
        mPresenter = mAreaPresentetImpl;
        mPresenter.attachView(this);
        mPresenter.onCreate();
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
