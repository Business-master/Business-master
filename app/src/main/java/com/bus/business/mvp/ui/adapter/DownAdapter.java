package com.bus.business.mvp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bus.business.App;
import com.bus.business.R;
import com.bus.business.common.ApiConstants;
import com.bus.business.mvp.entity.AreaBean;
import com.bus.business.mvp.entity.MeetingFileBean;
import com.bus.business.mvp.entity.WanBean;

import com.bus.business.mvp.ui.activities.AreaActivity;

import com.bus.business.utils.UT;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Url;

/**
 * Created by ATRSnail on 2017/2/16.
 * 会议详情 附件下载适配器
 */

public class DownAdapter extends BaseAdapter{

    Context context ;

    List<MeetingFileBean>  list=new ArrayList<>();



    public DownAdapter(Activity mActivity) {
         this.context = mActivity;
    }

    public List<MeetingFileBean> getList() {
        return list;
    }

    public void setList(List<MeetingFileBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_down,null,false);
            holder.textView = (TextView) convertView.findViewById(R.id.name_fj);
            holder.down_btn = (Button) convertView.findViewById(R.id.down_fj);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        MeetingFileBean meetingFileBean = getList().get(position);
        holder.textView.setText(meetingFileBean.getShowFileName());
        final int meetingFileId= meetingFileBean.getId();
         holder.down_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String url = ApiConstants.NETEAST_HOST+ApiConstants.Meeting_file_download+"?meetingfileId="+meetingFileId;
                 Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                 context.startActivity(intent);
             }
         });
        return convertView;
    }

    class  ViewHolder{
        Button down_btn;
        TextView textView;
    }


}
