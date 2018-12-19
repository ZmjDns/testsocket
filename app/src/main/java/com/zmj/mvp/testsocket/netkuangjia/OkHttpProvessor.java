package com.zmj.mvp.testsocket.netkuangjia;

import android.os.Handler;
import android.view.textclassifier.TextClassification;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Zmj
 * @date 2018/12/19
 */
public class OkHttpProvessor implements IHttpProcessor {

    private final String TAG = this.getClass().getSimpleName();

    private OkHttpClient mOkHttpClient;
    /**
     * okHttp运行在子线程中，在OkHttpProvessor定义handler进行线程间通信
     */
    private Handler myHandler;

    public OkHttpProvessor(){
        mOkHttpClient = new OkHttpClient();
        myHandler = new Handler();
    }
    @Override
    public void post(String url, Map<String, Object> params, ICallBack callBack) {
        RequestBody requestBody = appendBody(params);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .header("User-Agent","a")   //防止访问被禁止
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailed(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    final String result = response.body().string();
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSuccess(result);
                        }
                    });
                }else {
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onFailed(response.message().toString());
                        }
                    });
                }
            }
        });
    }

    @Override
    public void get(String url, Map<String, Object> params, ICallBack callBack) {

    }

    private RequestBody appendBody(Map<String, Object> params) {
        FormBody.Builder body = new FormBody.Builder();
        if (params == null){
            return body.build();
        }
        for (Map.Entry<String,Object> entry : params.entrySet()){
            body.add(entry.getKey(),entry.getValue().toString());
        }
        return body.build();
    }
}
