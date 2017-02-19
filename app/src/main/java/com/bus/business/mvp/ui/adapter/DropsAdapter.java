package com.bus.business.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bus.business.App;
import com.bus.business.R;
import com.bus.business.common.ApiConstants;
import com.bus.business.mvp.entity.DropBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by xuchunhui on 16/8/12.
 */
public class DropsAdapter extends BaseQuickAdapter<DropBean> {

    public DropsAdapter(int layoutResId, List<DropBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, DropBean likeModel) {

        baseViewHolder.setText(R.id.item_title, likeModel.getBankName());
        baseViewHolder.setText(R.id.tv_type,likeModel.getPledgeCode());
        baseViewHolder.setText(R.id.tv_lilv,likeModel.getCashRate()+"%");
        baseViewHolder.setText(R.id.address, "贷款简介 : "+likeModel.getLoanIntroduction());

        ImageView img = baseViewHolder.getView(R.id.daimajia_slider_image);

        Glide.with(App.getAppContext()).load(ApiConstants.NETEAST_HOST+likeModel.getLogoUrl()).asBitmap() // gif格式有时会导致整体图片不显示，貌似有冲突
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.image_place_holder)
                .error(R.drawable.ic_load_fail)
                .into(img);
    }
}
