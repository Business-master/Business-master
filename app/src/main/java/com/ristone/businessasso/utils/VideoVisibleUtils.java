package com.ristone.businessasso.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.socks.library.KLog;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by ATRSnail on 2017/3/31.
 * 视频新闻是否可见 的  公共类
 */

public class VideoVisibleUtils {
    Context context;
    int postion = Integer.MAX_VALUE;//视频新闻的位置


    public VideoVisibleUtils(Context context) {
        this.context = context;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public void judgeVisible(final RecyclerView mNewsRV) {

        mNewsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
//                    KLog.a("第一个可见Item的po" + firstVisibleItemPosition + "--------新闻位置" + postion);

                    if (postion < firstVisibleItemPosition &&isSlidingToLast) {
                        JCVideoPlayer.releaseAllVideos();
                    }

                    if (postion> lastVisibleItemPosition &&!isSlidingToLast) {
                        JCVideoPlayer.releaseAllVideos();
                    }

                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    isSlidingToLast = true;
                } else {
                    isSlidingToLast = false;
                }
            }
        });
    }
}
