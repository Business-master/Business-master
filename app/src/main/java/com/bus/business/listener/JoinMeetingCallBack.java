package com.bus.business.listener;

/**
 * Created by ATRSnail on 2017/3/6.
 * 取消报名---请假 成功 刷新页面--接口
 */

public interface JoinMeetingCallBack {
     void getJoinResult(boolean flag);//获得是否参加会议--返回的数据
}
