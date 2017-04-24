package com.ristone.businessasso.mvp.ui.adapter;

import com.ristone.businessasso.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/27
 */
public class CityAdapter extends BaseQuickAdapter<String>{
    public CityAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.text_place,s);
    }
}
