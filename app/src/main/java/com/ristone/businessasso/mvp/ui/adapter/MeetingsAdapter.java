package com.ristone.businessasso.mvp.ui.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ristone.businessasso.R;
import com.ristone.businessasso.common.UsrMgr;
import com.ristone.businessasso.listener.JoinMeetingCallBack;
import com.ristone.businessasso.mvp.entity.MeetingBean;
import com.ristone.businessasso.mvp.entity.response.base.BaseRspObj;
import com.ristone.businessasso.mvp.ui.activities.ApplyActivity;
import com.ristone.businessasso.repository.network.RetrofitManager;

import com.ristone.businessasso.utils.DateUtil;
import com.ristone.businessasso.utils.TransformUtils;
import com.ristone.businessasso.utils.UT;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.socks.library.KLog;

import java.util.List;

import rx.Subscriber;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/24
 * 会议列表适配器
 */
public class MeetingsAdapter extends BaseQuickAdapter<MeetingBean> {

    //joinType : 2  // 参会状态  0未报名 1未签到 2已签到 3本人参加 4助理参加  5请假   6取消报名 7未参加 8已过期
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

        View view0 = baseViewHolder.getView(R.id.item_0);//即将召开会议 的视图
        View view1 = baseViewHolder.getView(R.id.item_1);//正在召开会议的视图
        View view2= baseViewHolder.getView(R.id.item_2);//历史召开会议的视图
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





    }

    private void initView(View view, MeetingBean likeBean) {
        TextView name = (TextView) view.findViewById(R.id.tv_name);
        TextView address = (TextView) view.findViewById(R.id.tv_address);
        TextView date = (TextView) view.findViewById(R.id.tv_date);
        name.setText(likeBean.getMeetingName());
        address.setText(likeBean.getAreaCode());
        date.setText(DateUtil.getCurGroupDay(likeBean.getMeetingTime()));
    }


    private void init2(View view2, MeetingBean likeBean) {
        String stateStr="";
        TextView state = (TextView) view2.findViewById(R.id.tv_state);
        TextView name = (TextView) view2.findViewById(R.id.tv_name);
        TextView date = (TextView) view2.findViewById(R.id.tv_date);
        TextView address = (TextView) view2.findViewById(R.id.tv_address);
        int draw=R.drawable.join_banover;
        switch (Integer.valueOf(likeBean.getJoinType())){
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
                draw=R.drawable.absent_banover;
                break;
        }


        //会议是否变更
        if (!"0001".equals(likeBean.getModiType())){
            //会议变更 都布置成 灰色状态
            draw=R.drawable.absent_banover;

            name.setTextColor(mContext.getResources().getColor(R.color.color_cccccc));
            date.setTextColor(mContext.getResources().getColor(R.color.color_cccccc));
            address.setTextColor(mContext.getResources().getColor(R.color.color_cccccc));
        }else {
            name.setTextColor(mContext.getResources().getColor(R.color.color_999999));
            date.setTextColor(mContext.getResources().getColor(R.color.color_999999));
            address.setTextColor(mContext.getResources().getColor(R.color.color_999999));
        }

        state.setText(stateStr);
        state.setBackgroundResource(draw);
    }

    private void init1(View view1, MeetingBean likeBean) {
        String stateStr="";
        TextView state = (TextView) view1.findViewById(R.id.tv_state);
        TextView name = (TextView) view1.findViewById(R.id.tv_name);
        TextView date = (TextView) view1.findViewById(R.id.tv_date);
        TextView address = (TextView) view1.findViewById(R.id.tv_address);
        int color=mContext.getResources().getColor(R.color.color_0dadd5);
        switch (Integer.valueOf(likeBean.getJoinType())){
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
                color=mContext.getResources().getColor(R.color.color_cccccc);
                break;
        }


        //会议是否变更
        if (!"0001".equals(likeBean.getModiType())){
            //会议变更 都布置成 灰色状态
            color=mContext.getResources().getColor(R.color.color_cccccc);

            name.setTextColor(mContext.getResources().getColor(R.color.color_cccccc));
            date.setTextColor(mContext.getResources().getColor(R.color.color_cccccc));
            address.setTextColor(mContext.getResources().getColor(R.color.color_cccccc));
        }else {
            name.setTextColor(mContext.getResources().getColor(R.color.color_333333));
            date.setTextColor(mContext.getResources().getColor(R.color.color_999999));
            address.setTextColor(mContext.getResources().getColor(R.color.color_999999));
        }

        state.setText(stateStr);
        state.setTextColor(color);

    }



