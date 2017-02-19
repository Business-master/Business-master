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
import com.bus.business.mvp.ui.adapter.SzhwAdapter;
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
 */
public class SzhwFragment extends BaseFragment implements BaseQuickAdapter.OnRecyclerViewItemClickListener {


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

    private SzhwAdapter adapter;
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
        TypedArray imgAr = mActivity.getResources().obtainTypedArray(R.array.wan_images);
        TypedArray imgUrl = mActivity.getResources().obtainTypedArray(R.array.wan_urls);
        TypedArray titles = mActivity.getResources().obtainTypedArray(R.array.wan_titles);
        TypedArray details = mActivity.getResources().obtainTypedArray(R.array.wan_details);

        int len = imgUrl.length();
        for (int i = 0; i < len; i++) {
            WanBean wanBean = new WanBean();
            wanBean.setImgSrc(imgAr.getResourceId(i, 0));
            wanBean.setUrl(imgUrl.getString(i));
            wanBean.setTitle(titles.getString(i));
            wanBean.setDetail(details.getString(i));
            wanList.add(wanBean);
        }
        imgAr.recycle();
        imgUrl.recycle();
        titles.recycle();
        details.recycle();

    }

    private void initRecyclerView() {
        //禁用SwipeRefreshLayout下拉刷新
        mSwipeRefreshLayout.setEnabled(false);

        mNewsRV.setHasFixedSize(true);
        mNewsRV.addItemDecoration(new DividerGridItemDecoration(mActivity));
        mNewsRV.setLayoutManager(new GridLayoutManager(mActivity, 1));
        mNewsRV.setItemAnimator(new DefaultItemAnimator());

        adapter = new SzhwAdapter(R.layout.item_szhw2, wanList);
        adapter.setOnRecyclerViewItemClickListener(this);
        mNewsRV.setAdapter(adapter);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(View view, int i) {
        adapter.getData().get(i).intentToWebAct(mActivity);
    }
}
