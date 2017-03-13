package com.bus.business.mvp.ui.activities;




import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.common.Constants;
import com.bus.business.common.LoadNewsType;
import com.bus.business.common.UsrMgr;
import com.bus.business.mvp.entity.AssisBean;
import com.bus.business.mvp.entity.MeetingBean;

import com.bus.business.mvp.entity.NationBean;
import com.bus.business.mvp.entity.UserBean;
import com.bus.business.mvp.entity.response.RspNationBean;
import com.bus.business.mvp.entity.response.base.BaseRspObj;
import com.bus.business.mvp.event.MeetingDetailEvent;
import com.bus.business.mvp.presenter.impl.AssisPresenterImpl;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.mvp.view.AssisView;
import com.bus.business.mvp.view.DialogUtils;
import com.bus.business.mvp.view.WheelView;
import com.bus.business.repository.network.RetrofitManager;
import com.bus.business.utils.DensityUtil;
import com.bus.business.utils.TransformUtils;
import com.bus.business.utils.UT;
import com.socks.library.KLog;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

import static android.widget.LinearLayout.*;

/**
 * 报名Activity
 */
public class ApplyActivity extends BaseActivity implements AssisView{

    private MeetingBean meetingBean;
    private  int index=-1;
    private static final String[] MINZU = new String[56];
    private static final String[] ZHIWU = new String[]{"董事长", "总裁", "总经理", "副总经理",
            "经理", "秘书", "助理", "主席", "副主席","常务副主席","党组书记","秘书长","处长","副处长","调研员","副调研员",
            "干部","其他职务"};

   @BindView(R.id.scroll_apply)
    ScrollView scroll_apply;


//    @BindView(R.id.assis_ll)
//    LinearLayout assis_ll;
//
//    @BindView(R.id.meeting_rg)
//    RadioGroup meeting_rg;
//    @BindView(R.id.eat_rg)
//    RadioGroup eat_rg;
//    @BindView(R.id.assistant_rg)
//    RadioGroup assistant_rg;
//    @BindView(R.id.sleep_rg)
//    RadioGroup sleep_rg;
//    @BindView(R.id.driver_rg)
//    RadioGroup driver_rg;

    @BindView(R.id.car_num)
    EditText car_num;
    @BindView(R.id.remark_apply)
    EditText remark_apply;


    @Inject
    Activity mActivity;
    @Inject
    AssisPresenterImpl assisPresenterImpl;

    @BindView(R.id.apply_self)
     TextView apply_self;
    @BindView(R.id.apply_assis)
     TextView apply_assis;
    @BindView(R.id.drop_assis)
    ImageView drop_assis;
    @BindView(R.id.ll_assis)
    LinearLayout ll_assis;

    @BindView(R.id.apply_rg)
     RadioGroup apply_rg;
    @BindView(R.id.apply_man)
     RadioButton apply_man;
    @BindView(R.id.apply_woman)
     RadioButton apply_woman;


    @BindView(R.id.apply_name)
     EditText apply_name;
    @BindView(R.id.apply_company)
     EditText apply_company;
    @BindView(R.id.apply_duty)
     TextView apply_duty;
    @BindView(R.id.apply_phone)
     TextView apply_phone;
    @BindView(R.id.apply_cause)
     EditText apply_cause;
    @BindView(R.id.apply_nation)
    TextView apply_nation;
  @BindView(R.id.remark_num)
    TextView remark_num;
    @BindView(R.id.replace)
    LinearLayout replace;


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
    private String carNo="";//车牌号
    private int driver=-1;//司机的状态
    private String cause="";//拒绝或者请假原因//替会
    private String desp="";//备注
    private String leadName="";//领导名称
    private String nation="";//民族
    private String sex="";//性别
    private String companyName="";//公司名称
    private String job="";//职位


    private List<NationBean> nation_List;
    private AssisBean assisBean ;
    private List<AssisBean> list;
    private UserBean userBean=null;

    @Override
    public int getLayoutId() {
//        return R.layout.activity_apply;
        return R.layout.activity_apply1;
    }


    @Override
    public void initInjector() {
      mActivityComponent.inject(this);
    }


