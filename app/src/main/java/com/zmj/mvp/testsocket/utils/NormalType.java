package com.zmj.mvp.testsocket.utils;

import android.util.Log;

/**
 * @author Zmj
 * @date 2019/1/18
 */
public class NormalType<T> {

    private final String TAG = this.getClass().getSimpleName();

    private T mObject;

    public NormalType(T mObject) {
        this.mObject = mObject;
    }

    public T getmObject() {
        return mObject;
    }

    public void setmObject(T mObject) {
        this.mObject = mObject;
    }

    public <T> void show(T t){
        Log.d(TAG, "show: " + t );
    }

    public <T> void outPut(T...args){
        for (int i = 0; i < args.length;i++){
            Log.d(TAG, "outPut: " + args);
        }
    }
}
