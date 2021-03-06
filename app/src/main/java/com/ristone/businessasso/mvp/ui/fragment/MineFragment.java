package com.ristone.businessasso.mvp.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ristone.businessasso.App;
import com.ristone.businessasso.R;
import com.ristone.businessasso.common.UsrMgr;
import com.ristone.businessasso.mvp.entity.NationBean;
import com.ristone.businessasso.mvp.entity.UserBean;
import com.ristone.businessasso.mvp.entity.response.RspNationBean;
import com.ristone.businessasso.mvp.entity.response.base.BaseRspObj;
import com.ristone.businessasso.mvp.ui.activities.AboutActivity;
import com.ristone.businessasso.mvp.ui.activities.AddressListActivity;
import com.ristone.businessasso.mvp.ui.activities.AssisManActivity;
import com.ristone.businessasso.mvp.ui.activities.LoginActivity;
import com.ristone.businessasso.mvp.ui.fragment.base.BaseFragment;
import com.ristone.businessasso.mvp.view.DialogUtils;
import com.ristone.businessasso.mvp.view.WheelView;
import com.ristone.businessasso.repository.network.RetrofitManager;
import com.ristone.businessasso.utils.FileUtil;
import com.ristone.businessasso.utils.MethodsCompat;
import com.ristone.businessasso.utils.TransformUtils;
import com.ristone.businessasso.utils.UT;
import com.socks.library.KLog;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

import static com.ristone.businessasso.mvp.ui.activities.MainActivity.CONTACTS_OK;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/24
 * 我的   碎片
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.tv_use_name)
    TextView mUseName;
    @BindView(R.id.tv_use_phone)
    TextView mUsePhone;
    @BindView(R.id.tv_cache_size)
    TextView mCacheSize;
    @BindView(R.id.tv_companyName)
    TextView tv_companyName;
    @BindView(R.id.tv_sgslPosition)
    TextView tv_sgslPosition;
    @BindView(R.id.tv_qgslPosition)
    TextView tv_qgslPosition;
    @BindView(R.id.tv_position)
    TextView tv_position;
    @BindView(R.id.tv_qShPosition)
    TextView tv_qShPosition;
    @BindView(R.id.tv_sShPosition)
    TextView tv_sShPosition;


    @BindView(R.id.isAssis_ll)
    LinearLayout isAssis_ll;


    private UserBean userBean;

    @Inject
    Activity mActivity;

//    private   String[] ZHIWU ;
    private  List<NationBean> dutyList=new ArrayList<>();
    private String dutyCode;

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        userBean = UsrMgr.getUseInfo();
        mUseName.setText(userBean.getNiceName());
        mUsePhone.setText(userBean.getPhoneNo());
        tv_companyName.setText(userBean.getCompanyName());
        tv_sgslPosition.setText(userBean.getSgslPosition());
        tv_qgslPosition.setText(userBean.getQgslPosition());

        if(!TextUtils.isEmpty(userBean.getsShPosition()))
        tv_sShPosition.setText(userBean.getsShPosition());
        if(!TextUtils.isEmpty(userBean.getqShPosition()))
        tv_qShPosition.setText(userBean.getqShPosition());
        tv_position.setText(userBean.getPosition());
        caculateCacheSize();
        if (userBean.getIsAssistant()==1){
            isAssis_ll.setVisibility(View.VISIBLE);
        }else {
            isAssis_ll.setVisibility(View.GONE);
        }

//        Drawable dra = getResources().getDrawable(R.drawable.apply_xiabiao);
//        if(userBean.getIsAssistant()==1){
//            tv_position.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
//            tv_position.setClickable(false);
//        }else {
//            tv_position.setCompoundDrawablesWithIntrinsicBounds(null,null,dra,null);
//            tv_position.setClickable(true);
//            initData();
//        }

//        initData();
    }

    private void initData() {
//        RetrofitManager.getInstance(1).getAllPosition()
//                .compose(TransformUtils.<RspNationBean>defaultSchedulers())
//                .subscribe(new Subscriber<RspNationBean>() {
//                    @Override
//                    public void onCompleted() {
//                        KLog.d();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        KLog.e(e.toString());
//                    }
//
//                    @Override
//                    public void onNext(RspNationBean rspNationBean) {
//                        KLog.d(rspNationBean.toString());
//                        if ("0".equals(rspNationBean.getHead().getRspCode())){
//                            dutyList = (List<NationBean>) rspNationBean.getBody().getList();
////                            ZHIWU = new String[dutyList.size()];
//                            for (int i = 0; i <dutyList.size() ; i++) {
//                                NationBean nationBean = dutyList.get(i);
////                               ZHIWU[i]=nationBean.getName();
//                            }
//                        }
//                    }
//                });
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_mine;
    }


    @OnClick({R.id.tv_account_manager, R.id.about_us, R.id.rl_clear_cache
            , R.id.tv_logout,R.id.tv_assis_manager,R.id.tv_position})
//            , R.id.tv_logout, R.id.tv_address_list,R.id.tv_assis_manager,R.id.tv_position})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_account_manager:
                userBean.intentToClass(mActivity);
                break;