    @Override
    public void initViews() {
        userBean  =UsrMgr.getUseInfo();
        meetingBean = (MeetingBean) getIntent().getSerializableExtra(MeetingBean.MEETINGBEAN);
        index = getIntent().getIntExtra("index",-1);

        scroll_apply.setFocusable(true);
        scroll_apply.setFocusableInTouchMode(true);
        scroll_apply.requestFocus();

        setCustomTitle("会务报名");
        showOrGoneSearchRl(View.GONE);
        initPresenter();

        TypedArray minzu = getResources().obtainTypedArray(R.array.minzu);
        for (int i = 0; i <minzu.length() ; i++) {
            MINZU[i]=minzu.getString(i);
        }
        minzu.recycle();


        remark_apply.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()<200)
                { remark_num.setText((200-s.length())+"");}
                else {
                    UT.show("只能输入200字");
                    remark_num.setText("0");
                }
            }
        });

//        apply_assis.setCompoundDrawablePadding(10);

//        initRadioGroup(meeting_rg,meetings);
//        initRadioGroup(eat_rg,eats);
//        initRadioGroup(sleep_rg,stayNames,stayNames);
//        initRadioGroup(driver_rg,driverNames,driverTagNames);
//
//        meeting_rg.setOnCheckedChangeListener(this);
//        eat_rg.setOnCheckedChangeListener(this);
//        sleep_rg.setOnCheckedChangeListener(this);
//        driver_rg.setOnCheckedChangeListener(this);

        initSelfView();//初始化选择本人

    }

    private void initSelfView() {


        joinType = 3;
        apply_self.setTextColor(getResources().getColor(R.color.color_0dadd5));
        apply_self.setBackgroundResource(R.drawable.apply_rectange);
        apply_assis.setTextColor(getResources().getColor(R.color.color_cccccc));
        apply_assis.setText("助理");
        ll_assis.setBackgroundResource(R.drawable.leave_rectange);
        drop_assis.setImageResource(R.mipmap.icon_drop_gray);
        replace.setVisibility(GONE);


        //报名页面自动加载 本人信息
        if (userBean==null){
            return;
        }
        apply_name.setText(userBean.getNiceName());


        sex = userBean.getSex();
         if ("男".equals(userBean.getSex())){
            apply_man.setChecked(true);
         }else   if ("女".equals(userBean.getSex())){
             apply_woman.setChecked(true);
         }else {
             apply_rg.clearCheck();//后台数据性别为空
         }

        apply_company.setText(userBean.getCompanyName());
        apply_duty.setText(userBean.getPosition());
        apply_phone.setText(userBean.getPhoneNo());
        apply_nation.setText(userBean.getNation());
    }


    private void initAssisView(AssisBean assisBean) {
        //报名页面自动加载 所选助理信息
        if (assisBean==null){
            return;
        }
        apply_name.setText(assisBean.getNiceName());


            sex = assisBean.getSex();
            if ("男".equals(assisBean.getSex())){
                apply_man.setChecked(true);
            }else   if ("女".equals(assisBean.getSex())){
                apply_woman.setChecked(true);
            }else {
                apply_rg.clearCheck();//后台数据性别为空
            }



        apply_company.setText(assisBean.getCompanyName());
        apply_duty.setText(assisBean.getPosition());
        apply_phone.setText(assisBean.getPhoneNo());
        apply_nation.setText(assisBean.getNation());
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



//        initRadioGroup(assistant_rg,assistantNames,assistantNames);
//        assistant_rg.setOnCheckedChangeListener(this);
    }




    @OnClick({R.id.apply_self,R.id.ll_assis,R.id.apply_man,R.id.apply_woman,
            R.id.apply_duty,R.id.sleep_zl,R.id.sleep_need,R.id.eat_zl,R.id.eat_need
            ,R.id.apply_nation,R.id.driver_zl,R.id.driver_need,R.id.ensure_apply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.apply_self:
                initSelfView();
                break;
            case R.id.ll_assis:
                if (assistantNames!=null&&assistantNames.length>0)
                {
                    joinType = 4;
                    drop_assis.setImageResource(R.mipmap.icon_drop);
                    replace.setVisibility(VISIBLE);
                    apply_assis.setTextColor(getResources().getColor(R.color.color_0dadd5));
                    ll_assis.setBackgroundResource(R.drawable.apply_rectange);
                    apply_self.setTextColor(getResources().getColor(R.color.color_cccccc));
                    apply_self.setBackgroundResource(R.drawable.leave_rectange);
                    initWheelView("请选择助理",assistantNames);
                }else {
                    UT.show("暂无数据");
                }
                break;
            case R.id.apply_man:
                sex="男";
                break;
            case R.id.apply_woman:
                sex="女";
                break;
            case R.id.apply_duty:
                initWheelView("请选择职务",ZHIWU);
                break;
            case R.id.sleep_zl:
               stay = 1;
                break;
            case R.id.sleep_need:
                stay = 2;
                break;
            case R.id.eat_zl:
               foodId=1;
                break;
            case R.id.eat_need:
                foodId=2;
                break;
            case R.id.apply_nation:
                 initWheelView("请选择民族",MINZU);
                break;
            case R.id.driver_zl:
               driver=2;
                break;
            case R.id.driver_need:
                driver=1;
                break;
            case R.id.ensure_apply:
                getData();
                KLog.a("meetingBean.getId()  "+meetingBean.getId()+"\njoinType"+joinType+"\nfoodId" +foodId+"\n职务"+job+
                        " \nstay"+stay+"\nUserAssistantId"+userAssistantId
                        +"\nCarNo"+carNo
                        +"\ndriver"+driver
                        +"\ncause"+cause
                        +"\ndesp"+desp
                        +"\nleadName"+leadName
                        +"\nnation"+nation+
                        "\nsex"+sex
                        +"\ncompanyName"+companyName+"\njob"+job);
               applyMetting();
                break;
        }
    }

    private void initWheelView(final String title, String[] planets) {
        final String[] selecItem = {""};
        View outerView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
        final WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
        wv.setOffset(2);
        wv.setItems(Arrays.asList(planets));
        wv.setSeletion(0);
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                selecItem[0] =item;
            }
        });

        new DialogUtils.Builder(this)
                .setTitle(title)
                .setContentView(outerView)
                .setCancelable(false)
                .setPositiveButton("确定",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //如果没有选择，默认选择第一个
                        if ("".equals(selecItem[0])){
                            selecItem[0]= wv.getSeletedItem();
                        }
                        if ("请选择民族".equals(title)){
                            apply_nation.setText( selecItem[0]);
                        }else  if ("请选择助理".equals(title)){
                            apply_assis.setText(selecItem[0]);
                           initApplyData( selecItem[0]);
                        }else  if ("请选择职务".equals(title)){
                            apply_duty.setText( selecItem[0]);
                        }
                        dialog.dismiss();
                    }
                }).create().show();
    }

    private void applyMetting() {

        if (joinType ==-1 ||foodId ==-1 ||stay ==-1 ||driver ==-1 ||"".equals(leadName) || "".equals(sex)
                || "".equals(companyName) || "".equals(job)
                || "".equals(nation)){
            UT.show("请完善报名信息");
            return;
        }

        if (assistantNames!=null&&assistantNames.length>0){
            if (joinType==4){
                if ("".equals(userAssistantId) || "".equals(cause))
                {UT.show("请完善报名信息");
                   return;}
            }
        }else {
            if (joinType!=3){
                UT.show("请完善报名信息");
                return;
            }
        }



        RetrofitManager.getInstance(1).joinMeeting(meetingBean.getId(),joinType,foodId
                ,stay,userAssistantId,carNo,driver,cause,desp,leadName,nation,sex,companyName,job)
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
                          if (index==1){
                                EventBus.getDefault().post(new MeetingDetailEvent(1));
                            }
                            finish();
                        }
                        UT.show(responseBody.getHead().getRspMsg());

                    }
                });
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



