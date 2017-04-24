package com.ristone.businessasso.mvp.ui.activities;

import android.view.View;

import com.ristone.businessasso.R;
import com.ristone.businessasso.common.LoadNewsType;
import com.ristone.businessasso.mvp.entity.AreaBean;
import com.ristone.businessasso.mvp.presenter.impl.AreaPresentetImpl;
import com.ristone.businessasso.mvp.ui.activities.base.BaseActivity;
import com.ristone.businessasso.mvp.ui.adapter.AreaChAdapter;
import com.ristone.businessasso.mvp.view.AreaView;
import com.ristone.businessasso.mvp.view.CustomGridView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 选择切换地区的页面
 */
public class AreaActivity extends BaseActivity implements AreaView{

    @BindView(R.id.area_gv)
    CustomGridView area_gv;
    @BindView(R.id.orgina_gv)
    CustomGridView orgina_gv;

    private AreaChAdapter adapter;
    private AreaChAdapter adapter2;

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
        if (areaBeanList!=null&&areaBeanList.size()>0){
            adapter.setList(areaBeanList);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void setChambreBeanList(List<AreaBean> chambrelist, @LoadNewsType.checker int loadType) {
        if (chambrelist!=null&&chambrelist.size()>0){
            adapter2.setList(chambrelist);
            adapter2.notifyDataSetChanged();
        }
    }

    @Override
    public void initViews() {
        setCustomTitle("分类选择");
        showOrGoneSearchRl(View.GONE);
        initPresenter();
        adapter = new AreaChAdapter(AreaActivity.this,true);
        adapter2 = new AreaChAdapter(AreaActivity.this,false);
        area_gv.setAdapter(adapter);
        orgina_gv.setAdapter(adapter2);

    }


    private void initPresenter() {
        mAreaPresentetImpl.setNewsTypeAndId(-1, -1, "",-1);
        mPresenter = mAreaPresentetImpl;
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDestory();
        super.onDestroy();
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
