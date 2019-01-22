package com.zmj.mvp.testsocket.rxjavaandretrofit;

import com.zmj.mvp.testsocket.bean.LoginResult;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Zmj
 * @date 2019/1/21
 */
public class LoginMethod {

    private final String TAG = this.getClass().getSimpleName();

    private static final String HOST = "http://192.168.1.254:8080/myssm/";
    private static final int DEFAULT_TIME_OUT = 5;

    private Retrofit mRetrofit;
    private LoginService mLoginService;

    private static LoginMethod loginInstance;


    //访问http时创建单例
    public static LoginMethod getLoginInstance(){
        if (loginInstance == null){
            synchronized (LoginMethod.class){
                if (loginInstance == null){
                    loginInstance = new LoginMethod();
                }
            }
        }
        return loginInstance;
    }

    private LoginMethod(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(HOST)
                .build();

        mLoginService = mRetrofit.create(LoginService.class);
    }

    public void login(Observer<LoginResult> subscriber,String name,String psd){
        Observable observable = mLoginService.login(name,psd);
        toSubscribe(observable,subscriber);
    }

    private<T> void toSubscribe(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Observer<? super T>)subscriber);
    }
}
