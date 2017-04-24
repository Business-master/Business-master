package com.ristone.businessasso.mvp.ui.adapter;

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

/**
 *首页--基层组织 新闻.商讯 适配器
 */
public class AreaAdapter extends BaseQuickAdapter<AreaSeaBean> {

    public AreaAdapter(int layoutResId, List<AreaSeaBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, AreaSeaBean likeModel) {
        //将 地区传送给 基层组织
//        if (baseViewHolder.getAdapterPosition()==1){
//            EventBus.getDefault().post(new AreaFirstEvent(likeModel));
//        }


        baseViewHolder.setText(R.id.item_title, likeModel.getTitle());
        baseViewHolder.setText(R.id.item_desc, DateUtil.getCurGroupDay(likeModel.getCtime()));
        baseViewHolder.setText(R.id.item_type, likeModel.getAreaCode());

        TextView textView = baseViewHolder.getView(R.id.type);

        if ("1".equals(likeModel.getType())){
            textView.setVisibility(View.GONE);
        }else {
            textView.setVisibility(View.VISIBLE);
        }

        ImageView img = baseViewHolder.getView(R.id.daimajia_slider_image);

        Glide.with(App.getAppContext()).load(ApiConstants.NETEAST_HOST+likeModel.getFmImg()).asBitmap() // gif格式有时会导致整体图片不显示，貌似有冲突
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.image_place_holder)
                .error(R.drawable.ic_load_fail)
                .into(img);
    }
}