//            case R.id.tv_address_list:
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    /**
//                     * 请求权限是一个异步任务  不是立即请求就能得到结果 在结果回调中返回
//                     */
//                    mActivity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CONTACTS_OK);
//                } else {
//                    startActivity(new Intent(mActivity, AddressListActivity.class));
//                }
//
//                break;
            case R.id.about_us:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.rl_clear_cache:
                initDialog("确定清除？", "取消", "确定", new SweetAlertDialog.OnSweetClickListener() {

                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        FileUtil.cleanCacheData(mActivity);
                        mCacheSize.setText("0KB");
                        sweetAlertDialog.dismiss();
                    }
                });

                break;
            case R.id.tv_logout:
                initDialog("确定退出？", "取消", "确定", new SweetAlertDialog.OnSweetClickListener() {

                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        UsrMgr.clearUserInfo();
                        startActivity(new Intent(mActivity, LoginActivity.class));
                        mActivity.finish();
                    }
                });

                break;
            case R.id.tv_assis_manager:
                        startActivity(new Intent(mActivity, AssisManActivity.class));
                break;
            case R.id.tv_position:
//                if (ZHIWU!=null&&ZHIWU.length>0)
//                initWheelView("请选择职务",ZHIWU);
                 choiceDutyDialog();
                break;
        }
    }

    private void choiceDutyDialog() {
        final EditText editText = new EditText(mActivity);
        final  AlertDialog dialog = new AlertDialog.Builder(mActivity)
                .setTitle("修改职务")
                .setView(editText)
                .setPositiveButton("确定",null)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String duty = editText.getText().toString().trim();
                if (TextUtils.isEmpty(duty)){
                    UT.show("输入不能为空");
                    return;
                }
                changePosition2(dialog,duty);
            }
        });
    }


    private void changePosition2(final DialogInterface dialog, final String duty) {

        if (!TextUtils.isEmpty(duty)){
            //改变职务
            RetrofitManager.getInstance(1).getChangeAssisObservable(duty)
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
                        public void onNext(BaseRspObj baseRspObj) {
                            KLog.d(baseRspObj.toString());
                            if (baseRspObj.getHead().getRspCode().equals("0")){
                                tv_position.setText(duty);
                                userBean.setPosition(duty);
                                UsrMgr.cacheUserInfo(new Gson().toJson(userBean));
                                dialog.dismiss();
                            }
                            UT.show(baseRspObj.getHead().getRspMsg());
                        }
                    });

        }
    }


//    private void initWheelView(final String title, String[] planets) {
//        final String[] selecItem = {""};
//        View outerView = LayoutInflater.from(mActivity).inflate(R.layout.wheel_view, null);
//        final WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
//        wv.setOffset(2);
//        wv.setItems(Arrays.asList(planets));
//        wv.setSeletion(0);
//        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
//            @Override
//            public void onSelected(int selectedIndex, String item) {
//                selecItem[0] =item;
//            }
//        });
//
//        new DialogUtils.Builder(mActivity)
//                .setTitle(title)
//                .setContentView(outerView)
//                .setCancelable(false)
//                .setPositiveButton("确定",new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //如果没有选择，默认选择第一个
//                        if ("".equals(selecItem[0])){
//                            selecItem[0]= wv.getSeletedItem();
//                        }
//
//                            if ("请选择职务".equals(title)){
//                                changePosition(selecItem[0],dialog);
//                            }
//
//
//
//                    }
//                }).create().show();
//    }

//    private void changePosition(final String duty, final DialogInterface dialog) {
//
//        //遍历 根据 中文 职务 查找 code
//        if (!TextUtils.isEmpty(duty)& dutyList!=null &dutyList.size()>0){
//            for (int i = 0; i <dutyList.size() ; i++) {
//                NationBean nationBean = dutyList.get(i);
//                if (duty.equals(nationBean.getName())){
//                    dutyCode = nationBean.getCode();
//                    break;
//                }
//            }
//        }
//
////         if (!TextUtils.isEmpty(dutyCode)&!(userBean.getIsAssistant()==1)){
//         if (!TextUtils.isEmpty(dutyCode)){
//            //改变职务
//            RetrofitManager.getInstance(1).getChangeAssisObservable(dutyCode)
//                    .compose(TransformUtils.<BaseRspObj>defaultSchedulers())
//                    .subscribe(new Subscriber<BaseRspObj>() {
//                        @Override
//                        public void onCompleted() {
//                            KLog.d();
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            KLog.e(e.toString());
//                        }
//
//                        @Override
//                        public void onNext(BaseRspObj baseRspObj) {
//                            KLog.d(baseRspObj.toString());
//                            if (baseRspObj.getHead().getRspCode().equals("0")){
//                                tv_position.setText(duty);
//                                dialog.dismiss();
//                            }
//                            UT.show(baseRspObj.getHead().getRspMsg());
//                        }
//                    });
//
//        }
//    }

    /**
     * 计算缓存的大小
     */
    private void caculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File cacheDir = mActivity.getCacheDir();

        fileSize += FileUtil.getDirSize(cacheDir);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (App.isMethodsCompat(Build.VERSION_CODES.FROYO)) {
            File externalCacheDir = MethodsCompat
                    .getExternalCacheDir(mActivity);
            fileSize += FileUtil.getDirSize(externalCacheDir);
        }
        if (fileSize > 0)
            cacheSize = FileUtil.formatFileSize(fileSize);
        mCacheSize.setText(cacheSize);
    }


    private void initDialog(String title, String cancelStr, String confirmStr, SweetAlertDialog.OnSweetClickListener clickListener) {
        new SweetAlertDialog(mActivity, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
//                .setContentText("Won't be able to recover this file!")
                .setCancelText(cancelStr)
                .setConfirmText(confirmStr)
                .showCancelButton(true)
                .setConfirmClickListener(clickListener)
                .show();
    }
}
