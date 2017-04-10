package com.bus.business.mvp.ui.fragment;

import android.app.Activity;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.mvp.entity.WanBean;
import com.bus.business.mvp.ui.adapter.WanAdapter;
import com.bus.business.mvp.ui.fragment.base.BaseFragment;
import com.bus.business.widget.DividerGridItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/6
 * 万花筒
 */
public class WanFragment extends BaseFragment implements BaseQuickAdapter.OnRecyclerViewItemClickListener {


    @BindView(R.id.news_rv)
    RecyclerView mNewsRV;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.empty_view)
    TextView mEmptyView;

    @Inject
    Activity mActivity;

    private WanAdapter wanAdapter;
    private List<WanBean> wanList;

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        initDate();
        initRecyclerView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    private void initDate() {
        wanList = new ArrayList<>();
        TypedArray imgAr = mActivity.getResources().obtainTypedArray(R.array.actions_images);
        TypedArray imgUrl = mActivity.getResources().obtainTypedArray(R.array.actions_urls);

        int len = imgAr.length();
        for (int i = 0; i < len; i++) {
            WanBean wanBean = new WanBean();
            wanBean.setImgSrc(imgAr.getResourceId(i, 0));
            wanBean.setUrl(imgUrl.getString(i));
            wanList.add(wanBean);
        }
        imgAr.recycle();
        imgUrl.recycle();

    }

    private void initRecyclerView() {
        //禁用SwipeRefreshLayout下拉刷新
        mSwipeRefreshLayout.setEnabled(false);

        mNewsRV.setHasFixedSize(true);
        mNewsRV.addItemDecoration(new DividerGridItemDecoration(mActivity));
        mNewsRV.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mNewsRV.setItemAnimator(new DefaultItemAnimator());

        wanAdapter = new WanAdapter(R.layout.item_wans, wanList);
        wanAdapter.setOnRecyclerViewItemClickListener(this);
        mNewsRV.setAdapter(wanAdapter);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(View view, int i) {
        wanAdapter.getData().get(i).intentToWebAct(mActivity);
    }
}
