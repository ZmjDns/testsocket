package com.zmj.mvp.testsocket.animation;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/1/27
 * Description :ViewPager 翻页动画
 */
public class DepthPageTransFormer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(@NonNull View view, float position) {
        int pageWidth = view.getWidth();
        if (position < -1){     // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
        }else if (position <= 0){   //[-1,0]
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);
        }else if (position < 1){    //(0,1]
            //Fade the page out    渐退
            view.setAlpha(1-position);
            //Counter the default side transaction  计算默认边的转换
            view.setTranslationX(pageWidth - position);
            //Scale the page down (between MIN_SCALE and 1) //在1-0.75之间缩小Viewpager的大小
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        }else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}
