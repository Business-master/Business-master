package com.ristone.businessasso.mvp.ui.activities;

import android.app.Activity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ristone.businessasso.R;
import com.ristone.businessasso.mvp.ui.activities.base.BaseActivity;
import com.ristone.businessasso.mvp.ui.adapter.CityAdapter;
import com.ristone.businessasso.widget.RecyclerViewDivider;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 地区页面
 */
public class PlaceActivity extends BaseActivity {

    @BindView(R.id.city_list)
    RecyclerView mRecycle;

    private CityAdapter cityAdapter;
    private List<String> cityList = new ArrayList<>();
    private String[] cityArray;


    @Inject
    Activity mActivity;
    @Override
    public int getLayoutId() {
        return R.layout.activity_place;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        setCustomTitle("地区");
        showOrGoneSearchRl(View.GONE);

        initData();
        initRecyclerView();

    }

    private void initRecyclerView() {
        cityAdapter = new CityAdapter(R.layout.item_city, cityList);
        mRecycle.setHasFixedSize(true);
        mRecycle.addItemDecoration(new RecyclerViewDivider(mActivity,
                LinearLayoutManager.VERTICAL, 2, getResources().getColor(R.color.red)));
        mRecycle.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        mRecycle.setItemAnimator(new DefaultItemAnimator());
        mRecycle.setAdapter(cityAdapter);
    }

    private void initData() {
        cityArray = getResources().getStringArray(R.array.city_list);
        KLog.a(cityArray);

        for(String city : cityArray) {
           cityList.add(city);
        }
        System.out.println(cityList.toString());
    }

}