//即将召开
    private void init0(View view0, MeetingBean likeBean) {
        TextView state = (TextView) view0.findViewById(R.id.tv_name);
        TextView date = (TextView) view0.findViewById(R.id.tv_date);
        TextView address = (TextView) view0.findViewById(R.id.tv_address);
        TextView leave = (TextView) view0.findViewById(R.id.leave);
        TextView apply = (TextView) view0.findViewById(R.id.apply);
        ImageView imageView = (ImageView) view0.findViewById(R.id.chan_ic_meet);

        apply.setEnabled(true);
        leave.setEnabled(true);


        //会议是否读取
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
        int  jointype=0;
        if (!TextUtils.isEmpty(likeBean.getJoinType())){
            jointype=Integer.valueOf(likeBean.getJoinType());
        }

            if (jointype==0){
                apply.setText("报名");
                apply.setTextColor(Color.parseColor("#0dadd5"));
                apply.setBackgroundResource(R.drawable.apply_rectange);

                leave.setText("请假");
                leave.setTextColor(Color.parseColor("#fdae73"));
                leave.setBackgroundResource(R.drawable.leaven_rectange);
            }else if ( jointype==5){
                leave.setText("已请假");
                leave.setTextColor(Color.parseColor("#fdae73"));
                leave.setBackgroundResource(R.drawable.leaven_rectange);

                apply.setVisibility(View.INVISIBLE);
            }else if ( jointype==2){
                leave.setText("已签到");
                leave.setTextColor(Color.parseColor("#4DC056"));
                leave.setBackgroundResource(R.drawable.sign_rectange);

                apply.setVisibility(View.INVISIBLE);
            }else if ( jointype==9){
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




        //会议是否变更
        if (!"0001".equals(likeBean.getModiType())){
            //会议变更 都布置成 灰色状态
            state.setTextColor(mContext.getResources().getColor(R.color.color_cccccc));
            date.setTextColor(mContext.getResources().getColor(R.color.color_cccccc));
            address.setTextColor(mContext.getResources().getColor(R.color.color_cccccc));
            leave.setTextColor(Color.parseColor("#cccccc"));
            apply.setTextColor(Color.parseColor("#cccccc"));
            leave.setBackgroundResource(R.drawable.leave_rectange);
            apply.setBackgroundResource(R.drawable.leave_rectange);
            apply.setEnabled(false);
            leave.setEnabled(false);
            imageView.setVisibility(View.VISIBLE);
        }else {
            imageView.setVisibility(View.GONE);
            state.setTextColor(mContext.getResources().getColor(R.color.color_333333));
            date.setTextColor(mContext.getResources().getColor(R.color.color_999999));
            address.setTextColor(mContext.getResources().getColor(R.color.color_999999));
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
                    if (Integer.valueOf(meetingBean.getJoinType())==5){
                        UT.show("已请假，不能报名！");
                    }else if (Integer.valueOf(meetingBean.getJoinType())==0){
                            Intent intent = new Intent(mContext,ApplyActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(MeetingBean.MEETINGBEAN,meetingBean);
//                            bundle.putInt("index",0);
                            intent.putExtras(bundle);
                            if(meetingBean!=null){
                                mContext.startActivity(intent);
                            }
                    }else{
                        if(UsrMgr.getUseInfo().getIsAssistant()==1)
                        createDialog(6,"请输入取消报名的原因");
                    }
                    break;
                case R.id.leave:
                    if (Integer.valueOf(meetingBean.getJoinType())==0){
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
                            KLog.d();
                        }

                        @Override
                        public void onError(Throwable e) {
                            KLog.e(e.toString());
                        }

                        @Override
                        public void onNext(BaseRspObj responseBody) {
                            KLog.d(responseBody.toString());
                            if (responseBody.getHead().getRspCode().equals("0")) {
                                joinMeetingCallBack.getJoinResult(true);
                            }
                            UT.show(responseBody.getHead().getRspMsg());
                        }
                    });
        }
    }
}
