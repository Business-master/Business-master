package com.bus.business.mvp.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bus.business.App;
import com.bus.business.R;
import com.bus.business.common.ApiConstants;
import com.bus.business.mvp.entity.DropBean;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.utils.UT;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bus.business.mvp.entity.DropBean.DROP_BEAN;

/**
 * 贷款详情页面
 */

public class DropDetailActivity extends BaseActivity {

    private DropBean dropBean;

    private ImageView img;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.tv_loanCode)
    TextView tv_loanCode;
    @BindView(R.id.tv_lend_money)
    TextView tv_lend_money;
    @BindView(R.id.tv_loanIntroduction)
    TextView tv_loanIntroduction;
    @BindView(R.id.tv_cashRate)
    TextView tv_cashRate;
    @BindView(R.id.tv_productDesp)
    TextView tv_productDesp;
    @BindView(R.id.tv_load)
    TextView tv_load;
    @BindView(R.id.tv_pledgeCode)
    TextView tv_pledgeCode;
    @Override
    public int getLayoutId() {
        return R.layout.activity_drop_detail;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        setCustomTitle("贷款详情");
        showOrGoneSearchRl(View.GONE);

        dropBean = (DropBean) getIntent().getSerializableExtra(DROP_BEAN);
        img = (ImageView) findViewById(R.id.img_icon_logo);
        inputData();
    }

    private void inputData() {

        KLog.a("img_url--》"+ApiConstants.NETEAST_HOST+dropBean.getLogoUrl());

        name.setText(dropBean.getProductName()+" - 产品名称");
        tv_pledgeCode.setText(dropBean.getPledgeCode());
        tv_load.setText(dropBean.getPledgeCode());
        address.setText(dropBean.getLendingTime()+"");
        tv_loanCode.setText(dropBean.getLoanCode()+"年");
        if(!TextUtils.isEmpty(dropBean.getLendingMoney()))
        tv_lend_money.setText(dropBean.getLendingMoney()+"万元");
        tv_loanIntroduction.setText(dropBean.getReplayCode());
        tv_cashRate.setText(dropBean.getCashRate()+"%");
        tv_productDesp.setText(dropBean.getLoanIntroduction());

        Glide.with(App.getAppContext()).load(ApiConstants.NETEAST_HOST+dropBean.getLogoUrl())
                .asBitmap() // gif格式有时会导致整体图片不显示体图片不显示，貌似有冲突
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.image_place_holder)
                .error(R.drawable.ic_load_fail)
                .into(img);
    }

    @OnClick(R.id.btnSure)
    public void submitClick(View view) {
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        final EditText et = new EditText(this);
        et.setHint("姓名");
        final EditText et1 = new EditText(this);
        et1.setHint("手机号");
        ll.addView(et);
        ll.addView(et1);

        new AlertDialog.Builder(this).setTitle("申请")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(ll)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "内容不能为空！" + input, Toast.LENGTH_LONG).show();
                        }else {
                            UT.show("提交成功");
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

}
