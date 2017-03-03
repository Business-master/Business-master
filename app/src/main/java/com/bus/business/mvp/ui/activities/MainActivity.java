package com.bus.business.mvp.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bus.business.R;
import com.bus.business.common.Constants;
import com.bus.business.common.NewsType;
import com.bus.business.mvp.entity.NotReadBean;
import com.bus.business.mvp.entity.response.base.BaseRspObj;
import com.bus.business.mvp.event.ChangeSearchStateEvent;
import com.bus.business.mvp.event.CheckMeetingStateEvent;
import com.bus.business.mvp.event.ReadMeeting;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.mvp.ui.activities.base.CheckPermissionsActivity;
import com.bus.business.mvp.ui.fragment.ExpertFragment;
import com.bus.business.mvp.ui.fragment.MainPagerFragment;
import com.bus.business.mvp.ui.fragment.MineFragment;
import com.bus.business.mvp.ui.fragment.NewMeetingFragment;
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
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

public class MainActivity extends CheckPermissionsActivity {
    public static final int CAMERA_OK = 2;
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
    private  int notReadCounnt=0;
    private SweetAlertDialog pDialog;

    protected AMapLocation location;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();

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

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("正在定位...");
        pDialog.setCancelable(false);

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
//        textUnreadLabel.setVisibility(hasPush ? View.VISIBLE : View.GONE);
        getNotReadCount();
        initLocation();
    }

    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }


    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation loc) {
            pDialog.dismiss();
            if (null != loc) {
                location = loc;
                KLog.a("loc---->"+loc.toStr());
                UT.show("loc--->"+loc.getAddress());
                //解析定位结果
                startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);
//                String result = Utils.getLocationStr(loc);
//                tvResult.setText(result);
            } else {
                UT.show("定位失败,请重新定位");
                //tvResult.setText("定位失败，loc is null");
            }
        }
    };

    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void startLocation(){
        pDialog.show();
        //根据控件的选择，重新设置定位参数
        resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    // 根据控件的选择，重新设置定位参数
    private void resetOption() {
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(true);
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(true);
        // 设置是否单次定位
        locationOption.setOnceLocation(true);
        //设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
        locationOption.setOnceLocationLatest(true);
        //设置是否使用传感器
        locationOption.setSensorEnable(true);
        //设置是否开启wifi扫描，如果设置为false时同时会停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        String strInterval = "2000";
        if (!TextUtils.isEmpty(strInterval)) {
            try{
                // 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
                locationOption.setInterval(Long.valueOf(strInterval));
            }catch(Throwable e){
                e.printStackTrace();
            }
        }

        String strTimeout = "5000";
        if(!TextUtils.isEmpty(strTimeout)){
            try{
                // 设置网络请求超时时间
                locationOption.setHttpTimeOut(Long.valueOf(strTimeout));
            }catch(Throwable e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 停止定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void stopLocation(){
        // 停止定位
        locationClient.stopLocation();
    }

    private void getNotReadCount(){
        RetrofitManager.getInstance(1).getNotReadCount()
                .compose(TransformUtils.<NotReadBean>defaultSchedulers())
                .subscribe(new Subscriber<NotReadBean>() {
                    @Override
                    public void onCompleted() {
                        KLog.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                    }

                    @Override
                    public void onNext(NotReadBean notReadBean) {
                        if ("0".equals(notReadBean.getHead().getRspCode())){

                            notReadCounnt = notReadBean.getBody().getNotReaded();
                            if (notReadCounnt>0){
                                //未读会议
                                textUnreadLabel.setVisibility(View.VISIBLE);
                                textUnreadLabel.setText(notReadCounnt+"");
                            }else {
                                textUnreadLabel.setVisibility(View.GONE);
                            }

                        }
                    }
                });
    }

    private void initData() {
        currIndex = 0;
        fragmentTags = new ArrayList<>(Arrays.asList("HomeFragment", "ImFragment", "InterestFragment", "FinancialFragment", "MemberFragment"));
    }

    private void chageIndex(int index) {
//        setCustomTitle(index == 0 || index == 1 ? "" : setTabSelection(index));
        setCustomTitle(index == 0  ? "" : setTabSelection(index));
//        showOrGoneSearchRl(index == 0 || index == 1 ? View.VISIBLE : View.GONE);
        showOrGoneSearchRl(index == 0  ? View.VISIBLE : View.GONE);
//        showOrGoneLogo(index == 2 ? View.VISIBLE : View.GONE);
        if (index == 1) {
            hasPush = false;
//            textUnreadLabel.setVisibility(View.GONE);

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
                    startLocation();
               //     startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);
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
            startLocation();
            //startActivity(new Intent(MainActivity.this, CaptureActivity.class));
        }

    }

    private String setTabSelection(int index) {
        switch (index) {
            case 1:
                return "会务";
            case 2:
//                return "万花筒";
                return "百宝箱";
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
//                return new WanFragment();
                return new WanFragment2();
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
//            textUnreadLabel.setVisibility(View.VISIBLE);

            return;
        }
        homeFragmentIndex = event1.getMsg();
        String msg = "onEventMainThread收到了消息：" + event1.getMsg();
        KLog.d("harvic", msg);
        //    UT.show(msg);

    }

    @Subscribe
    public void onEventMainThread(ReadMeeting readMeeting) {
        if (readMeeting.isRead()) {
           getNotReadCount();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
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
        KLog.a("location--->"+location.toStr());
        RetrofitManager.getInstance(1).signInMeeting(meetingId,location.getLongitude(),location.getLatitude())
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
                        KLog.a("********"+baseRspObj.toString());
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
