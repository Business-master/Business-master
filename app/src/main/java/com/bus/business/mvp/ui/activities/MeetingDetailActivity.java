package com.bus.business.mvp.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.view.MotionEvent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.bus.business.R;
import com.bus.business.common.Constants;
import com.bus.business.mvp.entity.MeetingBean;
import com.bus.business.mvp.entity.MeetingDetailBean;
import com.bus.business.mvp.entity.MeetingFileBean;
import com.bus.business.mvp.entity.response.RspMeetingDetailBean;
import com.bus.business.mvp.entity.response.RspMeetingFileBean;
import com.bus.business.mvp.entity.response.base.BaseRspObj;
import com.bus.business.mvp.event.CheckMeetingStateEvent;
import com.bus.business.mvp.event.MeetingDetailEvent;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.mvp.ui.activities.base.CheckPermissionsActivity;
import com.bus.business.mvp.ui.activities.base.LocationActivity;
import com.bus.business.mvp.ui.adapter.DownAdapter;
import com.bus.business.mvp.ui.adapter.MeetingsAdapter;
import com.bus.business.mvp.view.CustomGridView;
import com.bus.business.repository.network.RetrofitManager;
import com.bus.business.utils.ApplyUtils;
import com.bus.business.utils.DateUtil;
import com.bus.business.utils.SystemUtils;
import com.bus.business.utils.TransformUtils;
import com.bus.business.utils.UT;
import com.bus.business.widget.URLImageGetter;
import com.bus.libzxing.activity.CaptureActivity;
import com.socks.library.KLog;
import com.bus.business.utils.SystemUtils;
import com.bus.business.utils.UT;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

import static com.bus.business.mvp.entity.MeetingBean.MEETINGBEAN;
import static com.bus.business.mvp.entity.MeetingBean.MEETINGPOS;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/10
 */
public class MeetingDetailActivity extends CheckPermissionsActivity {

    @Inject
    Activity mActivity;

    @BindView(R.id.news_detail_title_tv)
    TextView mTitle;
    @BindView(R.id.tv_publish_date)
    TextView mPubDate;
    @BindView(R.id.tv_join_address)
    TextView mJoinAddress;
    @BindView(R.id.tv_join_date)
    TextView mJoinDate;
    @BindView(R.id.news_detail_body_tv)
    TextView mNewsDetailBodyTv;


//    @BindView(R.id.cancel_apply)
//    Button cancel_apply;
//    @BindView(R.id.btn_apply)
//    Button btn_apply;
//    @BindView(R.id.btn_leave)
//    Button btn_leave;

//    @BindView(R.id.progress_bar)
//    ProgressBar mProgressBar;


    @BindView(R.id.sign_meeting_detail)
    TextView sign_meeting_detail;
    @BindView(R.id.apply_meeting_detail)
    TextView apply_meeting_detail;
    @BindView(R.id.leave_meeting_detail)
    TextView leave_meeting_detail;
    @BindView(R.id.tv_publish_address)
    TextView tv_publish_address;
   @BindView(R.id.bottom_meeting_detail)
   LinearLayout bottom_meeting_detail;
    @BindView(R.id.down_fj)
    CustomGridView down_fj;//下载附件
    DownAdapter downAdapter;
    @BindView(R.id.map_loc)
    LinearLayout map_loc;//地图导航--提供地点,跳转APP或网页

    public static final int Location_OK = 3;//定位权限的请求标志


    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;


    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.map_rl)
    RelativeLayout map_rl;
