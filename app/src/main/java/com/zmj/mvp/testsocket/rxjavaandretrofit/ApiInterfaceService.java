package com.zmj.mvp.testsocket.rxjavaandretrofit;

import com.zmj.mvp.testsocket.bean.LoginResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/1/24
 * Description :用于请求网络的接口
 */
public interface ApiInterfaceService {
    @POST("?action=login")
    @FormUrlEncoded
    Observable<LoginResult> loginByObservablePost(@FieldMap Map<String, RequestBody> paramsBody);
    //网络请求作为被观察者，用Observable来声明
}