//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        RadioButton ra = (RadioButton) group.findViewById(checkedId);
//        String name = (String) ra.getTag();
//        initApplyData(name,ra);
//
//    }

    private void initApplyData(String name) {
//    private void initApplyData(String name, RadioButton ra) {
//
//        //参加会议
//        if ( String.valueOf(meetings[0]).equals(name)){
//            assis_ll.setVisibility(GONE);
//            joinType = 3;
//        }else if ( String.valueOf(meetings[1]).equals(name)){
//            assis_ll.setVisibility(VISIBLE);
//            joinType = 4;
//
//        }
//
        if (joinType==4){
            //判断选取哪一个助理
            if (assistantNames!=null&assistantNames.length>0&list!=null&list.size()>0){
                for (int i = 0; i <list.size() ; i++) {
                    AssisBean  assisBean = list.get(i);
                    if (name.equals(assisBean.getUserName())){
                        userAssistantId = assisBean.getId();
                        initAssisView(assisBean);
                        break;
                    }
                }
            }
//            else {
//                if ( String.valueOf(meetings[1]).equals(name)){
//                    ra.setChecked(false);
//                    UT.show("暂无数据");
//                }
//            }
        }

//
//
//        //饮食
//        if (String.valueOf(eats[0]).equals(name)){
//            foodId = 1;
//        }else if (String.valueOf(eats[1]).equals(name)){
//            foodId = 2;
//        }
//
//        //住宿
//        if (stayNames[0].equals(name)){
//            stay = 1;
//        }else  if (stayNames[1].equals(name)){
//            stay = 2;
//        }
//
//        //司机
//        if (driverTagNames[0].equals(name)){
//            driver = 1;
//        }else  if (driverTagNames[1].equals(name)){
//            driver = 2;
//        }
    }

    private void getData() {
        leadName=apply_name.getText().toString().trim();
        companyName=apply_company.getText().toString().trim();
        job=apply_duty.getText().toString().trim();
        nation =apply_nation.getText().toString().trim();
        carNo = car_num.getText().toString().trim();
        desp = remark_apply.getText().toString().trim();
        cause = apply_cause.getText().toString().trim();
    }


