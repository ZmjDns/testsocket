package com.zmj.mvp.testsocket.netkuangjia;

import android.util.Log;

import com.zmj.mvp.testsocket.App;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

/**
 * @author Zmj
 * @date 2018/12/19
 */
public class XUtilsProsessor implements IHttpProcessor {

    private final String TAG = this.getClass().getSimpleName();

    public XUtilsProsessor (App app){
        x.Ext.init(app);
    }
    @Override
    public void post(String url, Map<String, Object> params, final ICallBack callBack) {
        RequestParams requestParams = new RequestParams(url);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "onSuccess: " + result);
                callBack.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "onError: " + ex.toString());
                callBack.onFailed(ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void get(String url, Map<String, Object> params, ICallBack callBack) {

    }
}
