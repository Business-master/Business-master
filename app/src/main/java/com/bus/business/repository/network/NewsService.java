package com.bus.business.repository.network;

import com.bus.business.common.ApiConstants;
import com.bus.business.mvp.entity.response.RspAssisBean;
import com.bus.business.mvp.entity.response.RspBannerBean;
import com.bus.business.mvp.entity.response.RspBusDetailBean;
import com.bus.business.mvp.entity.response.RspBusinessBean;
import com.bus.business.mvp.entity.response.RspMeetingBean;
import com.bus.business.mvp.entity.response.RspNewDetailBean;
import com.bus.business.mvp.entity.response.RspNewsBean;
import com.bus.business.mvp.entity.response.RspPhoneBookbean;
import com.bus.business.mvp.entity.response.RspUserBean;
import com.bus.business.mvp.entity.response.RspWeatherBean;
import com.bus.business.mvp.entity.response.base.BaseRspObj;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public interface NewsService {

    @FormUrlEncoded
    @POST(ApiConstants.NEWS_URL)
    Observable<RspNewsBean> getNewsList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(ApiConstants.BUSINESS_LIST_URL)
    Observable<RspBusinessBean> getBusinessList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(ApiConstants.ASSISS_LIST_URL)
    Observable<RspAssisBean> getAssissList(@FieldMap Map<String, String> map);

   @FormUrlEncoded
    @POST(ApiConstants.ADD_ASSIS_URL)
    Observable<BaseRspObj> getAddAssis(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(ApiConstants.MEETINGS_URL)
    Observable<RspMeetingBean> getMeetingsList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(ApiConstants.LOGIN_IN_URL)
    Observable<RspUserBean> loginIn(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(ApiConstants.NEW_DETAIL_URL)
    Observable<RspNewDetailBean> getNewDetail(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(ApiConstants.BUS_DETAIL_URL)
    Observable<RspBusDetailBean> getBusDetail(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(ApiConstants.REVISE_PASSWORD_URL)
    Observable<BaseRspObj> getRevisePassword(@FieldMap Map<String, String> map);


    @GET(ApiConstants.SELECT_ASSIS_URL)
    Observable<BaseRspObj> getSelectAssis(@FieldMap Map<String, String> map);

    /**
     * 参会
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.JOIN_MEETING_URL)
    Observable<BaseRspObj> joinMeeting(@FieldMap Map<String, String> map);

    /**
     * 签到
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstants.SIGN_IN_URL)
    Observable<BaseRspObj> signInMeeting(@FieldMap Map<String, String> map);

    @POST(ApiConstants.BANNER_URL)
    Observable<RspBannerBean> getBanners();

    @POST(ApiConstants.WEATHER_URL)
    Observable<RspWeatherBean> getWeather();

    @GET
    Observable<ResponseBody> getNewsBodyHtmlPhoto(
            @Url String photoPath);

    @FormUrlEncoded
    @POST(ApiConstants.PHONE_BOOK_URL)
    Observable<RspPhoneBookbean> getPhoneBook(@FieldMap Map<String, String> map);

}
