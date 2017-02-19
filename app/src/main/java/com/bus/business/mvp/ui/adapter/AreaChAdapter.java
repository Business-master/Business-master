package com.bus.business.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.mvp.entity.AreaBean;
import com.bus.business.mvp.event.AreaCodeEvent;
import com.bus.business.mvp.event.ChangeSearchStateEvent;
import com.bus.business.mvp.ui.activities.AreaActivity;
import com.bus.business.utils.ApplyUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ATRSnail on 2017/2/16.
 */

public class AreaChAdapter extends BaseAdapter{

    List<AreaBean>  list=new ArrayList<>();
    AreaActivity areaActivity;
    boolean isArea;

    public AreaChAdapter(AreaActivity areaActivity, boolean b) {
        this.areaActivity = areaActivity;
        this.isArea = b;
    }

    public List<AreaBean> getList() {
        return list;
    }

    public void setList(List<AreaBean> list) {
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
            convertView = LayoutInflater.from(areaActivity).inflate(R.layout.area_item,null,false);
            holder.textView = (TextView) convertView.findViewById(R.id.area_tv_item);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        AreaBean areaBean = getList().get(position);
        String string = areaBean.getName();

//        if (isArea){
//            int  i = string.indexOf("工商联");
//            holder.textView.setText(string.substring(0,i));
//        }else {
//            holder.textView.setText(string);
//        }


        holder.textView.setText(string);
        holder.textView.setOnClickListener(new MyClick(areaBean,isArea));
        return convertView;
    }

    class  ViewHolder{
        TextView textView;
    }

    class MyClick implements View.OnClickListener{
        AreaBean areaBean;
        boolean isArea;

        public MyClick(AreaBean areaBean, boolean isArea) {
            this.areaBean = areaBean;
            this.isArea = isArea;
        }

        @Override
        public void onClick(View v) {
            EventBus.getDefault().post(new AreaCodeEvent(areaBean,isArea));
            areaActivity.finish();
        }
    }
}
