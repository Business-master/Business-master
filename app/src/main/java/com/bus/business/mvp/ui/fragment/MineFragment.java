package com.bus.business.mvp.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bus.business.App;
import com.bus.business.R;
import com.bus.business.common.UsrMgr;
import com.bus.business.mvp.entity.UserBean;
import com.bus.business.mvp.ui.activities.AboutActivity;
import com.bus.business.mvp.ui.activities.AddressListActivity;
import com.bus.business.mvp.ui.activities.AssisManActivity;
import com.bus.business.mvp.ui.activities.LoginActivity;
import com.bus.business.mvp.ui.fragment.base.BaseFragment;
import com.bus.business.utils.FileUtil;
import com.bus.business.utils.MethodsCompat;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.bus.business.mvp.ui.activities.MainActivity.CONTACTS_OK;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/24
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

    @BindView(R.id.isAssis_ll)
    LinearLayout isAssis_ll;


    private UserBean userBean;

    @Inject
    Activity mActivity;

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
        tv_position.setText(userBean.getPosition());
        caculateCacheSize();
        if (userBean.getIsAssistant()==1){
            isAssis_ll.setVisibility(View.VISIBLE);
        }else {
            isAssis_ll.setVisibility(View.GONE);
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_mine;
    }


    @OnClick({R.id.tv_account_manager, R.id.about_us, R.id.rl_clear_cache
            , R.id.tv_logout, R.id.tv_address_list,R.id.tv_assis_manager})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_account_manager:
                userBean.intentToClass(mActivity);
                break;
            case R.id.tv_address_list:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    /**
                     * 请求权限是一个异步任务  不是立即请求就能得到结果 在结果回调中返回
                     */
                    mActivity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CONTACTS_OK);
                } else {
                    startActivity(new Intent(mActivity, AddressListActivity.class));
                }

                break;
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
        }
    }

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
