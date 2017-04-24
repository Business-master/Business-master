package com.ristone.businessasso.mvp.ui.activities;



import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ristone.businessasso.R;
import com.ristone.businessasso.common.UsrMgr;
import com.ristone.businessasso.mvp.entity.AssisBean;
import com.ristone.businessasso.mvp.entity.NationBean;
import com.ristone.businessasso.mvp.entity.OrganBean;
import com.ristone.businessasso.mvp.entity.UserBean;
import com.ristone.businessasso.mvp.entity.response.RspNationBean;
import com.ristone.businessasso.mvp.entity.response.RspOrganBean;
import com.ristone.businessasso.mvp.entity.response.base.BaseRspObj;
import com.ristone.businessasso.mvp.event.AddAssisEvent;
import com.ristone.businessasso.mvp.ui.activities.base.BaseActivity;
import com.ristone.businessasso.mvp.view.DialogUtils;
import com.ristone.businessasso.mvp.view.WheelView;
import com.ristone.businessasso.repository.network.RetrofitManager;
import com.ristone.businessasso.utils.PhoneUtils;
import com.ristone.businessasso.utils.TransformUtils;
import com.ristone.businessasso.utils.UT;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * 添加助理 页面
 */

public class AddAssisActivity extends BaseActivity {

    @BindView(R.id.name_add_assis)
    TextView name;
    @BindView(R.id.phone_add_assis)
    TextView phone;
    @BindView(R.id.pass_add_assis)
    TextView pass;
    @BindView(R.id.firm_add_assis)
    TextView firm;

    String nameStr ;
    String phoneStr ;
    String passStr ;
    String firmStr ;
    String sex ;
    String company ;
    String duty ;
    String nation;

    String sexCode ;
    String companyCode ;
    String dutyCode ;
    String nationCode;
    String assisId;//修改助理的ID

    private static final String[] ZHIWU = new String[]{"董事长", "总裁", "总经理", "副总经理",
            "经理", "秘书", "助理", "主席", "副主席","常务副主席","党组书记","秘书长","处长","副处长","调研员","副调研员",
            "干部","其他职务"};
    private static final String[] MINZU = new String[56];
    private String [] companyNames;

    @BindView(R.id.sex_add_assis)
    RadioGroup sex_add_assis;
    @BindView(R.id.apply_man)
    RadioButton apply_man;
    @BindView(R.id.apply_woman)
    RadioButton apply_woman;
    @BindView(R.id.company_add_assis)
    TextView  company_add_assis;
    @BindView(R.id.duty_add_assis)
    TextView  duty_add_assis;
    @BindView(R.id.nation_add_assis)
    TextView  nation_add_assis;

   List<NationBean> sexList=new ArrayList<>();
    List<NationBean> dutyList=new ArrayList<>();
    List<OrganBean> origanList=new ArrayList<>();
    List<NationBean> nationList=new ArrayList<>();



    private AssisBean assisBean;
    private int index=1;

