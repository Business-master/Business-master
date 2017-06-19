package com.ristone.businessasso.mvp.ui.activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.ristone.businessasso.R;
import com.ristone.businessasso.common.Constants;
import com.ristone.businessasso.common.UsrMgr;
import com.ristone.businessasso.mvp.entity.response.base.BaseRspObj;
import com.ristone.businessasso.mvp.event.ReadMeeting;
import com.ristone.businessasso.repository.network.RetrofitManager;
import com.ristone.businessasso.utils.MarkUtils;
import com.ristone.businessasso.utils.TransformUtils;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;


/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/27
 */
public class SplashActivity extends FragmentActivity {

    private Class cls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (UsrMgr.isLogin()){
            recordLogin();
        }

        initLaunchLogo();
    }

    private void initLaunchLogo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cls = UsrMgr.isLogin() ? MainActivity.class : LoginActivity.class;
                Intent intent = new Intent(SplashActivity.this, cls);
                if (UsrMgr.isLogin()){
                    //如果启动app的Intent中带有额外的参数，表明app是从点击通知栏的动作中启动的
                    //将参数取出，传递到MainActivity中
                    if(getIntent().getBundleExtra(Constants.EXTRA_BUNDLE) != null){
                        intent.putExtra(Constants.EXTRA_BUNDLE,
                                getIntent().getBundleExtra(Constants.EXTRA_BUNDLE));
                    }
                }
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 500);
    }

    //记录登陆时间和次数
    public static void recordLogin(){
        RetrofitManager.getInstance(1).LastlogTime()
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
                        KLog.a(responseBody.toString());
                    }
                });
    }

}
