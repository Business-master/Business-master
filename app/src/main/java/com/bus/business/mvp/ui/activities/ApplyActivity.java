package com.bus.business.mvp.ui.activities;




import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.ToggleButton;

import com.bus.business.R;
import com.bus.business.common.Constants;
import com.bus.business.common.LoadNewsType;
import com.bus.business.mvp.entity.AssisBean;
import com.bus.business.mvp.entity.MeetingBean;

import com.bus.business.mvp.entity.response.base.BaseRspObj;
import com.bus.business.mvp.event.JoinToMeetingEvent;
import com.bus.business.mvp.presenter.impl.AssisPresenterImpl;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.mvp.view.AssisView;
import com.bus.business.repository.network.RetrofitManager;
import com.bus.business.utils.DensityUtil;
import com.bus.business.utils.TransformUtils;
import com.bus.business.utils.UT;
import com.socks.library.KLog;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

import static android.widget.LinearLayout.*;

/**
 * 报名Activity
 */
public class ApplyActivity extends BaseActivity implements AssisView, RadioGroup.OnCheckedChangeListener{

    private MeetingBean meetingBean;


   @BindView(R.id.scroll_apply)
    ScrollView scroll_apply;


    @BindView(R.id.assis_ll)
    LinearLayout assis_ll;

    @BindView(R.id.meeting_rg)
    RadioGroup meeting_rg;
    @BindView(R.id.eat_rg)
    RadioGroup eat_rg;
    @BindView(R.id.assistant_rg)
    RadioGroup assistant_rg;
    @BindView(R.id.sleep_rg)
    RadioGroup sleep_rg;
    @BindView(R.id.driver_rg)
    RadioGroup driver_rg;

    @BindView(R.id.car_num)
    EditText car_num;
    @BindView(R.id.remark_apply)
    EditText remark_apply;


    @Inject
    Activity mActivity;
    @Inject
    AssisPresenterImpl assisPresenterImpl;



    int[] meetings= new  int[]{R.drawable.apply_self,R.drawable.apply_assis};
    int[] eats= new  int[]{R.drawable.apply_zili,R.drawable.apply_need};

    String[] stayNames= new  String[]{"自理","需要提供"};
    String[] assistantNames;
    String[] driverTagNames= new  String[]{"无","有"};
    String[] driverNames= new  String[]{"需要提供","自理"};

    private int joinType=-1;//参加会议的状态
    private int foodId=-1;//饮食的状态
    private int stay=-1;//住宿的状态
    private String userAssistantId="";//代理当前用户的助理id
    private String carNo;//车牌号
    private int driver=1;//司机的状态
    private String cause="";//拒绝或者请假原因
    private String desp="";//备注



    @Override
    public int getLayoutId() {
        return R.layout.activity_apply3;
    }


