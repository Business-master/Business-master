package com.ristone.businessasso.utils;

import android.content.Context;

/**
 * Created by ATRSnail on 2017/2/9.
 * dp -- px 转换类
 */

public class DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位  转成 px 像素
     */

     public static int dip2px(Context context,float dpValue){
         float scale = context.getResources().getDisplayMetrics().density;
          return (int) (dpValue*scale+0.5f);
       }

    /**
     * 根据手机的分辨率从 px 像素的单位  转成 dp
     */

    public static int px2dip(Context context,float pxValue){
        final  float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue/scale+0.5f);
    }

}
