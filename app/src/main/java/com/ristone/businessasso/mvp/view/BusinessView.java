package com.ristone.businessasso.mvp.view;

import com.ristone.businessasso.common.LoadNewsType;

import com.ristone.businessasso.mvp.entity.BusinessBean;
import com.ristone.businessasso.mvp.view.base.BaseView;

import java.util.List;


/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
public interface BusinessView extends BaseView{
    void setBusinessList(List<BusinessBean> newsSummary, @LoadNewsType.checker int loadType);
}
