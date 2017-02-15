package com.bus.business.mvp.view;

import com.bus.business.common.LoadNewsType;
import com.bus.business.mvp.entity.response.base.BaseNewBean;
import com.bus.business.mvp.view.base.BaseView;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
public interface BusinessView extends BaseView{
    void setBusinessList(List<BaseNewBean> newsSummary, @LoadNewsType.checker int loadType);
}
