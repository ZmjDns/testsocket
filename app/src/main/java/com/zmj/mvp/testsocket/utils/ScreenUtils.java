package com.zmj.mvp.testsocket.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author Zmj
 * @date 2019/1/3
 */
public class ScreenUtils {
    /**
     * 根据手机的分辨率从dp的单位转化为px（像素）
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context,float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue*scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从px（像素）的单位转换成dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context,float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue/scale + 0.5f);
    }

    /**
     * 获取屏幕像素的大小
     * @param context
     * @return
     */
    public static int[] getScreenSize(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMertics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMertics);

        return new int[]{outMertics.widthPixels,outMertics.heightPixels};
    }
}
