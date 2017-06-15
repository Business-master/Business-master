package com.ristone.businessasso.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ristone.businessasso.App;
import com.ristone.businessasso.R;
import com.ristone.businessasso.common.ApiConstants;
import com.ristone.businessasso.mvp.entity.XinWenBean;
import com.ristone.businessasso.utils.DateUtil;

import java.util.List;

/**
 * Created by xuchunhui on 16/8/12.
 */
public class PolicyAdapter extends BaseQuickAdapter<XinWenBean> {

    public PolicyAdapter(int layoutResId, List<XinWenBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, XinWenBean likeModel) {

        baseViewHolder.setText(R.id.item_title, likeModel.getTitle());
        baseViewHolder.setText(R.id.item_desc, DateUtil.getCurGroupDay(likeModel.getCtime()));
        baseViewHolder.setText(R.id.item_type, likeModel.getAreaCode());

        ImageView img = baseViewHolder.getView(R.id.daimajia_slider_image);

        Glide.with(App.getAppContext()).load(ApiConstants.NETEAST_HOST+likeModel.getFmImg()).asBitmap() // gif格式有时会导致整体图片不显示，貌似有冲突
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.image_place_holder)
                .error(R.drawable.ic_load_fail)
                .into(img);
    }
}
