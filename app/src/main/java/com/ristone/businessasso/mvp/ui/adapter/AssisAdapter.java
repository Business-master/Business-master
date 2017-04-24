package com.ristone.businessasso.mvp.ui.adapter;

import com.ristone.businessasso.R;
import com.ristone.businessasso.mvp.entity.AssisBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ATRSnail on 2017/1/19.
 * 助手助理列表适配器
 */

public class AssisAdapter extends BaseQuickAdapter<AssisBean> {
    public AssisAdapter(int layoutResId, List<AssisBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, AssisBean assisBean) {
              baseViewHolder.setText(R.id.tv_phone_assis,assisBean.getPhoneNo());
              baseViewHolder.setText(R.id.tv_phone_name,assisBean.getUserName());
    }
}
