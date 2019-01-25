package com.zmj.mvp.testsocket.rxjavaandretrofit.loginmvp;

import android.nfc.Tag;
import android.util.Log;

import com.zmj.mvp.testsocket.bean.LoginResult;
import com.zmj.mvp.testsocket.rxjavaandretrofit.RetrofitClientSingleton;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/1/24
 * Description :
 */
public class LoginModel implements IModel {

    private final String TAG = this.getClass().getSimpleName();

//    @Override
//    public void loadData(Observer<LoginResult> observer, LoadDataListener listener) {
//
//        Observable<LoginResult> observable = RetrofitClientSingleton.getInstance().myApiService.loginByObservablePost()
//    }

    @Override
    public Observable<LoginResult> login(String name, String psd,Observer<LoginResult> observer) {
        //对View曾传来的数据处理
        Map<String, RequestBody> paramsBody = new HashMap<>();
        RequestBody rbname = RequestBody.create(MediaType.parse("text/plain"),name);
        RequestBody rbPsd = RequestBody.create(MediaType.parse("text/plain"),psd);
        paramsBody.put("name",rbname);
        paramsBody.put("password",rbPsd);

        //被观察者执行网络请求
        return Observable.create(new ObservableOnSubscribe<LoginResult>() {
            @Override
            public void subscribe(ObservableEmitter<LoginResult> emitter) throws Exception {
                //获取ApiInterfaceService对象
                RetrofitClientSingleton.getInstance().myApiService.loginByObservablePost(paramsBody)
                        .subscribeOn(Schedulers.io())       //在子线程中网络请求
                        .observeOn(AndroidSchedulers.mainThread())//在主线程中操作返回结果
                        .subscribe(observer);   //将observable（被观察者）与observer（观察者）关联
            }
        });
    }
}
