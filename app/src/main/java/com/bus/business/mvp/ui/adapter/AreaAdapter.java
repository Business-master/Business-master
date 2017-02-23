package com.bus.business.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bus.business.App;
import com.bus.business.R;
import com.bus.business.common.ApiConstants;
import com.bus.business.mvp.entity.AreaSeaBean;
import com.bus.business.mvp.entity.response.base.BaseNewBean;
import com.bus.business.mvp.event.AreaFirstEvent;
import com.bus.business.utils.DateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by xuchunhui on 16/8/12.
 */
public class AreaAdapter extends BaseQuickAdapter<AreaSeaBean> {

    public AreaAdapter(int layoutResId, List<AreaSeaBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, AreaSeaBean likeModel) {
        if (baseViewHolder.getAdapterPosition()==1){
            EventBus.getDefault().post(new AreaFirstEvent(likeModel));
        }


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
