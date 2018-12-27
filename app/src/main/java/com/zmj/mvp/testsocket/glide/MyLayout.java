package com.zmj.mvp.testsocket.glide;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

/**
 * @author Zmj
 * @date 2018/12/27
 */
public class MyLayout extends LinearLayout {

    private ViewTarget<MyLayout,GlideDrawable> viewTarget;

    public MyLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        viewTarget = new ViewTarget<MyLayout, GlideDrawable>(this) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                MyLayout myLayout = getView();
                myLayout.setImageAsBackground(resource);
            }
        };
    }

    public ViewTarget<MyLayout, GlideDrawable> getTarget() {
        return viewTarget;
    }

    private void setImageAsBackground(GlideDrawable resource) {
        setBackground(resource);
    }
}
