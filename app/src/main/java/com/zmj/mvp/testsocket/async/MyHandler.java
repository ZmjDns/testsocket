package com.zmj.mvp.testsocket.async;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @author Zmj
 * @date 2018/11/19
 */
public abstract class MyHandler extends Handler {
    private final WeakReference<Context> targetAct;

    /*//HAndler 新的使用方法
    //1.声明
    private Handler newHandler;
    //2.一般在构造方法或初始化的时候实例化
    //newHandler = new Handler(Looper.getMainLooper());*/

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
