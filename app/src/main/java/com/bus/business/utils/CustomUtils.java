package com.bus.business.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bus.business.R;

/**
 * Created by ATRSnail on 2017/3/31.
 * 常用的  公共类
 */

public class CustomUtils {
    Context context;


    public CustomUtils(Context context) {
        this.context = context;
    }

    public  void showNoMore(final RecyclerView mNewsRV) {
        mNewsRV.setOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState==RecyclerView.SCROLL_STATE_IDLE){
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    int totalItemCount=linearLayoutManager.getItemCount();
                    if (lastVisibleItemPosition==(totalItemCount-1)&&isSlidingToLast){

                            Snackbar.make(mNewsRV, context.getString(R.string.no_more), Snackbar.LENGTH_SHORT).show();


                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy>0){
                    isSlidingToLast=true;
                }else {
                    isSlidingToLast=false;
                }
            }
        });
    }
}