//    private void  initRadioGroup(RadioGroup radioGroup, String[] dataNames,String tagNames[]){
//        int space1= DensityUtil.dip2px(mActivity,20);
//        int space= DensityUtil.dip2px(mActivity,40);
//        for (int i = 0; i <dataNames.length ; i++) {
//            RadioButton radioButton = new RadioButton(mActivity);
//            radioButton.setPadding(space1,space1,0,space1);
//            radioButton.setTextColor(Color.parseColor("#999999"));
//            radioButton.setText(dataNames[i]);
//            Drawable drawable = getResources().getDrawable(R.drawable.apply_sleep);
//            radioButton.setButtonDrawable(drawable);
//            radioButton.setTag(tagNames[i]);
//
//            View view = new View(mActivity);
//            ViewGroup.LayoutParams la = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(mActivity,2));
//            view.setLayoutParams(la);
//            view.setBackgroundColor(Color.parseColor("#d9d9d9"));
//
//
//
//            radioGroup.addView(radioButton);
//            radioGroup.setPadding(space,space1,space,space1);
//            radioGroup.addView(view);
//        }
//    }
//
//
//
//
//
//    private void  initRadioGroup(RadioGroup radioGroup, int[] drawables){
//        int space1= DensityUtil.dip2px(mActivity,20);
//        int space= DensityUtil.dip2px(mActivity,60);
//
//        for (int i = 0; i <2 ; i++) {
//            RadioButton radioButton = new RadioButton(mActivity);
//            if(i==1){
//                radioButton.setPadding(0,0,space,0);
//
//            }else {
//                radioButton.setPadding(space,0,0,0);
//            }
//
//            radioButton.setTag(String.valueOf(drawables[i]));
//
//
//
//            Drawable drawable = getResources().getDrawable(drawables[i]);
//            radioButton.setButtonDrawable(drawable);
//
//
//            radioGroup.addView(radioButton);
//            radioGroup.setPadding(space,space1,space,space1);
//        }
//    }
//
//
//    private void initMinzu() {
//        RetrofitManager.getInstance(1).getAllNation()
//                .compose(TransformUtils.<RspNationBean>defaultSchedulers())
//                .subscribe(new Subscriber<RspNationBean>() {
//                    @Override
//                    public void onCompleted() {
//                        KLog.d();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        KLog.e(e.toString());
//                    }
//
//                    @Override
//                    public void onNext(RspNationBean rspNationBean) {
//                        KLog.d(rspNationBean.toString());
//                        nation_List= rspNationBean.getBody().getList();
//                        for (int i = 0; i <nation_List.size() ; i++) {
//                            NationBean nationBean = nation_List.get(i);
//                            MINZU[i] = nationBean.getName();
//                        }
//                    }
//                });
//    }

}
