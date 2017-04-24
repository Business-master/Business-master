package com.ristone.businessasso.mvp.ui.fragment;

import android.view.View;

import com.ristone.businessasso.R;
import com.ristone.businessasso.mvp.ui.fragment.base.BaseLazyFragment;
import com.ristone.businessasso.utils.UT;

import butterknife.OnClick;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/2/19
 * 贷款优化专家  碎片
 */

public class SubmitLoanFragment extends BaseLazyFragment {
    @Override
    public void onFirstUserVisible() {

    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_submit_loan;
    }

    @OnClick(R.id.btnSure)
    public void submitClick(View view) {
        UT.show("提交成功");
    }
}
