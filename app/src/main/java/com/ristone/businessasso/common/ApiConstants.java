package com.ristone.businessasso.common;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 * 接口类
 */
public class ApiConstants {

    //线下
//    public static final String NETEAST_HOST = "http://172.16.10.15:9300/";
//    public static final String NETEAST_HOST = "http://172.16.6.81:8080/";
//    public static final String NETEAST_HOST = "http://43.254.24.113:9300";
//    public static final String NETEAST_HOST = "http://43.254.24.113:9100/";//新的外网地址
    public static final String NETEAST_HOST = "http://172.16.6.50:8080/";//新的外网地址
    public static final String NETEAST_IMG_HOST = "http://files.heweather.com/cond_icon/%s.png";

    //线上
//    public static final String NETEAST_HOST = "http://59.108.94.40:9100/";

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

    public static final String GET_AREA_LIST = "/gsl-api/mblDictionary/getCodeList";//地区行业字典查询

    public static final String AREA_SEARCH_DATA = "/gsl-api/search/getResource";//根据区域和行业查询新闻商讯

    public static final String PHONE_BOOK_URL = "/gsl-api/phoneBook/getUsers";

    public static final String REGISTER_JPUSH_URL = "/gsl-api/push/ initRegistrationId";

    public static final String TOPICS_URL = "/gsl-api/gsl/dissertation/disList";

    public static final String TOPIC_DETAIL_URL = "/gsl-api/gsl/dissertation/getdissertation";

    public static final String DROP_LIST_URL = "/gsl-api/cashProfessor/list";

    public static final String Not_read_meeting = "/gsl-api/gsl/meeting/countUserRead";

    public static final String Change_meeting_read = "/gsl-api/gsl/meeting/changeUserRead";

    public static final String Meeting_file_list = "/gsl-api/meetingFile/fileList";

    public static final String Meeting_file_download = "gsl-api/meetingFile/download";

    public static final String GetAllNation = "/gsl-api/user/getAllNation";

    public static final String GetAllPosition = "/gsl-api/user/getAllPosition";//获取所有的职务

    public static final String GetMeetingDetail = "gsl-api/gsl/meeting/meetingDetail";

    public static final String GetAllCompany = "gsl-api/user/getAllCompany";

    public static final String GetAllSex = "gsl-api/user/getAllSex";

    public static final String GetUserById = "gsl-api/user/getUserById";

    public static final String Questionnaire = "gsl-api/questionnaire/list";//调查问卷

    public static final String VersionInfo = "gsl-api/mblVersion/getVersionInfo";//版本信息

    public static final String DownVersion = "gsl-api/mblVersion/download";//下载版本


    public static final String LastLogTime = "gsl-api/gsl/sysUser/lastlogTime";//登陆时间和次数

}
