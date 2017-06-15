package com.ristone.businessasso.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ristone.businessasso.App;
import com.ristone.businessasso.R;
import com.ristone.businessasso.common.ApiConstants;
import com.ristone.businessasso.mvp.entity.AreaSeaBean;
import com.ristone.businessasso.utils.DateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 *首页--基层组织 新闻.商讯 适配器
 */
public class AreaAdapter extends BaseQuickAdapter<AreaSeaBean> {

    public AreaAdapter(int layoutResId, List<AreaSeaBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, AreaSeaBean likeModel) {

        View newView = baseViewHolder.getView(R.id.new_item);//正常文字新闻
        View videoView = baseViewHolder.getView(R.id.video_item);//视频新闻


        newView.setVisibility(View.GONE);
        videoView.setVisibility(View.GONE);
        //"type":  //2：新闻；1：商讯 5:视频

        String type = likeModel.getType();

        if (!TextUtils.isEmpty(type)) {
            if ("5".equals(type)) {
                videoView.setVisibility(View.VISIBLE);
                initVideoView(videoView, likeModel, baseViewHolder);

            } else {
                newView.setVisibility(View.VISIBLE);
                TextView shangxun = (TextView) newView.findViewById(R.id.type);

                if ("2".equals(likeModel.getType())) {
                    shangxun.setVisibility(View.GONE);
                } else {
                    shangxun.setVisibility(View.VISIBLE);
                }

                initNewView(newView, likeModel);
            }
        }
    }




    private void initVideoView(View videoView, AreaSeaBean likeModel, BaseViewHolder baseViewHolder) {

        JCVideoPlayerStandard video = (JCVideoPlayerStandard) videoView.findViewById(R.id.videoplayer);



        video.time.setText( DateUtil.getCurGroupDay(likeModel.getCtime()));
        video.area.setText(likeModel.getAreaCode());

        String url = likeModel.getVideoUrl();

        if (url!=null){
            video.setUp(ApiConstants.NETEAST_HOST+url, JCVideoPlayer.SCREEN_LAYOUT_LIST,likeModel.getTitle());
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
    }

    private void initNewView(View newView, AreaSeaBean likeModel) {
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
