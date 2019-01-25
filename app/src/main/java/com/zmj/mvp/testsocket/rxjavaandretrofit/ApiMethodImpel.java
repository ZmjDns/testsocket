package com.zmj.mvp.testsocket.rxjavaandretrofit;

import android.util.Log;

import com.zmj.mvp.testsocket.bean.LoginResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/1/24
 * Description :
 */
public class ApiMethodImpel {

    private final String TAG = this.getClass().getSimpleName();

    public void loginAccount(Observer<LoginResult> observer,Map<String,String> params){
        Map<String, RequestBody> paramsBody = new HashMap<>();

        for (Map.Entry<String,String> entry : params.entrySet()){
            Log.d("ApiMethodImpel", entry.getKey() + ": " + entry.getValue());
            RequestBody body = RequestBody.create(MediaType.parse("text/plain"),entry.getValue());
            paramsBody.put(entry.getKey(),body);
        }

        Observable<LoginResult> observable =  RetrofitClientSingleton.getInstance().myApiService.loginByObservablePost(paramsBody);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
