package com.zmj.mvp.testsocket.rxjavaandretrofit.loginmvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.bean.LoginResult;
import com.zmj.mvp.testsocket.rxjavaandretrofit.ApiMethodImpel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/1/22
 * Description :
 */
public class TestRxJandRetrAct2 extends AppCompatActivity implements IView {
    
    private final String TAG = this.getClass().getSimpleName();

    private Observer<LoginResult> observer;

    private ApiMethodImpel apiMethodImpel;



    private LoginPersenter loginPersenter; //持有Presenter的引用

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test_rxjava_retrofit_1);

        apiMethodImpel = new ApiMethodImpel();

        //login();


        loginPersenter = new LoginPersenter();

        loginPersenter.onAttach(this);  //绑定View传到Presenter

        //loginPersenter.login("aaa","123456");
    }

    private void login(){
        Map<String,String> parmas = new HashMap<>();

        //parmas.put("actoin","login");
        parmas.put("name","aaa");
        parmas.put("password","1234546");


        observer = new Observer<LoginResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(LoginResult loginResult) {
                Log.d(TAG, "onNext: " + loginResult.getDataBean().getToken());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };

        apiMethodImpel.loginAccount(observer,parmas);

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showData(LoginResult result) {
        Log.d(TAG, "showData: INACTIVITY" + result.getDataBean().getToken());
        Toast.makeText(this,result.getDataBean().getToken(),Toast.LENGTH_SHORT).show();
    }

    //调用此方法，用于网络请求
    public void mvpLogin(View view){
        loginPersenter.login("aaa","123456");
    }

    @Override
    protected void onDestroy() {
        loginPersenter.detach();
        super.onDestroy();
    }
}
