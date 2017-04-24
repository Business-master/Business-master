package com.ristone.businessasso.mvp.ui.fragment;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ristone.businessasso.R;
import com.ristone.businessasso.mvp.entity.WanBean;
import com.ristone.businessasso.mvp.ui.adapter.SzhwAdapter;
import com.ristone.businessasso.mvp.ui.fragment.base.BaseFragment;
import com.ristone.businessasso.widget.DividerGridItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 山珍海味  碎片
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

    private   int index=1;

    public static SzhwFragment getIntance(int i) {
        SzhwFragment szhwFragment = new SzhwFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index",i);
        szhwFragment.setArguments(bundle);
        return szhwFragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        index = getArguments().getInt("index");
        initDate();
        initRecyclerView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    private void initDate() {
        wanList = new ArrayList<>();
         int [] a ;

        /***
         * inderx 百宝箱 1 山珍海味  2 翡翠玛瑙  3 金融政策
         */
        if (index==1){
            a = new int[]{R.array.wan1_images,R.array.wan1_urls,R.array.wan1_titles,R.array.wan1_details};
        }else if (index==2){
            a = new int[]{R.array.wan2_images,R.array.wan2_urls,R.array.wan2_titles,R.array.wan2_details};
        }else {
            a = new int[]{R.array.policy_images,R.array.policy_urls,R.array.policy_titles,R.array.policy_details};
        }
        TypedArray imgAr = mActivity.getResources().obtainTypedArray(a[0]);
        TypedArray imgUrl = mActivity.getResources().obtainTypedArray(a[1]);
        TypedArray titles = mActivity.getResources().obtainTypedArray(a[2]);
        TypedArray details = mActivity.getResources().obtainTypedArray(a[3]);

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

        if (index==3){
            adapter = new SzhwAdapter(R.layout.item_policy, wanList);
        }else {
            adapter = new SzhwAdapter(R.layout.item_szhw, wanList);
        }

        adapter.setOnRecyclerViewItemClickListener(this);
        mNewsRV.setAdapter(adapter);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(View view, int i) {
        adapter.getData().get(i).intentToWebAct(mActivity);
    }
}
