package com.ristone.businessasso.common;

import android.content.Context;
import android.text.TextUtils;

import com.ristone.businessasso.App;
import com.ristone.businessasso.mvp.entity.UserBean;
import com.ristone.businessasso.mvp.entity.UserInfoBean;
import com.ristone.businessasso.mvp.entity.response.RspUserInfoBean;
import com.ristone.businessasso.repository.network.RetrofitManager;
import com.ristone.businessasso.utils.SharedPrefsUtils;
import com.google.gson.Gson;
import com.ristone.businessasso.utils.TransformUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 * 用户信息(登录里面的)管理
 */
public class UsrMgr {

    public static final String USER_INFO = "userinfo";
    private static Context context = App.getAppContext();

    /**
     * 获取用户信息
     *
     * @return
     */
    public static UserBean getUseInfo() {
        UserBean model = null;
        String string = SharedPrefsUtils.getStringPreference(context, USER_INFO);
        if (!TextUtils.isEmpty(string)) {
            model = new Gson().fromJson(string, UserBean.class);
        }
        return model;
    }

    /**
     * 获取用户Id
     *
     * @return
     */
    public static String getUseId() {
        UserBean model = getUseInfo();
        return model != null ? model.getId() : "";
    }

    /**
     * 获取用户电话
     *
     * @return
     */
    public static String getUsePhone() {
        UserBean model = getUseInfo();
        return model != null ? model.getPhoneNo() : "";
    }

    /**
     * 缓存用户信息
     *
     * @param json
     */
    public static void cacheUserInfo(String json) {
        SharedPrefsUtils.setStringPreference(context, USER_INFO, json);
    }




    /**
     * 清除缓存数据
     */
    public static void clearUserInfo() {
        cacheUserInfo("");
    }


    /**
     * 判断是否已登录
     *
     * @return
     */
    public static boolean isLogin() {
        UserBean model = getUseInfo();
        if (model != null) {
            return true;
        } else {
            return false;
        }
    }
}
