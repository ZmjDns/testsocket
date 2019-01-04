package com.zmj.mvp.testsocket.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @author Zmj
 * @date 2019/1/4
 */
public abstract class BaseDialog {
    private final String TAG = this.getClass().getSimpleName();

    protected Dialog dialog;
    protected View view;
    protected Context context;
    protected int resLaout;

    public BaseDialog(Context context,int resLaout) {
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(resLaout,null);
    }
}
