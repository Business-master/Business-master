package com.ristone.businessasso.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/27
 * 专家页面的常量
 */
public class DropsType {

    public static final String DROP_TYPE = "drop_type";
    public static final int TYPE_BANK = 1;
    public static final int TYPE_NO_BANK = 2;

    @IntDef({TYPE_BANK, TYPE_NO_BANK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface checker {
    }
}
