package com.bus.business.mvp.view;

import android.widget.ListView;

import com.bus.business.common.LoadNewsType;
import com.bus.business.mvp.entity.AssisBean;
import com.bus.business.mvp.entity.response.base.BaseNewBean;
import com.bus.business.mvp.view.base.BaseView;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
public interface AssisView extends BaseView{
    void setAssissList(List<AssisBean> assissList,@LoadNewsType.checker int loadType);
}
