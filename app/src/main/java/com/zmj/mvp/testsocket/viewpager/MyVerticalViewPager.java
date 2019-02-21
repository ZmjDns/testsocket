package com.zmj.mvp.testsocket.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/20
 * Description :自定义垂直ViewPager https://www.jianshu.com/p/d3065bbc1167
 */
public class MyVerticalViewPager extends ViewPager {

    public MyVerticalViewPager(@NonNull Context context) {
        super(context,null);
    }

    public MyVerticalViewPager(Context context, AttributeSet attrs) {
        super(context,attrs);
        //设置ViewPager的切换动画，这里实现垂直滑动
        setPageTransformer(true,new DefaultTransformer());
    }

    //拦截touch事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(ev);
        swapEvent(ev);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapEvent(ev));
    }

    private MotionEvent swapEvent(MotionEvent event) {
       /* //获取宽高
        float width = getWidth();
        float hight = getHeight();
        //将Y轴的移动距离转变成X轴的移动距离
        float swappedX = (event.getY() / hight) * width;
        float swappedY = (event.getX() / width) * hight;
        //重设event的位置
        event.setLocation(swappedX,swappedY);
        return event;*/

        //获取宽高
        float width = getWidth();
        float height = getHeight();
        //将Y轴的移动距离转变成X轴的移动距离
        float swappedX = (event.getY() / height) * width;
        //将X轴的移动距离转变成Y轴的移动距离
        float swappedY = (event.getX() / width) * height;
        //重设event的位置
        event.setLocation(swappedX, swappedY);
        return event;
    }

    //垂直翻页动画
    public class DefaultTransformer implements ViewPager.PageTransformer{

        @Override
        public void transformPage(@NonNull View view, float position) {
            /*float alpha = 0;
            if (0 <= position && position <= 1){
                alpha = 1 - position;
            }else if(-1 < position && position < 0){
                alpha = position + 1;
            }

            view.setAlpha(alpha);
            float transX = view.getWidth() * position;
            view.setTranslationX(transX);
            float transY = view.getHeight() * position;
            view.setTranslationY(transY);*/


            float alpha = 0;
            if (0 <= position && position <= 1) {
                alpha = 1 - position;
            } else if (-1 < position && position < 0) {
                alpha = position + 1;
            }
            view.setAlpha(alpha);
            float transX = view.getWidth() * - position;
            view.setTranslationX(transX);
            float transY = position * view.getHeight();
            view.setTranslationY(transY);
        }
    }
}
