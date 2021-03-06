package com.ristone.businessasso.mvp.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.ristone.businessasso.R;
import com.ristone.businessasso.mvp.entity.WanBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 *山珍海味适配器
 */

public class SzhwAdapter extends BaseQuickAdapter<WanBean>{
    public SzhwAdapter(int layoutResId, List<WanBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, WanBean wanBean) {
         ImageView img = baseViewHolder.getView(R.id.img_szhw2);
         img.setImageResource(wanBean.getImgSrc());
        TextView title = baseViewHolder.getView(R.id.title_szhw2);
         title.setText(wanBean.getTitle());
        TextView detail = baseViewHolder.getView(R.id.details_szhw2);
        detail.setText(wanBean.getDetail());
    }
}
