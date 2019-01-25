package com.zmj.mvp.testsocket.rxjavaandretrofit.loginmvp;

import com.zmj.mvp.testsocket.bean.LoginResult;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/1/24
 * Description :
 */
public interface IModel {
//    void loadData(Observer<LoginResult> observer, LoadDataListener listener);
//
//    interface LoadDataListener{
//        void dataLoadComplete(LoginResult result);
//    }

    //被观察者
    Observable<LoginResult> login(String name,String psd,Observer<LoginResult> observer);
}
