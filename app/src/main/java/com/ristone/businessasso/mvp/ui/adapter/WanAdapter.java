package com.ristone.businessasso.mvp.ui.adapter;

import android.widget.ImageView;

import com.ristone.businessasso.R;
import com.ristone.businessasso.mvp.entity.WanBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/11
 * 万花筒适配器
 */

public class WanAdapter extends BaseQuickAdapter<WanBean>{
    public WanAdapter(int layoutResId, List<WanBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, WanBean wanBean) {
         ImageView img = baseViewHolder.getView(R.id.img_wan);
        img.setImageResource(wanBean.getImgSrc());
    }
}