   private void  initRadioGroup(RadioGroup radioGroup, String[] dataNames,String tagNames[]){
       int space1= DensityUtil.dip2px(mActivity,20);
       int space= DensityUtil.dip2px(mActivity,40);
       for (int i = 0; i <dataNames.length ; i++) {
           RadioButton radioButton = new RadioButton(mActivity);
           radioButton.setPadding(space1,space1,0,space1);
           radioButton.setTextColor(Color.parseColor("#999999"));
           radioButton.setText(dataNames[i]);
           Drawable drawable = getResources().getDrawable(R.drawable.apply_sleep);
           radioButton.setButtonDrawable(drawable);
           radioButton.setTag(tagNames[i]);

           View view = new View(mActivity);
           ViewGroup.LayoutParams la = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(mActivity,2));
           view.setLayoutParams(la);
           view.setBackgroundColor(Color.parseColor("#d9d9d9"));



           radioGroup.addView(radioButton);
           radioGroup.setPadding(space,space1,space,space1);
           radioGroup.addView(view);
       }
   }





    private void  initRadioGroup(RadioGroup radioGroup, int[] drawables){
        int space1= DensityUtil.dip2px(mActivity,20);
        int space= DensityUtil.dip2px(mActivity,60);

        for (int i = 0; i <2 ; i++) {
            RadioButton radioButton = new RadioButton(mActivity);
            if(i==1){
                radioButton.setPadding(0,0,space,0);

            }else {
                radioButton.setPadding(space,0,0,0);
            }

            radioButton.setTag(String.valueOf(drawables[i]));



            Drawable drawable = getResources().getDrawable(drawables[i]);
            radioButton.setButtonDrawable(drawable);


            radioGroup.addView(radioButton);
            radioGroup.setPadding(space,space1,space,space1);
        }




    }

    @Override
    public void initInjector() {
      mActivityComponent.inject(this);
    }



    @Override
    public void initViews() {

        desp = remark_apply.getText().toString().trim();
        carNo = car_num.getText().toString().trim();
        meetingBean = (MeetingBean) getIntent().getSerializableExtra(MeetingBean.MEETINGBEAN);

        scroll_apply.setFocusable(true);
        scroll_apply.setFocusableInTouchMode(true);
        scroll_apply.requestFocus();

        setCustomTitle("报名信息");
        showOrGoneSearchRl(View.GONE);
        initPresenter();

        initRadioGroup(meeting_rg,meetings);
        initRadioGroup(eat_rg,eats);
        initRadioGroup(sleep_rg,stayNames,stayNames);
        initRadioGroup(driver_rg,driverNames,driverTagNames);

        meeting_rg.setOnCheckedChangeListener(this);
        eat_rg.setOnCheckedChangeListener(this);
        sleep_rg.setOnCheckedChangeListener(this);
        driver_rg.setOnCheckedChangeListener(this);

    }

    private void initPresenter() {
        assisPresenterImpl.setNewsTypeAndId(1, Constants.numPerPage, "",-1);
        mPresenter = assisPresenterImpl;
        mPresenter.attachView(this);
        mPresenter.onCreate();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        assisPresenterImpl.onDestory();
    }



     private AssisBean assisBean ;
    private List<AssisBean> list;

    @Override
    public void setAssissList(List<AssisBean> assissList, @LoadNewsType.checker int loadType) {
        list = assissList;

        List<String> stringList = new ArrayList<>();
        for (int i = 0; i <assissList.size() ; i++) {
            assisBean = assissList.get(i);
            if (assisBean.getIsAssistant()==2){//此助理空闲
                stringList.add(assisBean.getUserName());
            }

        }

        assistantNames = new String[stringList.size()];
        for (int i = 0; i <stringList.size() ; i++) {
            assistantNames[i] =stringList.get(i);
        }

        initRadioGroup(assistant_rg,assistantNames,assistantNames);
        assistant_rg.setOnCheckedChangeListener(this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String msg) {


    }

    @OnClick({R.id.ensure_apply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ensure_apply:
               applyMetting();
                break;
        }
    }

    private void applyMetting() {

        if (joinType ==-1 || stay==-1 || foodId ==-1 ){
            UT.show("请完善报名信息");
            return;
        }

        if (assistantNames.length>0){
            if (joinType==4 & "".equals(userAssistantId)){
                UT.show("请完善报名信息");
                return;
            }
        }else {
            if (joinType!=3){
                UT.show("请完善报名信息");
                return;
            }
        }




        RetrofitManager.getInstance(1).joinMeeting(meetingBean.getId(),joinType,foodId
                ,stay,userAssistantId,carNo,driver,cause,desp)
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
//                            meetingBean.setJoinType(true);
                            EventBus.getDefault().post(new JoinToMeetingEvent(1));
                            finish();
                        }
//                        UT.show(responseBody.getHead().getRspMsg());

                    }
                });
    }



    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton ra = (RadioButton) group.findViewById(checkedId);
        String name = (String) ra.getTag();
        initApplyData(name,ra);

    }

    private void initApplyData(String name, RadioButton ra) {

        //参加会议
        if ( String.valueOf(meetings[0]).equals(name)){
            assis_ll.setVisibility(GONE);
            joinType = 3;
        }else if ( String.valueOf(meetings[1]).equals(name)){
            assis_ll.setVisibility(VISIBLE);
            joinType = 4;

        }

        if (joinType==4){
            //判断选取哪一个助理
            if (assistantNames.length>0&list!=null&list.size()>0){
                for (int i = 0; i <list.size() ; i++) {
                    AssisBean  assisBean = list.get(i);
                    if (name.equals(assisBean.getUserName())){
                        userAssistantId = assisBean.getId();
                        break;
                    }
                }
            }else {
                if ( String.valueOf(meetings[1]).equals(name)){
                    ra.setChecked(false);
                    UT.show("暂无数据");
                }
            }
        }



        //饮食
        if (String.valueOf(eats[0]).equals(name)){
            foodId = 1;
        }else if (String.valueOf(eats[1]).equals(name)){
            foodId = 2;
        }

        //住宿
        if (stayNames[0].equals(name)){
            stay = 1;
        }else  if (stayNames[1].equals(name)){
            stay = 2;
        }

        //司机
        if (driverTagNames[0].equals(name)){
            driver = 1;
        }else  if (driverTagNames[1].equals(name)){
            driver = 2;
        }
    }


}
