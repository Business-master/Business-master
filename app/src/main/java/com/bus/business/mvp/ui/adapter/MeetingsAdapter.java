package com.bus.business.mvp.ui.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bus.business.App;
import com.bus.business.R;
import com.bus.business.common.UsrMgr;
import com.bus.business.listener.JoinMeetingCallBack;
import com.bus.business.mvp.entity.MeetingBean;
import com.bus.business.mvp.entity.UserBean;
import com.bus.business.mvp.entity.response.base.BaseRspObj;
import com.bus.business.mvp.event.JoinToMeetingEvent;
import com.bus.business.mvp.ui.activities.ApplyActivity;
import com.bus.business.repository.network.RetrofitManager;

import com.bus.business.utils.DateUtil;
import com.bus.business.utils.DensityUtil;
import com.bus.business.utils.TransformUtils;
import com.bus.business.utils.UT;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

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


   private  Button cancel_apply;
   private   Button btn_apply;
   private   Button   btn_leave;



    public JoinMeetingCallBack joinMeetingCallBack;

    public void setJoinMeetingCallBack(JoinMeetingCallBack joinMeetingCallBack) {
        this.joinMeetingCallBack = joinMeetingCallBack;
    }

    public MeetingsAdapter(int layoutResId, List<MeetingBean> data) {
        super(layoutResId, data);

    }




    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final MeetingBean likeBean) {
        int status = Integer.valueOf(likeBean.getStatus());

        View view0 = baseViewHolder.getView(R.id.item_0);
        View view1 = baseViewHolder.getView(R.id.item_1);
        View view2= baseViewHolder.getView(R.id.item_2);
        view0.setVisibility(View.GONE);
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
        switch (status){
            case 0 :
                view0.setVisibility(View.VISIBLE);
                initView(view0,likeBean);
                init0(view0,likeBean);
                break;
            case 1 :
                view1.setVisibility(View.VISIBLE);
                initView(view1,likeBean);
                init1(view1,likeBean);
                break;
            case 2:
                view2.setVisibility(View.VISIBLE);
                initView(view2,likeBean);
                init2(view2,likeBean);
                break;
        }





//  TextView new_meeting = baseViewHolder.getView(R.id.new_meeting);
//        if (likeBean.getHasReaded()==1){
//            new_meeting.setVisibility(View.VISIBLE);
//        }else {
//            new_meeting.setVisibility(View.GONE);
//        }
//
//        Button jointype_tv = baseViewHolder.getView(R.id.jointype_tv);
//        ApplyUtils.MyClick myClick = new ApplyUtils.MyClick(likeBean,mContext);
//
//         cancel_apply = baseViewHolder.getView(R.id.cancel_apply);
//         btn_apply = baseViewHolder.getView(R.id.btn_apply);
//         btn_leave = baseViewHolder.getView(R.id.btn_leave);
//         cancel_apply.setOnClickListener(myClick);
//         btn_apply.setOnClickListener(myClick);
//         btn_leave.setOnClickListener(myClick);
//        String str =   ApplyUtils.getInstance().initState_Hide(cancel_apply,btn_apply,btn_leave,likeBean.getJoinType());
//        jointype_tv.setText(str);
//
//        int status = Integer.valueOf(likeBean.getStatus());
//      if (status!=0){
//            cancel_apply.setVisibility(View.INVISIBLE);
//            btn_apply.setVisibility(View.INVISIBLE);
//            btn_leave.setVisibility(View.INVISIBLE);
//        }
    }

    private void initView(View view, MeetingBean likeBean) {
        TextView name = (TextView) view.findViewById(R.id.tv_name);
        TextView address = (TextView) view.findViewById(R.id.tv_address);
        TextView date = (TextView) view.findViewById(R.id.tv_date);
        name.setText(likeBean.getMeetingName());
        address.setText(likeBean.getUserOrganization());
        date.setText(DateUtil.getCurGroupDay(likeBean.getMeetingTime()));
    }


    private void init2(View view2, MeetingBean likeBean) {
        String stateStr="";
        TextView state = (TextView) view2.findViewById(R.id.tv_state);
        int draw=R.drawable.join_banover;
        switch (likeBean.getJoinType()){
            case 0:
                stateStr="未报名";
                draw=R.drawable.absent_banover;
                break;
            case 1:
            case 3:
                stateStr="已报名";
                draw=R.drawable.join_banover;
                break;
            case 2:
                stateStr="已签到";
                draw=R.drawable.join_banover;
                break;
            case 4:
                stateStr="助理参加";
                draw=R.drawable.join_banover;
                break;
            case 5:
                stateStr="已请假";
                draw=R.drawable.leave_banover;
                break;
            case 6:
                stateStr="已取消报名";
                draw=R.drawable.absent_banover;
                break;
            case 7:
                stateStr="缺席";
                draw=R.drawable.absent_banover;
                break;
            case 8:
                stateStr="已过期";
                draw=R.drawable.absent_banover;
                break;
            case 9:
                stateStr="助理签到";
                draw=R.drawable.join_banover;
                break;
        }
        state.setText(stateStr);
        state.setBackgroundResource(draw);
    }

    private void init1(View view1, MeetingBean likeBean) {
        String stateStr="";
        TextView state = (TextView) view1.findViewById(R.id.tv_state);
        int color=mContext.getResources().getColor(R.color.color_0dadd5);
        switch (likeBean.getJoinType()){
            case 0:
                stateStr="未报名";
                color=mContext.getResources().getColor(R.color.color_cccccc);
                break;
            case 1:
            case 3:
                stateStr="已报名";
                color=mContext.getResources().getColor(R.color.color_0dadd5);
                break;
            case 2:
                stateStr="已签到";
                color=mContext.getResources().getColor(R.color.color_0dadd5);
                break;
            case 4:
                stateStr="助理参加";
                color=mContext.getResources().getColor(R.color.color_0dadd5);
                break;
            case 5:
                stateStr="已请假";
                color=mContext.getResources().getColor(R.color.color_fdae73);
                break;
            case 6:
                stateStr="已取消报名";
                color=mContext.getResources().getColor(R.color.color_cccccc);
                break;
            case 7:
                stateStr="缺席";
                color=mContext.getResources().getColor(R.color.color_cccccc);
                break;
            case 8:
                stateStr="已过期";
                color=mContext.getResources().getColor(R.color.color_cccccc);
                break;
            case 9:
                stateStr="助理签到";
                color=mContext.getResources().getColor(R.color.color_0dadd5);
                break;
        }
        state.setText(stateStr);
        state.setTextColor(color);
    }




    private void init0(View view0, MeetingBean likeBean) {
        TextView state = (TextView) view0.findViewById(R.id.tv_name);
        TextView date = (TextView) view0.findViewById(R.id.tv_date);
        TextView address = (TextView) view0.findViewById(R.id.tv_address);
        TextView leave = (TextView) view0.findViewById(R.id.leave);
        TextView apply = (TextView) view0.findViewById(R.id.apply);
        if (likeBean.getHasReaded()==1){
            Drawable dra = mContext.getResources().getDrawable(R.drawable.blue_circle);
            dra.setBounds(0,0,dra.getMinimumWidth(),dra.getMinimumHeight());
            state.setCompoundDrawables(dra,null,null,null);
            state.setCompoundDrawablePadding(10);
        }else {
            state.setCompoundDrawables(null,null,null,null);
            state.setCompoundDrawablePadding(0);
        }
        apply.setVisibility(View.VISIBLE);
        if (likeBean.getJoinType()==0){
            apply.setText("报名");
            apply.setTextColor(Color.parseColor("#0dadd5"));
            apply.setBackgroundResource(R.drawable.apply_rectange);

            leave.setText("请假");
            leave.setTextColor(Color.parseColor("#fdae73"));
            leave.setBackgroundResource(R.drawable.leaven_rectange);
        }else if (likeBean.getJoinType()==5){
            leave.setText("已请假");
            leave.setTextColor(Color.parseColor("#fdae73"));
            leave.setBackgroundResource(R.drawable.leaven_rectange);

            apply.setVisibility(View.INVISIBLE);
        }else if (likeBean.getJoinType()==2){
            leave.setText("已签到");
            leave.setTextColor(Color.parseColor("#4DC056"));
            leave.setBackgroundResource(R.drawable.sign_rectange);

            apply.setVisibility(View.INVISIBLE);
        }else if (likeBean.getJoinType()==9){
            leave.setText("助理签到");
            leave.setTextColor(Color.parseColor("#4DC056"));
            leave.setBackgroundResource(R.drawable.sign_rectange);

            apply.setVisibility(View.INVISIBLE);
        }else {
            apply.setText("取消报名");
            apply.setTextColor(Color.parseColor("#f11212"));
            apply.setBackgroundResource(R.drawable.applyn_rectange);

            leave.setText("请假");
            leave.setTextColor(Color.parseColor("#cccccc"));
            leave.setBackgroundResource(R.drawable.leave_rectange);
        }




        apply.setOnClickListener(new MyClick(likeBean));
        leave.setOnClickListener(new MyClick(likeBean));
    }

    public   class  MyClick implements View.OnClickListener
    {

        private MeetingBean meetingBean;


        public MyClick(MeetingBean likeBean) {
            this.meetingBean = likeBean;
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.apply:
                    if (meetingBean.getJoinType()==5){
                        UT.show("已请假，不能报名！");
                    }else if (meetingBean.getJoinType()==0){
                            Intent intent = new Intent(mContext,ApplyActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(MeetingBean.MEETINGBEAN,meetingBean);
                            bundle.putInt("index",0);
                            intent.putExtras(bundle);
                            if(meetingBean!=null){
                                mContext.startActivity(intent);
                            }
                    }else{
                        if(UsrMgr.getUseInfo().getIsAssistant()==1)
                        createDialog(0,"请输入取消报名的原因");
                    }
                    break;
                case R.id.leave:
                    if (meetingBean.getJoinType()==0){
                        createDialog(5,"请输入请假的原因");
                    }
                    break;
            }
        }

        public void createDialog(final int i,String title) {
            final EditText editText =new EditText(mContext);
            final AlertDialog dialog =new AlertDialog.Builder(mContext)
                    .setView(editText)
                    .setPositiveButton("确定", null)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setTitle(title).create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String cause = editText.getText().toString().trim();
                    if (TextUtils.isEmpty(cause)){
                        UT.show("输入不能为空");
                        return;
                    }
                    cancel_leave(i,cause);
                    dialog.dismiss();
                }
            });
        }

        //请假 或者取消报名
        public void cancel_leave(final int joinType, String cause) {
            RetrofitManager.getInstance(1).joinMeeting(meetingBean.getId(),joinType,cause)
                    .compose(TransformUtils.<BaseRspObj>defaultSchedulers())
                    .subscribe(new Subscriber<BaseRspObj>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(BaseRspObj responseBody) {
                            if (responseBody.getHead().getRspCode().equals("0")) {
                                if (joinType==5){
//                                    EventBus.getDefault().post(new JoinToMeetingEvent(2));

                                }else if (joinType==6){
//                                    EventBus.getDefault().post(new JoinToMeetingEvent(3));
                                }
                                joinMeetingCallBack.getJoinResult(true);
                                UT.show(responseBody.getHead().getRspMsg());
                            }

                        }
                    });
        }
    }
}
