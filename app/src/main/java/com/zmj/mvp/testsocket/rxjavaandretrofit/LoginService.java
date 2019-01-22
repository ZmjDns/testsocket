package com.zmj.mvp.testsocket.rxjavaandretrofit;

import com.zmj.mvp.testsocket.bean.LoginResult;

import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author Zmj
 * @date 2019/1/21
 */
public interface LoginService {

    @FormUrlEncoded
    @POST("?action=login")
    io.reactivex.Observable<LoginResult> login(@Field("name") String name,@Field("password") String password);
}
