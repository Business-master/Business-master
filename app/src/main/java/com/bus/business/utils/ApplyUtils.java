package com.bus.business.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bus.business.App;
import com.bus.business.R;
import com.bus.business.mvp.entity.MeetingBean;
import com.bus.business.mvp.entity.response.base.BaseRspObj;
import com.bus.business.mvp.event.JoinToMeetingEvent;
import com.bus.business.mvp.ui.activities.ApplyActivity;
import com.bus.business.repository.network.RetrofitManager;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by ATRSnail on 2017/2/12.
 * 报名 工具类(提取重复的代码方法)
 */

public  class ApplyUtils {
    private static ApplyUtils applyUtils =null;
    private ApplyUtils() {
    }

    public static ApplyUtils getInstance(){
        if (applyUtils==null){
            synchronized(ApplyUtils.class){
                if (applyUtils ==null){
                    applyUtils = new ApplyUtils();
                }
            }
        }


        return applyUtils;
    }






    //根据 JoinType 初始化会议列表----- 报名状态按钮的文字显示 --和-- 取消报名、报名、请假三个按钮的显示状态
    public  String initState_Hide(Button cancel,Button apply ,Button leave,int joinType){
        String stateStr="";
        switch (joinType){
            case 0:
                stateStr="未报名";
                hide(2,cancel,apply,leave);
                break;
            case 1:
            case 3:
                stateStr="已报名";
                hide(1,cancel,apply,leave);
                break;
            case 2:
                stateStr="已签到";
                hide(3,cancel,apply,leave);
                break;
            case 4:
                stateStr="助理参加";
                hide(3,cancel,apply,leave);
                break;
            case 5:
                stateStr="已请假";
                hide(3,cancel,apply,leave);
                break;
            case 6:
                stateStr="已取消报名";
                hide(3,cancel,apply,leave);
                break;
            case 7:
                stateStr="缺席";
                hide(3,cancel,apply,leave);
                break;
            case 8:
                stateStr="已过期";
                hide(3,cancel,apply,leave);
                break;
        }
      return stateStr;
    }

    //根据 JoinType 初始化会议详情----- 报名状态按钮的文字显示 --和-- 取消报名、报名、请假三个按钮的显示状态
    public  String initHide(Button cancel,Button apply ,Button leave,int joinType){
        String stateStr="";
        switch (joinType){
            case 0:
                stateStr="报名";
                hide(2,cancel,apply,leave);
                break;
            case 1:
            case 3:
                stateStr="已报名";
                hide(1,cancel,apply,leave);
                break;
            case 2:
                stateStr="已签到";
                hide(4,cancel,apply,leave);
                break;
            case 4:
                stateStr="助理参加";
                hide(4,cancel,apply,leave);
                break;
            case 5:
                stateStr="已请假";
                hide(4,cancel,apply,leave);
                break;
            case 6:
                stateStr="已取消报名";
                hide(4,cancel,apply,leave);
                break;
            case 7:
                stateStr="缺席";
                hide(4,cancel,apply,leave);
                break;
            case 8:
                stateStr="已过期";
                hide(4,cancel,apply,leave);
                break;
        }
        return stateStr;
    }



    public  void hide(int i,Button mCancel,Button mApply,Button mLeave) {
        switch (i){
            case 1:
                mApply.setVisibility(View.INVISIBLE);
                mCancel.setVisibility(View.VISIBLE);
                mLeave.setVisibility(View.VISIBLE);
                break;
            case 2:
                mApply.setVisibility(View.VISIBLE);
                mCancel.setVisibility(View.INVISIBLE);
                mLeave.setVisibility(View.INVISIBLE);
                break;
            case 3:
                mCancel.setVisibility(View.INVISIBLE);
                mLeave.setVisibility(View.INVISIBLE);
                mApply.setVisibility(View.INVISIBLE);
                break;
            case 4:
                mCancel.setVisibility(View.INVISIBLE);
                mLeave.setVisibility(View.INVISIBLE);
                break;
        }
    }



   public static class  MyClick implements View.OnClickListener
    {

        private MeetingBean meetingBean;
        private Context mContext=App.getAppContext();

        public MyClick(MeetingBean likeBean,Context context) {
            this.meetingBean = likeBean;
            this.mContext = context;
        }






        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cancel_apply:
                    createDialog(6,"请输入取消报名的原因");
                    break;
                case R.id.btn_apply:
                    if (meetingBean.getJoinType()!=0){
                        return;
                    }
                    Intent intent = new Intent(mContext,ApplyActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(MeetingBean.MEETINGBEAN,meetingBean);
                    intent.putExtras(bundle);
                    if(meetingBean!=null){
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.btn_leave:
                    createDialog(5,"请输入请假的原因");
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
                                    EventBus.getDefault().post(new JoinToMeetingEvent(2));
                                }else if (joinType==6){
                                    EventBus.getDefault().post(new JoinToMeetingEvent(3));
                                }
                            }
                            UT.show(responseBody.getHead().getRspMsg());
                        }
                    });
        }
    }
}
