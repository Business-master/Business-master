package com.bus.business.repository.network;

import android.text.TextUtils;
import android.util.SparseArray;

import com.bus.business.App;
import com.bus.business.common.ApiConstants;
import com.bus.business.common.UsrMgr;
import com.bus.business.mvp.entity.response.RspAssisBean;
import com.bus.business.mvp.entity.response.RspBannerBean;
import com.bus.business.mvp.entity.response.RspBusDetailBean;
import com.bus.business.mvp.entity.response.RspBusinessBean;
import com.bus.business.mvp.entity.response.RspMeetingBean;
import com.bus.business.mvp.entity.response.RspNewDetailBean;
import com.bus.business.mvp.entity.response.RspNewsBean;
import com.bus.business.mvp.entity.response.RspUserBean;
import com.bus.business.mvp.entity.response.RspWeatherBean;
import com.bus.business.mvp.entity.response.base.BaseRspObj;
import com.bus.business.utils.NetUtil;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public class RetrofitManager {

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    private NewsService mNewsService;

    private static SparseArray<RetrofitManager> sRetrofitManager = new SparseArray<>();
    private static volatile OkHttpClient sOkHttpClient;

    public RetrofitManager() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstants.getHost())
                .client(getOkHttpClient()).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        mNewsService = retrofit.create(NewsService.class);
    }

    private OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                Cache cache = new Cache(new File(App.getAppContext().getCacheDir(), "HttpCache"),
                        1024 * 1024 * 100);
                if (sOkHttpClient == null) {
                    sOkHttpClient = new OkHttpClient.Builder().cache(cache)
                            .connectTimeout(6, TimeUnit.SECONDS)
                            .readTimeout(6, TimeUnit.SECONDS)
                            .writeTimeout(6, TimeUnit.SECONDS)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(mLoggingInterceptor)
                            .build();
                }
            }
        }
        return sOkHttpClient;
    }

    public static RetrofitManager getInstance(int hostType) {
        RetrofitManager retrofitManager = sRetrofitManager.get(hostType);
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager();
            sRetrofitManager.put(hostType, retrofitManager);
            return retrofitManager;
        }
        return retrofitManager;
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isNetworkAvailable()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                KLog.d("no network");
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtil.isNetworkAvailable()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    private final Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            KLog.i(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            KLog.i(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    };

    public Observable<RspNewsBean> getNewsListObservable(int pageNum, int numPerPage, String title) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum + "");
        map.put("numPerPage", numPerPage + "");
        if (!TextUtils.isEmpty(title))
            map.put("title", title);
        KLog.a(map.toString());
        return mNewsService.getNewsList(map);
    }

    public Observable<RspMeetingBean> getMeetingsListObservable(int pageNum, int numPerPage, String meetingName,int status) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum + "");
        map.put("numPerPage", numPerPage + "");
        map.put("userId", UsrMgr.getUseId());
        if (status!=-1){
            map.put("status",status+"");
        }else {
            map.put("status","");
        }

        if (!TextUtils.isEmpty(meetingName))
            map.put("meetingName",meetingName);
        KLog.a(map.toString());
        return mNewsService.getMeetingsList(map);
    }




    public Observable<RspUserBean> getLoginInObservable(String phoneNum, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phoneNum);
        map.put("passWord", password);
        KLog.a(map.toString());
        return mNewsService.loginIn(map);
    }

    public Observable<RspBannerBean> getBannersObservable() {
        return mNewsService.getBanners();
    }

    public Observable<RspWeatherBean> getWeatherObservable() {
        return mNewsService.getWeather();
    }

    public Observable<RspNewDetailBean> getNewDetailObservable(String newsId) {
        Map<String, String> map = new HashMap<>();
        map.put("newsId", newsId);
        KLog.a(map.toString());
        return mNewsService.getNewDetail(map);
    }

    public Observable<RspBusDetailBean> getBusDetailObservable(String newsId) {
        Map<String, String> map = new HashMap<>();
        map.put("businessId", newsId);
        KLog.a(map.toString());
        return mNewsService.getBusDetail(map);
    }

    public Observable<ResponseBody> getNewsBodyHtmlPhoto(String photoPath) {
        return mNewsService.getNewsBodyHtmlPhoto(photoPath);
    }



    public Observable<RspBusinessBean> getBusinessListObservable(int pageNum, int numPerPage, String title) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum + "");
        map.put("numPerPage", numPerPage + "");
        if (!TextUtils.isEmpty(title))
            map.put("title", title);
        KLog.a(map.toString());
        return mNewsService.getBusinessList(map);
    }


    public Observable<BaseRspObj> getRevisePasswordObservable(String phone, String passWord, String oldPassWord) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("passWord", passWord);
        map.put("oldPassWord", oldPassWord);
        KLog.a(map.toString());
        return mNewsService.getRevisePassword(map);
    }

    //修改会议参加状态接口
    public Observable<BaseRspObj> signInMeeting(String meetingId) {
        Map<String, String> map = new HashMap<>();
        map.put("meetingId", meetingId);
        map.put("userId",UsrMgr.getUseId());
        KLog.a(map.toString());
        return mNewsService.signInMeeting(map);
    }

    //修改会议参加状态新接口
    public Observable<BaseRspObj> joinMeeting(int meetingId,int joinType,int foodId,int stay,String userAssistantId,String carNo,int driver,String cause,String desp) {
        Map<String, String> map = new HashMap<>();
        map.put("userId",UsrMgr.getUseId());
        map.put("meetingId", meetingId+"");
        map.put("joinType",String.valueOf(joinType));
        map.put("foodId",String.valueOf(foodId));
        map.put("stay",String.valueOf(stay));
        map.put("userAssistantId",String.valueOf(userAssistantId));
        map.put("carNo",carNo);
        map.put("driver",String.valueOf(driver));
        map.put("cause",cause);
        map.put("desp",desp);
        KLog.a(map.toString());
        return mNewsService.joinMeeting(map);
    }

    //请假 或者取消报名
    public Observable<BaseRspObj> joinMeeting(int meetingId,int joinType,String cause) {
        Map<String, String> map = new HashMap<>();
        map.put("userId",UsrMgr.getUseId());
        map.put("meetingId", meetingId+"");
        map.put("joinType",String.valueOf(joinType));
        map.put("cause",cause);
        KLog.a(map.toString());
        return mNewsService.joinMeeting(map);
    }

    public Observable<RspAssisBean> getAssissListObservable() {
        Map<String, String> map = new HashMap<>();
        map.put("userId",UsrMgr.getUseId());
        KLog.a(map.toString());
        return mNewsService.getAssissList(map);
    }

    public Observable<BaseRspObj> getAddAssisListObservable(String name,String pass,String phone) {
        Map<String, String> map = new HashMap<>();
        map.put("assistantedId",UsrMgr.getUseId());
        map.put("userName",name);
        map.put("password",pass);
        map.put("phoneNo",phone);
        KLog.a(map.toString());
        return mNewsService.getAddAssis(map);
    }

    // 用户选取助理接口传参
    public Observable<BaseRspObj> getSelectAssisObservable() {
        Map<String, String> map = new HashMap<>();
        map.put("userId",UsrMgr.getUseId());
        KLog.a(map.toString());
        return mNewsService.getSelectAssis(map);
    }
}
