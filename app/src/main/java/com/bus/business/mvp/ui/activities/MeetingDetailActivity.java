package com.bus.business.mvp.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
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
import android.widget.TextView;

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
import com.bus.business.mvp.entity.MeetingFileBean;
import com.bus.business.mvp.entity.response.RspMeetingFileBean;
import com.bus.business.mvp.entity.response.base.BaseRspObj;
import com.bus.business.mvp.event.JoinToMeetingEvent;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.mvp.ui.adapter.DownAdapter;
import com.bus.business.mvp.ui.adapter.MeetingsAdapter;
import com.bus.business.mvp.view.CustomGridView;
import com.bus.business.repository.network.RetrofitManager;
import com.bus.business.utils.ApplyUtils;
import com.bus.business.utils.DateUtil;
import com.bus.business.utils.SystemUtils;
import com.bus.business.utils.TransformUtils;
import com.bus.business.utils.UT;
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
import rx.Subscriber;

import static com.bus.business.mvp.entity.MeetingBean.MEETINGBEAN;
import static com.bus.business.mvp.entity.MeetingBean.MEETINGPOS;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/10
 */
public class MeetingDetailActivity extends BaseActivity{

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



//    @BindView(R.id.scroll_view)
//    NestedScrollView scrollView;

    @BindView(R.id.map)
    MapView mapView;
//    @BindView(R.id.btn)
//    Button btn;
    private AMap aMap;
    private double latitude = 39.906901;//纬度
    private double longitude = 116.397972;//经度

    private MeetingBean meetingBean;



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
        if (!TextUtils.isEmpty(meetingBean.getLatitude()) || !TextUtils.isEmpty(meetingBean.getLongitude())) {
            latitude = Double.valueOf(meetingBean.getLatitude());
            longitude = Double.valueOf(meetingBean.getLongitude());
        }

//        mProgressBar.setVisibility(View.GONE);
        setCustomTitle("会务详情");
        showOrGoneSearchRl(View.GONE);

        switch (Integer.valueOf(meetingBean.getStatus())){
            case 0:
            case 1:
                initJoinType(meetingBean.getJoinType());
                break;
            case 2:
                bottom_meeting_detail.setVisibility(View.GONE);
                break;
        }


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
        mJoinDate.setText("持续时间:     "+DateUtil.getCurGroupDay(meetingBean.getDuration()));
        mPubDate.setText("开始时间:     "+DateUtil.getCurGroupDay(meetingBean.getMeetingTime()));
        tv_publish_address.setText("会议来源:     "+meetingBean.getUserOrganization());
        mJoinAddress.setText("地        址:      "+meetingBean.getMeetingLoc());
        mNewsDetailBodyTv.setText(meetingBean.getMeetingContent());

        mapView.onCreate(savedInstanceState);
        init();
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
//        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
//            @Override
//            public void onTouch(MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    scrollView.requestDisallowInterceptTouchEvent(false);
//                } else {
//                    scrollView.requestDisallowInterceptTouchEvent(true);
//                }
//            }
//
//        });
       initDown_fj();
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


    private void initJoinType(int joinType) {
        switch (joinType){
            case 0 :
                initBottom("签到","报名","请假",
                        R.color.color_cccccc, R.color.color_0dadd5,R.color.color_0dadd5 ,false,true,true);
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
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();

        }
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
        aMap.setMapLanguage(AMap.CHINESE);
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(latitude, longitude), 14, 30, 0)));

        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title("北京市").snippet("DefaultMarker");

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
                    startActivity(new Intent(MeetingDetailActivity.this, CaptureActivity.class));
                }
                break;
            case R.id.apply_meeting_detail :
                Intent intent = new Intent(this,ApplyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(MeetingBean.MEETINGBEAN,meetingBean);
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
                if (SystemUtils.isInstallByread(Constants.AMAP_PACKAGENAME)) {
//                    SystemUtils.openGaoDeMap(MeetingDetailActivity.this, meetingBean.getMeetingLoc());
                    SystemUtils.openGaoDeMap(MeetingDetailActivity.this, "天安门");
                } else if (SystemUtils.isInstallByread(Constants.BAIDUMAP_PACKAGENAME)) {
                    SystemUtils.openBaiduMap(MeetingDetailActivity.this, "天安门");
//                    SystemUtils.openBaiduMap(MeetingDetailActivity.this, meetingBean.getMeetingLoc());
                } else {
//                    Uri uri = Uri.parse("http://api.map.baidu.com/geocoder?address=" + meetingBean.getMeetingLoc() + "&output=html&src=yourCompanyName|yourAppName");
                    Uri uri = Uri.parse("http://api.map.baidu.com/geocoder?address=" + "天安门" + "&output=html&src=yourCompanyName|yourAppName");
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                }
                break;
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        KLog.e("requestCode--->" + requestCode);
        switch (requestCode) {
            case MainActivity.CAMERA_OK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //这里已经获取到了摄像头的权限，想干嘛干嘛了可以
                    startActivityForResult(new Intent(MeetingDetailActivity.this, CaptureActivity.class), 0);
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
    public void onEventMainThread(JoinToMeetingEvent event){
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
                  break;
              case 4:
                  initBottom("已签到","已报名","请假",
                          R.color.color_cccccc, R.color.color_cccccc,R.color.color_cccccc ,false,false,false);
                  break;
          }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
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
                                EventBus.getDefault().post(new JoinToMeetingEvent(2));
                            }else if (joinType==0){
                                EventBus.getDefault().post(new JoinToMeetingEvent(3));
                            }
                        }
                        UT.show(responseBody.getHead().getRspMsg());
                    }
                });
    }

}
