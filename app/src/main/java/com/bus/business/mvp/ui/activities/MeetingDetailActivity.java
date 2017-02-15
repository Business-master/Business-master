package com.bus.business.mvp.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.mvp.entity.MeetingBean;
import com.bus.business.mvp.entity.response.base.BaseRspObj;
import com.bus.business.mvp.event.CheckMeetingStateEvent;
import com.bus.business.mvp.event.JoinToMeetingEvent;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.repository.network.RetrofitManager;
import com.bus.business.utils.ApplyUtils;
import com.bus.business.utils.DateUtil;
import com.bus.business.utils.TransformUtils;
import com.bus.business.utils.UT;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/10
 */
public class MeetingDetailActivity extends BaseActivity{

    @Inject
    Activity mActivity;

    @BindView(R.id.news_detail_title_tv)
    TextView mTitle;
    @BindView(R.id.tv_publish_date)
    TextView mPubDate;
    @BindView(R.id.tv_join_address)
    TextView mJoinAddress;
    @BindView(R.id.tv_join_date)
    TextView mJoinDate;
    @BindView(R.id.news_detail_body_tv)
    TextView mNewsDetailBodyTv;


    @BindView(R.id.cancel_apply)
    Button cancel_apply;
    @BindView(R.id.btn_apply)
    Button btn_apply;
    @BindView(R.id.btn_leave)
    Button btn_leave;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private MeetingBean meetingBean;
    private int currentPos;
    private String stateStr;


    @Override
    public int getLayoutId() {
        return R.layout.activity_meeting_detail;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        meetingBean = (MeetingBean) getIntent().getSerializableExtra(MeetingBean.MEETINGBEAN);
//        currentPos = getIntent().getIntExtra(MeetingBean.MEETINGPOS,0);

        mProgressBar.setVisibility(View.GONE);
        setCustomTitle(meetingBean.getMeetingName());
        showOrGoneSearchRl(View.GONE);

//        if (meetingBean.getCheckType()){
//            stateStr = "已参会";
//        }else {
//            stateStr = meetingBean.getJoinType()==1?"已报名":"报名";
//        }

        stateStr = ApplyUtils.getInstance().initHide(cancel_apply,btn_apply,btn_leave,meetingBean.getJoinType());
        if(meetingBean.getJoinType()==0){
            btn_apply.setTextColor(getResources().getColor(R.color.text_color_rectange));
            btn_apply.setBackgroundResource(R.drawable.btn_rectange);
        }
        btn_apply.setText(stateStr);


        cancel_apply.setOnClickListener(new ApplyUtils.MyClick(meetingBean,this));
        btn_leave.setOnClickListener(new ApplyUtils.MyClick(meetingBean,this));

        mTitle.setText(meetingBean.getMeetingName());
//        btn_apply.setBackgroundResource(meetingBean.getJoinType()!=0 ? R.drawable.grey_circle_5 : R.drawable.blue_circle_5);
        mJoinDate.setText("参会时间:"+DateUtil.getCurGroupDay(meetingBean.getMeetingTime()));
        mPubDate.setText("发表时间:"+DateUtil.getCurGroupDay(meetingBean.getCtime()));
        mJoinAddress.setText("参会地点:"+meetingBean.getMeetingLoc());
        mNewsDetailBodyTv.setText(meetingBean.getMeetingContent());
    }



//    @OnClick(R.id.img_add)
//    public void addToMeeting(View view){
//        requestAddToMeeting();
//
//    }

//    @Subscribe
//    public void onCheckMeetingToRefresh(CheckMeetingStateEvent event){
//
//    }


    @Subscribe
    public void onEventMainThread(JoinToMeetingEvent event){
        /**
         *1 报名 2请假 3取消报名
         */
          switch (event.getPos()){
              case 1:
                  btn_apply.setVisibility(View.INVISIBLE);
                  cancel_apply.setVisibility(View.VISIBLE);
                  btn_leave.setVisibility(View.VISIBLE);
                  break;
              case 2:
                  btn_apply.setText("已请假");
                  btn_apply.setVisibility(View.VISIBLE);
                  cancel_apply.setVisibility(View.INVISIBLE);
                  btn_leave.setVisibility(View.INVISIBLE);
                  break;
              case 3:
                  btn_apply.setText("已取消报名");
                  btn_apply.setVisibility(View.VISIBLE);
                  cancel_apply.setVisibility(View.INVISIBLE);
                  btn_leave.setVisibility(View.INVISIBLE);
                  break;
          }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }



//    public void requestAddToMeeting(){
////        if (meetingBean.getJoinType()==1){
////            UT.show("已报名");
////            return;
////        }
//        if (meetingBean.getJoinType()!=0){
//            UT.show("已报名");
//            return;
//        }
//
//        Intent intent = new Intent(mActivity,ApplyActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(MeetingBean.MEETINGBEAN,meetingBean);
//        intent.putExtras(bundle);
//        startActivity(intent);
//
//
//    }
}
