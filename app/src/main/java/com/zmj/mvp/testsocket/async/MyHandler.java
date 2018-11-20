package com.zmj.mvp.testsocket.async;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @author Zmj
 * @date 2018/11/19
 */
public abstract class MyHandler extends Handler {
    private final WeakReference<Context> targetAct;

    public MyHandler (Context context){
        targetAct = new WeakReference<Context>(context);
    }

    @Override
    public void handleMessage(Message msg) {
        Context target = targetAct.get();
        if (target != null){
            this.handleMessage(target,msg);
        }
    }

    protected abstract void  handleMessage(Context context,Message msg);
}