//    @BindView(R.id.btn)
//    Button btn;
    private AMap aMap;


    private MeetingBean meetingBean;

    /**
     * 地图定位参数
     */
    protected AMapLocation location;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    private SweetAlertDialog pDialog;



    private URLImageGetter mUrlImageGetter;//会议详情内容的图片

    @Override
    public int getLayoutId() {
//        return R.layout.activity_meeting_detail;
        return R.layout.activity_meeting_detail1;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {

        EventBus.getDefault().register(this);
        meetingBean = (MeetingBean) getIntent().getSerializableExtra(MeetingBean.MEETINGBEAN);

//        mProgressBar.setVisibility(View.GONE);
        setCustomTitle("会务详情");
        showOrGoneSearchRl(View.GONE);
        initBottom_Map(meetingBean);



//        stateStr = ApplyUtils.getInstance().initHide(cancel_apply,btn_apply,btn_leave,meetingBean.getJoinType());
//        if(meetingBean.getJoinType()==0){
//            btn_apply.setTextColor(getResources().getColor(R.color.text_color_rectange));
//            btn_apply.setBackgroundResource(R.drawable.btn_rectange);
//        }
//        btn_apply.setText(stateStr);
//
//
//        cancel_apply.setOnClickListener(new ApplyUtils.MyClick(meetingBean,this));
//        btn_apply.setOnClickListener(new ApplyUtils.MyClick(meetingBean,this));
//        btn_leave.setOnClickListener(new ApplyUtils.MyClick(meetingBean,this));

        mTitle.setText(meetingBean.getMeetingName());
        mJoinDate.setText("持续时间:     "+meetingBean.getDuration()+"天");
        mPubDate.setText("开始时间:     "+DateUtil.getCurGroupDay(meetingBean.getMeetingTime()));
        tv_publish_address.setText("会议来源:     "+meetingBean.getUserOrganization());
        mJoinAddress.setText("地        址:      "+meetingBean.getMeetingLoc());

        mUrlImageGetter = new URLImageGetter(mNewsDetailBodyTv, meetingBean.getMeetingContent(), 2);
        mNewsDetailBodyTv.setText(Html.fromHtml(meetingBean.getMeetingContent(), mUrlImageGetter, null));
//        mNewsDetailBodyTv.setText(meetingBean.getMeetingContent());

        mapView.onCreate(savedInstanceState);

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (TextUtils.isEmpty(meetingBean.getMeetingLoc())) {
//                    UT.show("会议地点错误");
//                    return;}
//                if (SystemUtils.isInstallByread(Constants.AMAP_PACKAGENAME)) {
//                    SystemUtils.openGaoDeMap(MeetingDetailActivity.this, meetingBean.getMeetingLoc());
//                } else if (SystemUtils.isInstallByread(Constants.BAIDUMAP_PACKAGENAME)) {
//                    SystemUtils.openBaiduMap(MeetingDetailActivity.this, meetingBean.getMeetingLoc());
//                } else {
//                    Uri uri = Uri.parse("http://api.map.baidu.com/geocoder?address=" + meetingBean.getMeetingLoc() + "&output=html&src=yourCompanyName|yourAppName");
//                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(it);
//                }
//            }
//            }});
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    scrollView.requestDisallowInterceptTouchEvent(false);
                } else {
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
            }

        });
       initDown_fj();


        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("正在定位...");
        pDialog.setCancelable(false);
        initLocation();
    }


    private void initBottom_Map(MeetingBean meet) {
        if (meet.getJoinType()==2){
            if (!TextUtils.isEmpty(meet.getLatitude()) || !TextUtils.isEmpty(meet.getLongitude())) {
              double  latitude = Double.valueOf(meet.getLatitude());
                double longitude = Double.valueOf(meet.getLongitude());
                KLog.a("纬度"+Double.valueOf(meet.getLatitude())+"经度"+Double.valueOf(meet.getLongitude()));
                init(latitude,longitude,meet.getMeetingLoc());
                map_rl.setVisibility(View.VISIBLE);
            }else {
                map_rl.setVisibility(View.GONE);
            }
        }else {
            map_rl.setVisibility(View.GONE);
        }
        switch (Integer.valueOf(meet.getStatus())){
            case 0:
                initJoinType(meet.getJoinType(),0);
                break;
            case 1:
                initJoinType(meet.getJoinType(),1);
                break;
            case 2:
                bottom_meeting_detail.setVisibility(View.GONE);
                break;
        }
    }
    private void initBottom_Map(MeetingDetailBean meet) {
        if (Integer.valueOf(meet.getJoinType())==2){
            if (!TextUtils.isEmpty(meet.getLatitude()) || !TextUtils.isEmpty(meet.getLongitude())) {
                double  latitude = Double.valueOf(meet.getLatitude());
                double longitude = Double.valueOf(meet.getLongitude());
                KLog.a("纬度"+Double.valueOf(meet.getLatitude())+"经度"+Double.valueOf(meet.getLongitude()));
                init(latitude,longitude,meet.getMeetingLoc());
                map_rl.setVisibility(View.VISIBLE);
            }else {
                map_rl.setVisibility(View.GONE);
            }
        }else {
            map_rl.setVisibility(View.GONE);
        }
        switch (Integer.valueOf(meet.getStatus())){
            case 0:
                initJoinType(Integer.valueOf(meet.getJoinType()),0);
                break;
            case 1:
                initJoinType(Integer.valueOf(meet.getJoinType()),1);
                break;
            case 2:
                bottom_meeting_detail.setVisibility(View.GONE);
                break;
        }
    }

    private void getJoinType() {
        RetrofitManager.getInstance(1).getMeetingDetail(String.valueOf(meetingBean.getId()))
                .compose(TransformUtils.<RspMeetingDetailBean>defaultSchedulers())
                .subscribe(new Subscriber<RspMeetingDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RspMeetingDetailBean rspMeetingDetailBean) {
                      KLog.a("****"+rspMeetingDetailBean.toString());
                        MeetingDetailBean meetingDetailBean=rspMeetingDetailBean.getBody().getMeetingDatail().get(0);
                        initBottom_Map(meetingDetailBean);
                    }
                });
    }

    private void initDown_fj() {
        downAdapter = new DownAdapter(mActivity);
        down_fj.setAdapter(downAdapter);
        RetrofitManager.getInstance(1).getMeetingFileList(String.valueOf(meetingBean.getId()))
                .compose(TransformUtils.<RspMeetingFileBean>defaultSchedulers())
                .subscribe(new Subscriber<RspMeetingFileBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RspMeetingFileBean rspMeetingFileBean) {
                        List<MeetingFileBean> list=rspMeetingFileBean.getBody().getFileList();
                        downAdapter.setList(list);
                        downAdapter.notifyDataSetChanged();
                    }
                });

    }


    private void initJoinType(int joinType,int status) {
        switch (joinType){
            case 0 :
                if (status==0){
                    initBottom("签到","报名","请假",
                            R.color.color_cccccc, R.color.color_0dadd5,R.color.color_0dadd5 ,false,true,true);
                }else {
                    initBottom("签到","报名","请假",
                            R.color.color_cccccc, R.color.color_cccccc,R.color.color_cccccc ,false,false,false);
                }
                break;
            case 1 :
            case 3:
            case 4:
                initBottom("签到","已报名","请假",
                        R.color.color_0dadd5, R.color.color_cccccc,R.color.color_cccccc ,true,false,false);
                break;
            case 2 :
                initBottom("已签到","已报名","请假",
                        R.color.color_cccccc, R.color.color_cccccc,R.color.color_cccccc ,false,false,false);
                break;
            case 9 :
                initBottom("助理签到","已报名","请假",
                        R.color.color_cccccc, R.color.color_cccccc,R.color.color_cccccc ,false,false,false);
                break;
            case 5 :
                initBottom("签到","报名","已请假",
                        R.color.color_cccccc, R.color.color_cccccc,R.color.color_cccccc ,false,false,false);
                break;
            case 7 :
            case 8 :
                initBottom("签到","报名","请假",
                        R.color.color_cccccc, R.color.color_cccccc,R.color.color_cccccc ,false,false,false);
                break;
            }
        }






    /**
     * 初始化AMap对象
     * @param location1
     * @param mLatitude
     * @param mLongitude
     */
    private void init( double mLatitude, double mLongitude,String location1) {
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
        aMap.setMapLanguage(AMap.CHINESE);
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(mLatitude, mLongitude), 14, 30, 0)));

        LatLng latLng = new LatLng(mLatitude, mLongitude);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
