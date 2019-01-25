package com.zmj.mvp.testsocket.rxjavaandretrofit;

import java.security.PublicKey;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/1/24
 * Description :Retrofit封装类 单例模式
 */
public class RetrofitClientSingleton {

    private final String TAG = this.getClass().getSimpleName();

    private static  RetrofitClientSingleton retrofitInstance;

    private static final int TIME_OUT = 20;

    private static final String BASR_URL = "http://192.168.1.254:8080/myssm/";

    private Retrofit myRertofit;
    public final ApiInterfaceService myApiService;

    public static RetrofitClientSingleton getInstance() {
        if (retrofitInstance == null){
            synchronized (RetrofitClientSingleton.class){
                if (retrofitInstance == null){
                    retrofitInstance = new RetrofitClientSingleton();
                }
            }
        }
        return retrofitInstance;
    }


    private RetrofitClientSingleton() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);

        myRertofit = new Retrofit.Builder()
                .baseUrl(BASR_URL)            //基础URL
                .client(builder.build())      //配置OkHttp的配置
                .addConverterFactory(GsonConverterFactory.create()) //数据转换
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //网络请求适配
                .build();

        //获取APiService对象，用于请求网络
        myApiService = myRertofit.create(ApiInterfaceService.class);
    }
}
