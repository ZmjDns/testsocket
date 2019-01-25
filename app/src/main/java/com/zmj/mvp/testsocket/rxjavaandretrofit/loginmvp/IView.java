package com.zmj.mvp.testsocket.rxjavaandretrofit.loginmvp;

import com.zmj.mvp.testsocket.bean.LoginResult;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/1/24
 * Description :
 */
public interface IView {
    void showLoading();

    void showData(LoginResult result);
}