//        markerOption.title("北京市").snippet(location);

        markerOption.draggable(true);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.gps_point)));
        aMap.addMarker(markerOption);

    }
    private void initBottom(String sign, String apply, String leave, int color1, int color2, int color13
    ,boolean sign_use,boolean apply_use,boolean leave_use) {
        sign_meeting_detail.setText(sign);
        apply_meeting_detail.setText(apply);
        leave_meeting_detail.setText(leave);

        sign_meeting_detail.setTextColor(getResources().getColor(color1));
        apply_meeting_detail.setTextColor(getResources().getColor(color2));
        leave_meeting_detail.setTextColor(getResources().getColor(color13));

        sign_meeting_detail.setClickable(sign_use);
        apply_meeting_detail.setClickable(apply_use);
        leave_meeting_detail.setClickable(leave_use);
    }

    @OnClick({R.id.sign_meeting_detail,R.id.apply_meeting_detail,R.id.leave_meeting_detail,R.id.map_loc})
    public void onClick2(View v){
        switch (v.getId()){
            case R.id.sign_meeting_detail :
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    /**
                     * 请求权限是一个异步任务  不是立即请求就能得到结果 在结果回调中返回
                     */
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MainActivity.CAMERA_OK);
                } else {
                    startLocation();
                }
                break;
            case R.id.apply_meeting_detail :
                Intent intent = new Intent(this,ApplyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(MeetingBean.MEETINGBEAN,meetingBean);
                bundle.putInt("index",1);
                intent.putExtras(bundle);
                if(meetingBean!=null){
                    startActivity(intent);
                }
                break;
            case R.id.leave_meeting_detail :
                createDialog(5,"请输入请假的原因");
                break;
            case R.id.map_loc :
                //地图导航
                if (TextUtils.isEmpty(meetingBean.getMeetingLoc())) {
                    UT.show("会议地点错误");
                    return;}
                openMap();
                break;
        }
    }

    private void openMap() {

        if (SystemUtils.isInstallByread(Constants.AMAP_PACKAGENAME)) {
                    SystemUtils.openGaoDeMap(MeetingDetailActivity.this, meetingBean.getMeetingLoc());
//            SystemUtils.openGaoDeMap(MeetingDetailActivity.this, "奥林匹克森林公园");
        }
        else
                if (SystemUtils.isInstallByread(Constants.BAIDUMAP_PACKAGENAME)) {
//                    SystemUtils.openBaiduMap("奎科大厦",MeetingDetailActivity.this);
                    SystemUtils.openBaiduMap(meetingBean.getMeetingLoc(),MeetingDetailActivity.this);
                }
        else
       if (SystemUtils.isInstallByread(Constants.TencentMAP_PACKAGENAME)) {
                    SystemUtils.openTencentMap(MeetingDetailActivity.this, meetingBean.getMeetingLoc());
//            SystemUtils.openTencentMap(MeetingDetailActivity.this, "奥林匹克森林公园");
        }

             else
                 {
                    Uri uri = Uri.parse("http://api.map.baidu.com/geocoder?address=" + meetingBean.getMeetingLoc() + "&output=html&src=yourCompanyName|yourAppName");
//                    Uri uri = Uri.parse("http://api.map.baidu.com/geocoder?address=" + "奎科大厦" + "&output=html&src=yourCompanyName|yourAppName");
//                    Uri uri = Uri.parse("http://api.map.baidu.com/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving&region=西安&output=html&src=yourCompanyName|yourAppName");
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        KLog.e("requestCode--->" + requestCode);
        switch (requestCode) {
            case MainActivity.CAMERA_OK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //这里已经获取到了摄像头的权限，想干嘛干嘛了可以
                    startLocation();
                } else {
                    //这里是拒绝给APP摄像头权限，给个提示什么的说明一下都可以。
                    UT.show("请手动打开相机权限");
                }
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onEventMainThread(MeetingDetailEvent event){
        /**
         *1 报名 2请假 3取消报名
         */
          switch (event.getPos()){
              case 1:
                  initBottom("签到","已报名","请假",
                          R.color.color_0dadd5, R.color.color_cccccc,R.color.color_cccccc ,true,false,false);
//                  btn_apply.setVisibility(View.INVISIBLE);
//                  cancel_apply.setVisibility(View.VISIBLE);
//                  btn_leave.setVisibility(View.VISIBLE);
                  break;
              case 2:
                  initBottom("签到","报名","已请假",
                          R.color.color_cccccc, R.color.color_cccccc,R.color.color_cccccc ,false,false,false);
//                  btn_apply.setText("已请假");
//                  btn_apply.setVisibility(View.VISIBLE);
//                  cancel_apply.setVisibility(View.INVISIBLE);
//                  btn_leave.setVisibility(View.INVISIBLE);
                  break;
              case 3:
//                  btn_apply.setText("已取消报名");
//                  btn_apply.setVisibility(View.VISIBLE);
//                  cancel_apply.setVisibility(View.INVISIBLE);
//                  btn_leave.setVisibility(View.INVISIBLE);
                  getJoinType();
                  break;
          }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        destroyLocation();
        mapView.onDestroy();
        super.onDestroy();

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    public void createDialog(final int i,String title) {
        final EditText editText =new EditText(this);
        final AlertDialog dialog =new AlertDialog.Builder(this)
                .setView(editText)
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setTitle(title).create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String cause = editText.getText().toString().trim();
                if (TextUtils.isEmpty(cause)){
                    UT.show("输入不能为空");
                    return;
                }
                cancel_leave(i,cause);
                dialog.dismiss();
            }
        });
    }

    //请假 或者取消报名
    public void cancel_leave(final int joinType, String cause) {
        RetrofitManager.getInstance(1).joinMeeting(meetingBean.getId(),joinType,cause)
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
                            if (joinType==5){
                                EventBus.getDefault().post(new MeetingDetailEvent(2));
                            }
                        }
                        UT.show(responseBody.getHead().getRspMsg());
                    }
                });
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
                UT.show("所在地--->"+loc.getAddress());
                //解析定位结果
                startActivityForResult(new Intent(MeetingDetailActivity.this, CaptureActivity.class), 0);
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
                        if (baseRspObj.getHead().getRspCode().equals("0"))
                            EventBus.getDefault().post(new CheckMeetingStateEvent());
                            EventBus.getDefault().post(new MeetingDetailEvent(3));
                        UT.show(baseRspObj.getHead().getRspMsg());
                    }
                });
    }





}
