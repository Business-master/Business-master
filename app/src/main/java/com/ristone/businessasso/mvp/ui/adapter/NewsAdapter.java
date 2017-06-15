package com.ristone.businessasso.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ristone.businessasso.App;
import com.ristone.businessasso.R;
import com.ristone.businessasso.common.ApiConstants;
import com.ristone.businessasso.common.Constants;
import com.ristone.businessasso.mvp.entity.XinWenBean;

import com.ristone.businessasso.utils.DateUtil;
import com.socks.library.KLog;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 新闻适配器（含有视频）
 */
public class NewsAdapter extends BaseQuickAdapter<XinWenBean> {

    public NewsAdapter(int layoutResId, List<XinWenBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, XinWenBean likeModel) {
        View newView = baseViewHolder.getView(R.id.new_item);//正常文字新闻
        View videoView = baseViewHolder.getView(R.id.video_item);//视频新闻



        newView.setVisibility(View.GONE);
        videoView.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(likeModel.getTypes())){
            if (!Constants.video_new.equals(likeModel.getTypes())){
                newView.setVisibility(View.VISIBLE);
                initNewView(newView,likeModel);
            }else {
                videoView.setVisibility(View.VISIBLE);
                initVideoView(videoView,likeModel,baseViewHolder);
            }
        }


    }

    private void initVideoView(View videoView, XinWenBean likeModel, BaseViewHolder baseViewHolder) {

        JCVideoPlayerStandard video = (JCVideoPlayerStandard) videoView.findViewById(R.id.videoplayer);



        video.time.setText( DateUtil.getCurGroupDay(likeModel.getCtime()));
        video.area.setText(likeModel.getAreaCode());

        video.setUp(ApiConstants.NETEAST_HOST+likeModel.getVideoUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST,likeModel.getTitle());
        video.titleTextView.setMaxLines(2);
        video.titleTextView.setEllipsize(TextUtils.TruncateAt.END);
        video.thumbImageView.setAdjustViewBounds(true);
        video.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        video.setIndex(baseViewHolder.getAdapterPosition());

        Glide.with(App.getAppContext()).load(ApiConstants.NETEAST_HOST+likeModel.getFmImg()).asBitmap() // gif格式有时会导致整体图片不显示，貌似有冲突
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.image_place_holder)
                .error(R.drawable.ic_load_fail)
                .into(video.thumbImageView);



    }

    private void initNewView(View newView, XinWenBean likeModel) {
        TextView title = (TextView) newView.findViewById(R.id.item_title);
        TextView time = (TextView) newView.findViewById(R.id.item_desc);
        TextView area = (TextView) newView.findViewById(R.id.item_type);
        title.setText(likeModel.getTitle());
        time.setText( DateUtil.getCurGroupDay(likeModel.getCtime()));
        area.setText(likeModel.getAreaCode());

        ImageView img = (ImageView) newView.findViewById(R.id.daimajia_slider_image);

        Glide.with(App.getAppContext()).load(ApiConstants.NETEAST_HOST+likeModel.getFmImg()).asBitmap() // gif格式有时会导致整体图片不显示，貌似有冲突
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.image_place_holder)
                .error(R.drawable.ic_load_fail)
                .into(img);
    }
}
