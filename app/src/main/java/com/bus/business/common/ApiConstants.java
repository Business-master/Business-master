package com.bus.business.common;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public class ApiConstants {

    //线下
//    public static final String NETEAST_HOST = "http://172.16.10.15:9300/";
    public static final String NETEAST_IMG_HOST = "http://files.heweather.com/cond_icon/%s.png";

    //线上
    public static final String NETEAST_HOST = "http://59.108.94.40:9100/";

    public static String getHost() {
        return NETEAST_HOST;
    }

    public static final String NEWS_URL = "/gsl-api/gsl/news/newsList";

    public static final String MEETINGS_URL = "/gsl-api/gsl/meeting/meetingList";

    public static final String LOGIN_IN_URL = "/gsl-api/gsl/sysUser/login";

    public static final String BANNER_URL = "/gsl-api/gsl/news/newsBannerList";
    public static final String WEATHER_URL = "/gsl-api/weather/getweather";

    public static final String NEW_DETAIL_URL = "/gsl-api/gsl/news/getNews";

    public static final String BUS_DETAIL_URL = "/gsl-api/gsl/business/getBusiness";

    public static final String ASSISS_LIST_URL = "/gsl-api/user/listUserAssistant";

    public static final String BUSINESS_LIST_URL = "/gsl-api/gsl/business/businessList";

    public static final String REVISE_PASSWORD_URL = "/gsl-api/gsl/sysUser/upPass";

    public static final String JOIN_MEETING_URL = "/gsl-api/gsl/meeting/join";

    public static final String SIGN_IN_URL = "/gsl-api/gsl/meeting/signIn";

    public static final String ADD_ASSIS_URL = "/gsl-api/user/addUserAssistant";

    public static final String SELECT_ASSIS_URL = "/gsl-api/user/selectAssistant";
}
