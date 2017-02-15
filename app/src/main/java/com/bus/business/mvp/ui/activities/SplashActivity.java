package com.bus.business.mvp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.bus.business.R;
import com.bus.business.common.UsrMgr;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/27
 * 程序刚进时的欢迎页面
 */
public class SplashActivity extends FragmentActivity {

    private Class cls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initLaunchLogo();
    }

    private void initLaunchLogo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cls = UsrMgr.isLogin() ? MainActivity.class : LoginActivity.class;
                startActivity(new Intent(SplashActivity.this, cls));
                SplashActivity.this.finish();
            }
        }, 500);
    }

}
