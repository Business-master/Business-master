package com.bus.business.mvp.ui.fragment;

import android.app.Activity;
import android.content.res.TypedArray;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.mvp.entity.WanBean;
import com.bus.business.mvp.ui.adapter.WanAdapter;
import com.bus.business.mvp.ui.fragment.base.BaseFragment;
import com.bus.business.mvp.view.CustomRecyclerView;
import com.bus.business.utils.UT;
import com.bus.business.widget.DividerGridItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 奇珍异宝碎片
 */
public class QzybFragment extends BaseFragment  {


    @BindView(R.id.hanming)
    View hanming;
    @BindView(R.id.youqi)
    View youqi;
    @BindView(R.id.all_qzyb)
    View all_qzyb;

    @BindView(R.id.scroll_qzyb)
    ScrollView scroll_qzyb;

     TextView textView1,textView2,textView3;

    CustomRecyclerView recyclerView1,recyclerView2,recyclerView3;

     @Inject
    Activity mActivity;




    @Override
    public int getLayoutId() {
        return R.layout.qzyb;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        initview();
    }

    private void initview() {
        scroll_qzyb.setFocusable(true);
        scroll_qzyb.setFocusableInTouchMode(true);
        scroll_qzyb.requestFocus();

        initCustomRecyclerView(textView1,recyclerView1,hanming,"汉铭",initData(R.array.hm_images,R.array.hm_urls));
        initCustomRecyclerView(textView2,recyclerView2,youqi,"用友", initData(R.array.yy_images,R.array.yy_urls));
        initCustomRecyclerView(textView3,recyclerView3,all_qzyb,"全部", initData(R.array.actions_images,R.array.actions_urls));

    }

    private void initCustomRecyclerView(TextView textView, CustomRecyclerView recyclerView, View view, String str, List<WanBean> wanList) {
                textView = (TextView) view.findViewById(R.id.title_custom);
                textView.setText(str);
                recyclerView= (CustomRecyclerView) view.findViewById(R.id.details_custom);
                initRecyclerView(recyclerView,wanList);

    }

    private void initRecyclerView(CustomRecyclerView recyclerView, List<WanBean> wanList) {


        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(mActivity));
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        WanAdapter   adapter = new WanAdapter(R.layout.item_wans, wanList);
        recyclerView.setAdapter(adapter);
    }


    private List<WanBean> initData(int images,int urls) {
        List<WanBean> wanList=new ArrayList<>();
        TypedArray imgAr = mActivity.getResources().obtainTypedArray(images);
        TypedArray imgUrl = mActivity.getResources().obtainTypedArray(urls);

        int len = imgAr.length();
        for (int i = 0; i < len; i++) {
            WanBean wanBean = new WanBean();
            wanBean.setImgSrc(imgAr.getResourceId(i, 0));
            wanBean.setUrl(imgUrl.getString(i));
            wanList.add(wanBean);
        }
        imgAr.recycle();
        imgUrl.recycle();
          return wanList;
    }



}
