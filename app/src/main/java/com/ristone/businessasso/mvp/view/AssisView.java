package com.ristone.businessasso.mvp.view;

import com.ristone.businessasso.common.LoadNewsType;
import com.ristone.businessasso.mvp.entity.AssisBean;
import com.ristone.businessasso.mvp.view.base.BaseView;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
public interface AssisView extends BaseView{
    void setAssissList(List<AssisBean> assissList,@LoadNewsType.checker int loadType);
}