    private UserBean userBean;


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_assis;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        userBean = UsrMgr.getUseInfo();

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            index = bundle.getInt("index");
            assisBean = (AssisBean) bundle.getSerializable("AssisBean");
        }

        if (index==2){

            setCustomTitle("修改助理信息");
            showOrGoneSearchRl(View.GONE);
            initAssisView(assisBean);
        }else {
            setCustomTitle("添加助理");
            showOrGoneSearchRl(View.GONE);
            if (!TextUtils.isEmpty(userBean.getCompanyName()))
                company_add_assis.setText(userBean.getCompanyName());
//            company_add_assis.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
//            company_add_assis.setClickable(false);
        }
        initData();

    }

    private void initAssisView(AssisBean assisBean) {
        //修改助理信息自动加载 所选助理信息
        if (assisBean==null){
            return;
        }

        assisId = assisBean.getId();

        if (!TextUtils.isEmpty(assisBean.getUserName()))
        name.setText(assisBean.getUserName());

        if (!TextUtils.isEmpty(assisBean.getPhoneNo()))
        phone.setText(assisBean.getPhoneNo());

        if (!TextUtils.isEmpty(assisBean.getSex()))
        sex = assisBean.getSex();
        if ("男".equals(assisBean.getSex())){
            apply_man.setChecked(true);
        }else   if ("女".equals(assisBean.getSex())){
            apply_woman.setChecked(true);
        }

//        Drawable dra = getResources().getDrawable(R.drawable.apply_xiabiao);
//        company_add_assis.setCompoundDrawablesWithIntrinsicBounds(null,null,dra,null);
//        company_add_assis.setClickable(true);
        if (!TextUtils.isEmpty(assisBean.getCompanyName()))
        company_add_assis.setText(assisBean.getCompanyName());

        if (!TextUtils.isEmpty(assisBean.getPosition()))
        duty_add_assis.setText(assisBean.getPosition());

        if (!TextUtils.isEmpty(assisBean.getNation()))
        nation_add_assis.setText(assisBean.getNation());
    }

    private void initData() {

        TypedArray minzu = getResources().obtainTypedArray(R.array.minzu);
        for (int i = 0; i <minzu.length() ; i++) {
            MINZU[i]=minzu.getString(i);
        }
        minzu.recycle();

          RetrofitManager.getInstance(1).getAllSex()
                  .compose(TransformUtils.<RspNationBean>defaultSchedulers())
                  .subscribe(new Subscriber<RspNationBean>() {
                      @Override
                      public void onCompleted() {
                          KLog.d();
                      }

                      @Override
                      public void onError(Throwable e) {
                          KLog.e(e.toString());
                      }

                      @Override
                      public void onNext(RspNationBean rspNationBean) {
                          KLog.d(rspNationBean.toString());
                          if ("0".equals(rspNationBean.getHead().getRspCode())){
                              sexList = (List<NationBean>) rspNationBean.getBody().getList();
                          }
                      }
                  });
        RetrofitManager.getInstance(1).getAllCompany()
                .compose(TransformUtils.<RspOrganBean>defaultSchedulers())
                .subscribe(new Subscriber<RspOrganBean>() {
                    @Override
                    public void onCompleted() {
                        KLog.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                    }

                    @Override
                    public void onNext(RspOrganBean rspOriganBean) {
                        KLog.d(rspOriganBean.toString());
                        if ("0".equals(rspOriganBean.getHead().getRspCode())){
                            origanList = (List<OrganBean>) rspOriganBean.getBody().getList();
//                            companyNames = new String[origanList.size()];
//                            for (int i = 0; i <origanList.size() ; i++) {
//                                companyNames[i]=origanList.get(i).getOrganizationName();
//                            }
                        }
                    }
                });

        RetrofitManager.getInstance(1).getAllPosition()
                .compose(TransformUtils.<RspNationBean>defaultSchedulers())
                .subscribe(new Subscriber<RspNationBean>() {
                    @Override
                    public void onCompleted() {
                        KLog.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                    }

                    @Override
                    public void onNext(RspNationBean rspNationBean) {
                        KLog.d(rspNationBean.toString());
                        if ("0".equals(rspNationBean.getHead().getRspCode())){
                            dutyList = (List<NationBean>) rspNationBean.getBody().getList();
                        }
                    }
                });

        RetrofitManager.getInstance(1).getAllNation()
                .compose(TransformUtils.<RspNationBean>defaultSchedulers())
                .subscribe(new Subscriber<RspNationBean>() {
                    @Override
                    public void onCompleted() {
                        KLog.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                    }

                    @Override
                    public void onNext(RspNationBean rspNationBean) {
                        KLog.d(rspNationBean.toString());
                        if ("0".equals(rspNationBean.getHead().getRspCode())){
                            nationList = (List<NationBean>) rspNationBean.getBody().getList();
                        }
                    }
                });
    }

    @OnClick({R.id.btn_add_assis,R.id.duty_add_assis,R.id.nation_add_assis})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_add_assis:
                    addAssis();
                break;
            case R.id.duty_add_assis:
                initWheelView("请选择职务",ZHIWU);
                break;
            case R.id.nation_add_assis:
                initWheelView("请选择民族",MINZU);
                break;
//            case R.id.company_add_assis:
//                initWheelView("请选择单位",companyNames);
//                break;
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
                       if ("请选择职务".equals(title)){
                            duty_add_assis.setText( selecItem[0]);
                        }else if ("请选择民族".equals(title)){
                           nation_add_assis.setText( selecItem[0]);
                       }
