package com.hjt.lifeserver.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by John on 2016/6/2.
 */
public class ConvertUtils {
    /**
     * 将dp转换为px
     * */
    public static int  dp2px(Context mContext,float defaultValue){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,defaultValue,mContext.getResources().getDisplayMetrics());
    }
    /**
     * 将sp转换为px
     * */
    public static int sp2px(Context mContext,float defaultValue){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,defaultValue,mContext.getResources().getDisplayMetrics());
    }
}
