package com.bus.business.mvp.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.mvp.entity.MeetingBean;
import com.bus.business.mvp.event.JoinToMeetingEvent;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.utils.ApplyUtils;
import com.bus.business.utils.DateUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;

import static com.bus.business.mvp.entity.MeetingBean.MEETINGBEAN;
import static com.bus.business.mvp.entity.MeetingBean.MEETINGPOS;

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

    public static void startIntent(int meetingId, int pos,Context context){
        Intent intent = new Intent(context, MeetingDetailActivity.class);
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable(MEETINGBEAN, new MeetingBean(Integer.valueOf(meetingId)));
        bundle1.putInt(MEETINGPOS, pos);
        intent.putExtras(bundle1);
        context.startActivity(intent);
    }


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

        mProgressBar.setVisibility(View.GONE);
        setCustomTitle("会务详情");
        showOrGoneSearchRl(View.GONE);


        stateStr = ApplyUtils.getInstance().initHide(cancel_apply,btn_apply,btn_leave,meetingBean.getJoinType());
        if(meetingBean.getJoinType()==0){
            btn_apply.setTextColor(getResources().getColor(R.color.text_color_rectange));
            btn_apply.setBackgroundResource(R.drawable.btn_rectange);
        }
        btn_apply.setText(stateStr);


        cancel_apply.setOnClickListener(new ApplyUtils.MyClick(meetingBean,this));
        btn_apply.setOnClickListener(new ApplyUtils.MyClick(meetingBean,this));
        btn_leave.setOnClickListener(new ApplyUtils.MyClick(meetingBean,this));

        mTitle.setText(meetingBean.getMeetingName());
        mJoinDate.setText("参会时间:"+DateUtil.getCurGroupDay(meetingBean.getMeetingTime()));
        mPubDate.setText("发表时间:"+DateUtil.getCurGroupDay(meetingBean.getCtime()));
        mJoinAddress.setText("参会地点:"+meetingBean.getMeetingLoc());
        mNewsDetailBodyTv.setText(meetingBean.getMeetingContent());
    }





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



}