//                       else if ("请选择单位".equals(title)){
//                           company_add_assis.setText( selecItem[0]);
//                       }
                        dialog.dismiss();
                    }
                }).create().show();
    }


    private void addAssis() {
        if (judgement()){
            RetrofitManager.getInstance(1).getAddAssisListObservable(nameStr,passStr,phoneStr,sexCode,companyCode,dutyCode,nationCode,assisId)
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
                        public void onNext(BaseRspObj baseRspObj) {
                            KLog.d(baseRspObj.toString());
                            if (baseRspObj.getHead().getRspCode().equals("0")){
                                EventBus.getDefault().post(new AddAssisEvent(1));
                                finish();
                            }
                            UT.show(baseRspObj.getHead().getRspMsg());
                        }
                    });
        }
    }


  //添加助理时判断是否符合条件
    private boolean judgement() {
        boolean right=true;
        nameStr = name.getText().toString().trim();
         phoneStr = phone.getText().toString().trim();
         passStr = pass.getText().toString().trim();
         firmStr = firm.getText().toString().trim();
        RadioButton radioButton = (RadioButton) findViewById(sex_add_assis.getCheckedRadioButtonId());
        if (radioButton!=null)
         sex = radioButton.getText().toString().trim();
         company = company_add_assis.getText().toString().trim();
         duty = duty_add_assis.getText().toString().trim();
         nation = nation_add_assis.getText().toString().trim();

        //遍历 根据 中文 性别（男）查找 code
        if (!TextUtils.isEmpty(sex)& sexList!=null &sexList.size()>0){
            for (int i = 0; i <sexList.size() ; i++) {
                NationBean nationBean = sexList.get(i);
                if (sex.equals(nationBean.getName())){
                    sexCode = nationBean.getCode();
                    break;
                }
            }
        }

        //遍历 根据 中文 单位名称查找 code
         if (!TextUtils.isEmpty(company)& origanList!=null &origanList.size()>0){
            for (int i = 0; i <origanList.size() ; i++) {
                OrganBean organBean = origanList.get(i);
                if (company.equals(organBean.getOrganizationName())){
                    companyCode = organBean.getOrganizationCode();
                    break;
                }
            }
        }

        //遍历 根据 中文 民族 查找 code
          if (!TextUtils.isEmpty(nation)& nationList!=null &nationList.size()>0){
            for (int i = 0; i <nationList.size() ; i++) {
                NationBean nationBean = nationList.get(i);
                if (nation.equals(nationBean.getName())){
                    nationCode = nationBean.getCode();
                    break;
                }
            }
        }

        //遍历 根据 中文 职务 查找 code
          if (!TextUtils.isEmpty(duty)& dutyList!=null &dutyList.size()>0){
            for (int i = 0; i <dutyList.size() ; i++) {
                NationBean nationBean = dutyList.get(i);
                if (duty.equals(nationBean.getName())){
                    dutyCode = nationBean.getCode();
                    break;
                }
            }
        }

          if (index==2){
              if (!TextUtils.isEmpty(nameStr)){
                  if (!(nameStr.length()>1&nameStr.length()<9)){
                      UT.show("请输入正确的用户名");
                      right=false;
                  }
              }

              if (!TextUtils.isEmpty(phoneStr)){
                  if (phoneStr.length()==11& PhoneUtils.isPhoneNum(phoneStr)){
                      if(phoneStr.equals(assisBean.getPhoneNo())){
                          phoneStr="号码相同";
                      }
                  }else {
                      UT.show("请输入正确的手机号");
                      right=false;
                  }
              }

              if (!TextUtils.isEmpty(passStr)||!TextUtils.isEmpty(firmStr)){
                  if (!(passStr.length()>5 &passStr.length()<21)){
                      UT.show("请输入正确的密码");
                      right=false;
                  }

                  if (!(passStr.equals(firmStr))){
                      UT.show("两次密码不一致");
                      right=false;
                  }
              }


          }else {
              if (TextUtils.isEmpty(nameStr)
                      ||TextUtils.isEmpty(phoneStr)
                      ||TextUtils.isEmpty(passStr)
                      ||TextUtils.isEmpty(sexCode)
                      ||TextUtils.isEmpty(companyCode)
                      ||TextUtils.isEmpty(nationCode)
                      ||TextUtils.isEmpty(dutyCode)
                      ||TextUtils.isEmpty(firmStr)){
                  UT.show("输入不能为空");
                  right=false;
              }else {

                  if (!(nameStr.length()>1&nameStr.length()<9)){
                      UT.show("请输入正确的用户名");
                      right=false;
                  }


                  if (!(phoneStr.length()==11&PhoneUtils.isPhoneNum(phoneStr))){
                      UT.show("请输入正确的手机号");
                      right=false;
                  }

                  if (!(passStr.length()>5 &passStr.length()<21)){
                      UT.show("请输入正确的密码");
                      right=false;
                  }

                  if (!passStr.equals(firmStr)){
                      UT.show("两次密码不一致");
                      right=false;
                  }


//                  if (nameStr.length()>1&nameStr.length()<9
//                          &phoneStr.length()==11&!passStr.matches("[0-9]+")&
//                          passStr.length()>5 &passStr.length()<21
//                          &passStr.equals(firmStr)){
//                      right=true;
//                  } else {
//                      UT.show("请输入正确的信息");
//                      right=false;
//                  }
              }
          }
       return  right;
    }


}
