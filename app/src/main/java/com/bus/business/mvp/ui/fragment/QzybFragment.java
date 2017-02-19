package com.bus.business.mvp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.mvp.entity.WanBean;
import com.bus.business.mvp.ui.adapter.CustomAdapter;
import com.bus.business.mvp.ui.adapter.WanAdapter;
import com.bus.business.mvp.ui.fragment.base.BaseFragment;
import com.bus.business.mvp.view.CustomGridView;
import com.bus.business.mvp.view.CustomListView;
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


//    @BindView(R.id.hanming)
//    View hanming;

    @BindView(R.id.scroll_qzyb)
    ScrollView scroll_qzyb;
    @BindView(R.id.title_custom)
     TextView textView1;
    @BindView(R.id.details_qzub)
     CustomGridView customGridView1;

     @Inject
    Activity mActivity;

    private CustomAdapter2 adapter;
    private List<WanBean> wanList;

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
        initDate();
        initview();
    }

    private void initview() {
        scroll_qzyb.setFocusable(true);
        scroll_qzyb.setFocusableInTouchMode(true);
        scroll_qzyb.requestFocus();

//        textView1 = (TextView) hanming.findViewById(R.id.title_custom);
        textView1.setText("汉铭");
//        customGridView1 = (CustomGridView) hanming.findViewById(R.id.details_custom);
        adapter = new CustomAdapter2(mActivity);
        adapter.setList(wanList);
        customGridView1.setAdapter(adapter);
    }



    private void initDate() {
        wanList = new ArrayList<>();
//        TypedArray imgAr = mActivity.getResources().obtainTypedArray(R.array.actions_images);
//        TypedArray imgUrl = mActivity.getResources().obtainTypedArray(R.array.actions_urls);
//
//        int len = imgAr.length();
        for (int i = 0; i < 5; i++) {
            WanBean wanBean = new WanBean();
//            wanBean.setImgSrc(imgAr.getResourceId(i, 0));
//            wanBean.setUrl(imgUrl.getString(i));
            wanBean.setUrl("名字"+i);
            wanList.add(wanBean);
        }
//        imgAr.recycle();
//        imgUrl.recycle();

    }





//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        CustomAdapter customAdapter= (CustomAdapter) parent.getAdapter();
//        WanBean wab= customAdapter.getList().get(position);
//        UT.show(""+wab.getUrl());
//    }

    public class CustomAdapter2 extends BaseAdapter {

        Context context ;

        List<WanBean>  list=new ArrayList<>();



        public CustomAdapter2(Context context) {
            this.context = context;
        }

        public List<WanBean> getList() {
            return list;
        }

        public void setList(List<WanBean> list) {
            this.list.clear();
            this.list.addAll(list);
        }

        @Override
        public int getCount() {
            return getList().size()>0?getList().size():0;
        }

        @Override
        public Object getItem(int position) {
            return getList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            if (convertView==null){
                holder=new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_qzyb,null,false);
                holder.textView = (TextView) convertView.findViewById(R.id.name_qzyb);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            WanBean wanBean = getList().get(position);
            holder.textView.setText(wanBean.getUrl());
            return convertView;
        }

        class  ViewHolder{
            TextView textView;
        }


    }

}
