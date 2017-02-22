package com.bus.business.mvp.ui.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.mvp.entity.MeetingBean;
import com.bus.business.mvp.entity.response.base.BaseRspObj;
import com.bus.business.mvp.ui.activities.ApplyActivity;
import com.bus.business.repository.network.RetrofitManager;
import com.bus.business.utils.ApplyUtils;
import com.bus.business.utils.DateUtil;
import com.bus.business.utils.TransformUtils;
import com.bus.business.utils.UT;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

import butterknife.OnClick;
import rx.Subscriber;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/24
 */
public class MeetingsAdapter extends BaseQuickAdapter<MeetingBean> {
    private String stateStr;

   private  Button cancel_apply;
   private   Button btn_apply;
   private   Button   btn_leave;



    public MeetingsAdapter(int layoutResId, List<MeetingBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final MeetingBean likeBean) {


        baseViewHolder.setText(R.id.tv_name, likeBean.getMeetingName());
//        baseViewHolder.setText(R.id.tv_address, "北京工商联");
        baseViewHolder.setText(R.id.tv_address, likeBean.getMeetingLoc());
        baseViewHolder.setText(R.id.tv_date, DateUtil.getCurGroupDay(likeBean.getMeetingTime()));

        TextView new_meeting = baseViewHolder.getView(R.id.new_meeting);
         if (likeBean.getHasReaded()==1){
             new_meeting.setVisibility(View.VISIBLE);
         }else {
             new_meeting.setVisibility(View.GONE);
         }


        Button jointype_tv = baseViewHolder.getView(R.id.jointype_tv);
        ApplyUtils.MyClick myClick = new ApplyUtils.MyClick(likeBean,mContext);

         cancel_apply = baseViewHolder.getView(R.id.cancel_apply);
         btn_apply = baseViewHolder.getView(R.id.btn_apply);
         btn_leave = baseViewHolder.getView(R.id.btn_leave);
         cancel_apply.setOnClickListener(myClick);
         btn_apply.setOnClickListener(myClick);
         btn_leave.setOnClickListener(myClick);
        String str =   ApplyUtils.getInstance().initState_Hide(cancel_apply,btn_apply,btn_leave,likeBean.getJoinType());
        jointype_tv.setText(str);

        int status = Integer.valueOf(likeBean.getStatus());
      if (status!=0){
            cancel_apply.setVisibility(View.INVISIBLE);
            btn_apply.setVisibility(View.INVISIBLE);
            btn_leave.setVisibility(View.INVISIBLE);
        }




//        TextView addText = baseViewHolder.getView(R.id.img_add);
//        if (likeBean.getCheckType()){
//            stateStr = "已参会";
//        }else {
//            stateStr = likeBean.getJoinType()?"已报名":"报名";
//        }
//        addText.setText(stateStr);
//        addText.setBackgroundResource(likeBean.getJoinType() ? R.drawable.grey_circle_5 : R.drawable.blue_circle_5);
//        addText.setOnClickListener(new addClickListener(addText, likeBean));
    }







//    class addClickListener implements View.OnClickListener {
//        private TextView tv;
//        private MeetingBean meetingBean;
//
//        public addClickListener(TextView tv, MeetingBean likeBean) {
//            this.tv = tv;
//            this.meetingBean = likeBean;
//        }
//
//        @Override
//        public void onClick(View view) {
//            if (meetingBean.getJoinType()){
//                UT.show("已报名");
//                return;
//            }
//
//            RetrofitManager.getInstance(1).joinMeeting(meetingBean.getId())
//                    .compose(TransformUtils.<BaseRspObj>defaultSchedulers())
//                    .subscribe(new Subscriber<BaseRspObj>() {
//                        @Override
//                        public void onCompleted() {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onNext(BaseRspObj responseBody) {
//                            if (responseBody.getHead().getRspCode().equals("0")) {
//                                tv.setBackgroundResource(R.drawable.grey_circle_5);
//                                tv.setText("已报名");
//                                meetingBean.setJoinType(true);
//                            }
//                            UT.show(responseBody.getHead().getRspMsg());
//
//                        }
//                    });
//        }
//    }
}
