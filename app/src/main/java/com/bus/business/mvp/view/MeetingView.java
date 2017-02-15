package com.bus.business.mvp.view;

import com.bus.business.common.LoadNewsType;
import com.bus.business.mvp.entity.MeetingBean;
import com.bus.business.mvp.view.base.BaseView;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/29
 */
public interface MeetingView extends BaseView {
    void setMeetingList(List<MeetingBean> newsSummary, @LoadNewsType.checker int loadType);
}