package com.ristone.businessasso.mvp.ui.activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.ristone.businessasso.R;
import com.ristone.businessasso.common.Constants;
import com.ristone.businessasso.common.UsrMgr;
import com.ristone.businessasso.utils.MarkUtils;



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
        //添加角标
        MarkUtils.getInstance(this).setMark(5);
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

}
