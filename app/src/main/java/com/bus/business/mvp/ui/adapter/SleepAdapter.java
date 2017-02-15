package com.bus.business.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bus.business.R;
import com.bus.business.mvp.entity.SleepBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ATRSnail on 2017/2/8.
 */

public class SleepAdapter extends BaseQuickAdapter<SleepBean> implements BaseQuickAdapter.OnRecyclerViewItemClickListener{


    public SleepAdapter(int layoutResId, List<SleepBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, SleepBean sleepBean) {
        baseViewHolder.setText(R.id.stayName,sleepBean.getCustonName());

    }




    @Override
    public void onItemClick(View view, int i) {
        ImageView imageView = (ImageView) view.findViewById(R.id.choice);
        imageView.setBackgroundResource(R.mipmap.sleep);

//        for (int j = 0; j <this.getItemCount() ; j++) {
//
//            if (j==i){
//
//            }
//        }

    }
}
