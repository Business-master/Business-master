package com.ristone.businessasso.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ristone.businessasso.R;
import com.ristone.businessasso.mvp.entity.AssisBean;
import com.ristone.businessasso.mvp.entity.QuestionnaireBean;

import java.util.List;

/**
 * Created by ATRSnail on 2017/5/10.
 */

public class QuestionnaireAdapter extends BaseQuickAdapter<QuestionnaireBean> {
    public QuestionnaireAdapter(int layoutResId, List<QuestionnaireBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, QuestionnaireBean questionnaireBean) {
        baseViewHolder.setText(R.id.rl_1,questionnaireBean.getName());
    }
}
