package com.bus.business.mvp.view;

import com.bus.business.common.LoadNewsType;
import com.bus.business.mvp.view.base.BaseView;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public interface NewsView<T> extends BaseView {

    void setNewsList(T newsBean, @LoadNewsType.checker int loadType);
}
