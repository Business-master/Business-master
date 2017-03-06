package com.bus.business.mvp.ui.activities;



import android.renderscript.BaseObj;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.mvp.entity.response.RspAssisBean;
import com.bus.business.mvp.entity.response.base.BaseRspObj;
import com.bus.business.mvp.event.AddAssisEvent;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.repository.network.RetrofitManager;
import com.bus.business.utils.TransformUtils;
import com.bus.business.utils.UT;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;


public class AddAssisActivity extends BaseActivity {

    @BindView(R.id.name_add_assis)
    TextView name;
    @BindView(R.id.phone_add_assis)
    TextView phone;
    @BindView(R.id.pass_add_assis)
    TextView pass;
    @BindView(R.id.firm_add_assis)
    TextView firm;
    private  boolean right=true;
    String nameStr ;
    String phoneStr ;
    String passStr ;
    String firmStr ;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_assis;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        setCustomTitle("添加助理");
        showOrGoneSearchRl(View.GONE);
    }

    @OnClick(R.id.btn_add_assis)
    public void onClick(View v){
        if (judgement()){
            RetrofitManager.getInstance(1).getAddAssisListObservable(nameStr,passStr,phoneStr)
                    .compose(TransformUtils.<BaseRspObj>defaultSchedulers())
                    .subscribe(new Subscriber<BaseRspObj>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            UT.show("保存失败");
                        }

                        @Override
                        public void onNext(BaseRspObj baseRspObj) {
                            if (baseRspObj.getHead().getRspCode().equals("0")){
                                EventBus.getDefault().post(new AddAssisEvent(1));
                                finish();
                            }
                            UT.show(baseRspObj.getHead().getRspMsg());
                        }
                    });
        }
    }

    private boolean judgement() {
         nameStr = name.getText().toString().trim();
         phoneStr = phone.getText().toString().trim();
         passStr = pass.getText().toString().trim();
         firmStr = firm.getText().toString().trim();
        if (TextUtils.isEmpty(nameStr)
                ||TextUtils.isEmpty(phoneStr)
                ||TextUtils.isEmpty(passStr)
                ||TextUtils.isEmpty(firmStr)){
            UT.show("输入不能为空");
           right=false;
        }else {
            if (nameStr.length()>1&nameStr.length()<9
                    &phoneStr.length()==11&!passStr.matches("[0-9]+")&
                    passStr.length()>5 &passStr.length()<21
                    &passStr.equals(firmStr)){
                right=true;
            } else {
                UT.show("请输入正确的信息");
                right=false;
            }
        }

       return  right;
    }


}
