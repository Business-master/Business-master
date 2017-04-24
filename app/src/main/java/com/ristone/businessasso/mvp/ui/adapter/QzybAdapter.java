package com.ristone.businessasso.mvp.ui.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ristone.businessasso.R;
import com.ristone.businessasso.mvp.entity.WanBean;
import com.ristone.businessasso.mvp.ui.activities.QzybActivity;
import com.ristone.businessasso.utils.UT;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 *奇珍异宝适配器
 */

public class QzybAdapter extends BaseQuickAdapter<WanBean>{
    public QzybAdapter(int layoutResId, List<WanBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final WanBean wanBean) {
         ImageView img = baseViewHolder.getView(R.id.img_wan);
        img.setImageResource(wanBean.getImgSrc());

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("-1".equals(wanBean.getDetail())){
                    UT.show("暂无数据");
                }else {
                    Intent intent = new Intent(mContext, QzybActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("wanBean",wanBean);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }

            }
        });
    }
}
