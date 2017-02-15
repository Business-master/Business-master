package com.bus.business.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/27
 */
public class NewsType {

    public static final String TYPE_SEARCH = "type_search";
    public static final int TYPE_REFRESH_XUNXI = 1;
    public static final int TYPE_REFRESH_XIEHUI = 2;
    public static final int TYPE_REFRESH_HUIWU = 3;
    public static final int TYPE_REFRESH_AREA = 4;

    @IntDef({TYPE_REFRESH_XUNXI, TYPE_REFRESH_XIEHUI, TYPE_REFRESH_HUIWU,TYPE_REFRESH_AREA})
    @Retention(RetentionPolicy.SOURCE)
    public @interface checker {
    }
}
