package com.ristone.businessasso.mvp.view;

import com.ristone.businessasso.common.LoadNewsType;
import com.ristone.businessasso.mvp.entity.MeetingBean;
import com.ristone.businessasso.mvp.view.base.BaseView;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/29
 */
public interface MeetingView extends BaseView {
    void setMeetingList(List<MeetingBean> newsSummary, @LoadNewsType.checker int loadType);
}