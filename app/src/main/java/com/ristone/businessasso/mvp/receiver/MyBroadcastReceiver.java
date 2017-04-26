package com.ristone.businessasso.mvp.receiver;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ristone.businessasso.R;
import com.ristone.businessasso.common.Constants;
import com.ristone.businessasso.mvp.event.ChangeSearchStateEvent;
import com.ristone.businessasso.mvp.ui.activities.MainActivity;
import com.ristone.businessasso.utils.SystemUtils;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/1/4
 * 极光推送广播
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    private NotificationManager nm;
    private String myValue = "";



    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }


        Bundle bundle = intent.getExtras();
//        Log.d(TAG, "onReceive - " + intent.getAction() + ", extras: " + AndroidUtil.printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的自定义消息");



        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的通知");

            receivingNotification(context, bundle);
            receiveMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了�?�知");

            openNotification(context, bundle);
            //判断app进程是否存活
//            if (SystemUtils.isAppAlive(context, "com.bus.business")) {
            if (SystemUtils.isAppAlive(context, "com.ristone.businessasso")) {
                //如果存活的话，就直接启动DetailActivity，但要考虑一种情况，就是app的进程虽然仍然在
                //但Task栈已经空了，比如用户点击Back键退出应用，但进程还没有被系统回收，如果直接启动
                //DetailActivity,再按Back键就不会返回MainActivity了。所以在启动
                //DetailActivity前，要先启动MainActivity。
                Log.i("NotificationReceiver", "the app process is alive");

                Intent mainIntent = new Intent(context, MainActivity.class);
                //将MainAtivity的launchMode设置成SingleTask, 或者在下面flag中加上Intent.FLAG_CLEAR_TOP,
                //如果Task栈中有MainActivity的实例，就会把它移到栈顶，把在它之上的Activity都清理出栈，
                //如果Task栈不存在MainActivity实例，则在栈顶创建
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle args = new Bundle();
                args.putString("meetingId", myValue);
                mainIntent.putExtra(Constants.EXTRA_BUNDLE, args);

//                Intent detailIntent = new Intent(context, MeetingDetailActivity.class);
//                Bundle bundle1 = new Bundle();
//                bundle1.putSerializable(MEETINGBEAN, new MeetingBean(Integer.valueOf(myValue)));
//                bundle1.putInt(MEETINGPOS, -1);
//                detailIntent.putExtras(bundle1);
//                Intent[] intents = {mainIntent, detailIntent};
//                context.startActivities(intents);
                context.startActivity(mainIntent);
                EventBus.getDefault().post(new ChangeSearchStateEvent(3));
            } else {
                //如果app进程已经被杀死，先重新启动app，将DetailActivity的启动参数传入Intent中，参数经过
                //SplashActivity传入MainActivity，此时app的初始化已经完成，在MainActivity中就可以根据传入             //参数跳转到DetailActivity中去了
                Log.i("NotificationReceiver", "the app process is dead");
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.ristone.businessasso");
//                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.bus.business");
                launchIntent.setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                Bundle args = new Bundle();
                args.putString("meetingId", myValue);
                launchIntent.putExtra(Constants.EXTRA_BUNDLE, args);
                context.startActivity(launchIntent);
            }

        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void openHintDialog(Context context,String message) {
       //提示会议改动弹窗
                        final View view = LayoutInflater.from(context).inflate(R.layout.main_hint_dialog,null);
                        TextView textView = (TextView) view.findViewById(R.id.ensure_mhd);
                        TextView msg = (TextView) view.findViewById(R.id.msg_mhd);
                        msg.setText(message);
//                        final AlertDialog dialog=  new AlertDialog.Builder(context)
//                                .setView(view)
//                                .setCancelable(false)
//                                .create();
////        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                        dialog.show();
//                        textView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });


        final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams para = new WindowManager.LayoutParams();
        para.height = -1;
        para.width = -1;
        para.format = 1;
        para.dimAmount=0.8f;
        para.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
//        para.type = WindowManager.LayoutParams.TYPE_TOAST;
        para.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        wm.addView(view, para);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(view);
            }});
    }

    private void receiveMessage(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String msg="";
        try {
            JSONObject jsonObject = new JSONObject(extras);
            msg = jsonObject.optString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "推送自定义信息 title : " + title+ "message : " + message+"extras : " + extras+"msg:"+msg);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (Settings.canDrawOverlays(context)){
                openHintDialog(context,msg);
            }else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }else {
            openHintDialog(context,msg);
        }
//        openHintDialog(context,msg);
    }

    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        Log.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d(TAG, "extras : " + extras);
    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        try {
            //当前的url的�?�是由，极光推�?�的服务传�?�过来的，http://www.saiermeng.com
//          {
//              url:"http://www.saiermeng.com";
//          }
            JSONObject extrasJson = new JSONObject(extras);
            myValue = extrasJson.optString("meetingId");
            KLog.a("url--->" + extras);

        } catch (Exception e) {
            Log.w(TAG, "Unexpected: extras is not a valid json", e);
            return;
        }
    }
}
