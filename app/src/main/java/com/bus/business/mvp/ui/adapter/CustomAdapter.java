package com.bus.business.mvp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bus.business.App;
import com.bus.business.R;
import com.bus.business.mvp.entity.AreaBean;
import com.bus.business.mvp.entity.WanBean;
import com.bus.business.mvp.event.AreaCodeEvent;
import com.bus.business.mvp.ui.activities.AreaActivity;
import com.bus.business.utils.ApplyUtils;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ATRSnail on 2017/2/16.
 */

public class CustomAdapter extends BaseAdapter{

    Context context ;

    List<WanBean>  list=new ArrayList<>();



    public CustomAdapter(Activity mActivity) {
         this.context = mActivity;
    }

    public List<WanBean> getList() {
        return list;
    }

    public void setList(List<WanBean> list) {
        this.list.clear();
        this.list.addAll(list);
    }

    @Override
    public int getCount() {
        return getList().size()>0?getList().size():0;
    }

    @Override
    public Object getItem(int position) {
        return getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_qzyb,null,false);
//            holder.textView = (TextView) convertView.findViewById(R.id.name_qzyb);
            holder.imageView = (ImageView) convertView.findViewById(R.id.img_qzyb);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        WanBean wanBean = getList().get(position);
//        holder.textView.setText(wanBean.getUrl());
        holder.imageView.setImageResource(wanBean.getImgSrc());
        return convertView;
    }

    class  ViewHolder{
        ImageView imageView;
//        TextView textView;
    }


}
