package com.bus.business.mvp.view;

import com.bus.business.common.LoadNewsType;
import com.bus.business.mvp.entity.AreaBean;
import com.bus.business.mvp.entity.AssisBean;
import com.bus.business.mvp.view.base.BaseView;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 * 区域视图
 */
public interface AreaView extends BaseView{
    //区域列表
    void setAreaBeanList(List<AreaBean> areaBeanList, @LoadNewsType.checker int loadType);

    //组织列表
    void setChambreBeanList(List<AreaBean> chambrelist, @LoadNewsType.checker int loadType);
}
