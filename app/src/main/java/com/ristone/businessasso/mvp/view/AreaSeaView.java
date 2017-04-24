package com.ristone.businessasso.mvp.view;

import com.ristone.businessasso.common.LoadNewsType;
import com.ristone.businessasso.mvp.entity.AreaSeaBean;
import com.ristone.businessasso.mvp.view.base.BaseView;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 * 区域搜索后的视图
 */
public interface AreaSeaView extends BaseView{
    //传递根据区域搜索后的  商讯--新闻数据
    void setAreaSeaBeanList(List<AreaSeaBean> areaSeaBeanList, @LoadNewsType.checker int loadType);
}
