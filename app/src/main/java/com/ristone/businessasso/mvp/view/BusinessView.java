package com.ristone.businessasso.mvp.view;

import com.ristone.businessasso.common.LoadNewsType;
import com.ristone.businessasso.mvp.entity.response.base.BaseNewBean;
import com.ristone.businessasso.mvp.view.base.BaseView;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
public interface BusinessView extends BaseView{
    void setBusinessList(List<BaseNewBean> newsSummary, @LoadNewsType.checker int loadType);
}
