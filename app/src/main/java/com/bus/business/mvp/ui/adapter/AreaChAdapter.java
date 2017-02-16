package com.bus.business.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.mvp.entity.AreaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ATRSnail on 2017/2/16.
 */

public class AreaChAdapter extends BaseAdapter{
    Context context;
    List<AreaBean>  list=new ArrayList<>();

    public AreaChAdapter(Context context) {
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.area_item,null,false);
            holder.textView = (TextView) convertView.findViewById(R.id.area_tv_item);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        AreaBean areaBean = getList().get(position);
        holder.textView.setText(areaBean.getName());
        return convertView;
    }

    class  ViewHolder{
        TextView textView;
    }
}
