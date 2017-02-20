package com.bus.business.mvp.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.bus.business.R;
import com.bus.business.common.UsrMgr;
import com.bus.business.mvp.entity.response.RspUserBean;
import com.bus.business.mvp.entity.response.base.BaseRspObj;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.repository.network.RetrofitManager;
import com.bus.business.utils.TransformUtils;
import com.bus.business.utils.UT;
import com.google.gson.Gson;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/27
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.phone)
    EditText phoneEt;
    @BindView(R.id.code)
    EditText passwordEt;
    private String phoneNum;
    private String password;
    private SweetAlertDialog pDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initInjector() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
    }

    @Override
    public void initViews() {
//        phoneEt.setText("18500241615");
//        passwordEt.setText("admin");
//        phoneEt.setText("");
//        passwordEt.setText("");
    }

    private void initDialog() {

    }

    @OnClick(R.id.btnSure)
    public void onLogin(View view) {

        phoneNum = phoneEt.getText().toString().trim();
        password = passwordEt.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(password)) {
            UT.show("不能为空");
            return;
        }
        pDialog.show();
        KLog.d("ddddd");
        RetrofitManager.getInstance(1).getLoginInObservable(phoneNum, password)
                .compose(TransformUtils.<RspUserBean>defaultSchedulers())
                .subscribe(new Subscriber<RspUserBean>() {
                    @Override
                    public void onCompleted() {
                        KLog.d();
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                        pDialog.dismiss();
                    }

                    @Override
                    public void onNext(RspUserBean rspUserBean) {
                        KLog.a("user--->" + rspUserBean.toString());
                        if (rspUserBean.getHead().getRspCode().equals("0")) {
                            UsrMgr.cacheUserInfo(new Gson().toJson(rspUserBean.getBody().getUser()));
                            KLog.a("userInfo--->" + UsrMgr.getUseInfo().toString());

                            registeJpushToService();
                        }else {
                            UT.show(rspUserBean.getHead().getRspMsg());
                        }
                    }
                });

    }

    private void registeJpushToService(){
//        if (TextUtils.isEmpty(App.getJpushId()))return;
        RetrofitManager.getInstance(1).getRegisterJpushInObservable(UsrMgr.getUseId(), JPushInterface.getRegistrationID(this))
                .compose(TransformUtils.<BaseRspObj>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj>() {
                    @Override
                    public void onCompleted() {
                        KLog.d();
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                        pDialog.dismiss();
                    }

                    @Override
                    public void onNext(BaseRspObj rspUserBean) {
                        pDialog.dismiss();
                        KLog.a("user--->" + rspUserBean.toString());
                        UT.show(rspUserBean.getHead().getRspMsg());
                        if (rspUserBean.getHead().getRspCode().equals("0")) {
                             startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            LoginActivity.this.finish();
                        }

                    }
                });
    }
}
