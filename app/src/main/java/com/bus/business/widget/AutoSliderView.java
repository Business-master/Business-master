package com.bus.business.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.mvp.entity.BannerBean;
import com.bus.business.utils.DateUtil;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/8/29
 */
public class AutoSliderView extends BaseSliderView {

    private BannerBean pageIconBean;
    public AutoSliderView(Context context,BannerBean pageIconBean) {
        super(context);
        this.pageIconBean = pageIconBean;
    }

    @Override
    public View getView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_new_item, null);
        TextView title = (TextView) view.findViewById(R.id.item_title);
        ImageView imageView = (ImageView) view.findViewById(R.id.daimajia_slider_image);
        TextView item_type = (TextView) view.findViewById(R.id.item_type);
        TextView item_desc = (TextView) view.findViewById(R.id.item_desc);
        ImageView img_vip = (ImageView) view.findViewById(R.id.item_vip_logo);
//        img_vip.setVisibility(pageIconBean.getIsvip().equals("0") ? View.VISIBLE : View.GONE);
//        title.setText(pageIconBean.getVfName());
        title.setText(pageIconBean.getTitle());
        item_type.setText("工商联");
        item_desc.setText(DateUtil.getCurGroupDay(pageIconBean.getCtime()));
        empty(R.mipmap.default_image);
        bindEventAndShow(view, imageView);
        return view;
    }
}
