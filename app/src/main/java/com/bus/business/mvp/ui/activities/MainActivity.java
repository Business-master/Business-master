package com.bus.business.mvp.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bus.business.R;
import com.bus.business.common.Constants;
import com.bus.business.common.NewsType;
import com.bus.business.mvp.entity.response.base.BaseRspObj;
import com.bus.business.mvp.event.ChangeSearchStateEvent;
import com.bus.business.mvp.event.CheckMeetingStateEvent;
import com.bus.business.mvp.event.JoinToMeetingEvent;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.mvp.ui.fragment.ExpertFragment;
import com.bus.business.mvp.ui.fragment.MainPagerFragment;
import com.bus.business.mvp.ui.fragment.MineFragment;
import com.bus.business.mvp.ui.fragment.NewMeetingFragment;
import com.bus.business.mvp.ui.fragment.WanFragment;
import com.bus.business.mvp.ui.fragment.WanFragment2;
import com.bus.business.repository.network.RetrofitManager;
import com.bus.business.utils.TransformUtils;
import com.bus.business.utils.UT;
import com.bus.libzxing.activity.CaptureActivity;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

public class MainActivity extends BaseActivity {
    private static final int CAMERA_OK = 2;
    public static final int CONTACTS_OK = 10000;
    private static int currIndex = 0;
    private int homeFragmentIndex = 0;
    @BindView(R.id.group)
    RadioGroup group;

    @BindView(R.id.textUnreadLabel)
    TextView textUnreadLabel;

    private ArrayList<String> fragmentTags;
    private FragmentManager fragmentManager;
    private Intent intent;
    private int searchIndex;
    private long mExitTime = 0;
    private boolean hasPush = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);

        mToolbar.setNavigationIcon(null);
        mToolbar.setNavigationOnClickListener(null);
        fragmentManager = getSupportFragmentManager();
        initData();
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_home:
                        chageIndex(0);
                        break;
                    case R.id.foot_bar_im:
                        chageIndex(1);
                        break;
                    case R.id.foot_bar_wan:
                        chageIndex(2);
                        break;
                    case R.id.foot_bar_financial:
                        chageIndex(3);
                        break;
                    case R.id.main_footbar_user:
                        chageIndex(4);
                        break;
                    default:
                        break;
                }
                showFragment();
            }
        });

        showFragment();

        Bundle bundle = getIntent().getBundleExtra(Constants.EXTRA_BUNDLE);
        //如果bundle存在，取出其中的参数，启动DetailActivity
        KLog.a("hasPush---->" + hasPush);
        if (bundle != null) {
            hasPush = true;
        }
        textUnreadLabel.setVisibility(hasPush ? View.VISIBLE : View.GONE);

    }

    private void initData() {
        currIndex = 0;
        fragmentTags = new ArrayList<>(Arrays.asList("HomeFragment", "ImFragment", "InterestFragment", "FinancialFragment", "MemberFragment"));
    }

    private void chageIndex(int index) {
        setCustomTitle(index == 0 || index == 1 ? "" : setTabSelection(index));
        showOrGoneSearchRl(index == 0 || index == 1 ? View.VISIBLE : View.GONE);
        showOrGoneLogo(index == 2 ? View.VISIBLE : View.GONE);
        if (index == 1) {
            hasPush = false;
            textUnreadLabel.setVisibility(View.GONE);
            EventBus.getDefault().post(new JoinToMeetingEvent());
        }

        currIndex = index;
        showFragment();
    }

    @OnClick(R.id.choice_search_layout)
    public void toSearch(View v) {
        intent = new Intent(MainActivity.this, SearchActivity.class);
        if (currIndex == 1) {
            searchIndex = NewsType.TYPE_REFRESH_HUIWU;
        } else {
            searchIndex = homeFragmentIndex == 0 ? NewsType.TYPE_REFRESH_XUNXI : NewsType.TYPE_REFRESH_XIEHUI;
        }
        intent.putExtra(NewsType.TYPE_SEARCH, searchIndex);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        KLog.e("requestCode--->" + requestCode);
        switch (requestCode) {
            case CAMERA_OK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //这里已经获取到了摄像头的权限，想干嘛干嘛了可以
                    startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);
                } else {
                    //这里是拒绝给APP摄像头权限，给个提示什么的说明一下都可以。
                    UT.show("请手动打开相机权限");
                }
                break;
            case CONTACTS_OK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(MainActivity.this, AddressListActivity.class));
                } else {
                    UT.show("请手动打开读取通讯录权限");
                }
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.choice_qr_scan)
    public void toCapture(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            /**
             * 请求权限是一个异步任务  不是立即请求就能得到结果 在结果回调中返回
             */
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_OK);
        } else {
            startActivity(new Intent(MainActivity.this, CaptureActivity.class));
        }

    }

    private String setTabSelection(int index) {
        switch (index) {
            case 2:
                return "万花筒";
            case 3:
                return "金融";
            case 4:
                return "我的";
        }
        return "";
    }

    private void showFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if (fragment == null) {
            fragment = instantFragment(currIndex);
        }
        for (int i = 0; i < fragmentTags.size(); i++) {
            Fragment f = fragmentManager.findFragmentByTag(fragmentTags.get(i));
            if (f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment, fragmentTags.get(currIndex));
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0:
                return new MainPagerFragment();
            case 1:
//                return new MeetingFragment();
                return new NewMeetingFragment();
            case 2:
                return new WanFragment();
//                return new WanFragment2();
            case 3:
                return new ExpertFragment();
            case 4:
                return new MineFragment();
            default:
                return null;
        }
    }


    @Subscribe
    public void onEventMainThread(ChangeSearchStateEvent event1) {
        if (event1.getMsg() == 3) {
            hasPush = true;
            textUnreadLabel.setVisibility(View.VISIBLE);

            return;
        }
        homeFragmentIndex = event1.getMsg();
        String msg = "onEventMainThread收到了消息：" + event1.getMsg();
        KLog.d("harvic", msg);
        //    UT.show(msg);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //扫描二维码，返回的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
//            UT.show(scanResult);
            signInMeeting(scanResult);
        }

    }

    private void signInMeeting(String meetingId) {
        RetrofitManager.getInstance(1).signInMeeting(meetingId)
                .compose(TransformUtils.<BaseRspObj>defaultSchedulers())
                .subscribe(new Subscriber<BaseRspObj>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseRspObj baseRspObj) {
                        if (baseRspObj.getHead().getRspCode().equals("0"))
                            EventBus.getDefault().post(new CheckMeetingStateEvent());
                        UT.show(baseRspObj.getHead().getRspMsg());
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {//
                // 如果两次按键时间间隔大于2000毫秒，则不退出
                Toast.makeText(this, getResources().getString(R.string.second_back_hint), Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();// 更新mExitTime
            } else {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
