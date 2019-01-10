package com.zmj.mvp.testsocket.async;

import android.os.AsyncTask;
import android.text.PrecomputedText;

import java.lang.ref.WeakReference;

/**
 * @author Zmj
 * @date 2019/1/9
 * 封装的弱引用AsyncTask
 */
public abstract class BaseWeakAsyncTask<Params,Progress,Result,WeakTarget> extends AsyncTask<Params,Progress,Result> {

    protected WeakReference<WeakTarget> mTarget;

    public BaseWeakAsyncTask(WeakTarget target){
        mTarget = new WeakReference<>(target);
    }

    @Override
    protected void onPreExecute() {
        final WeakTarget target = mTarget.get();
        if (target != null){
            this.onPreExecute(target);
        }
    }

    @Override
    protected Result doInBackground(Params... params) {
        final WeakTarget target = mTarget.get();
        if (target != null){
            return this.doInbackground(target,params);
        }else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Result result) {
        final WeakTarget target = mTarget.get();
        if (mTarget != null){
            this.onPostExecute(target,result);
        }
    }

    protected abstract void onPreExecute(WeakTarget target);
    protected abstract Result doInbackground(WeakTarget target,Params...params);
    protected abstract void onPostExecute(WeakTarget target,Result result);

}
