package com.zmj.mvp.testsocket.rxjavaandretrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.util.Log;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.bean.LoginResult;
import com.zmj.mvp.testsocket.retrofit.UseRetrofit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;


/**
 * @author Zmj
 * @date 2019/1/21
 */
public class TestRxJandRetrofitAct1 extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private Observer<LoginResult> subscriber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test_rxjava_retrofit_1);

        loginAct();
    }

    private void loginAct(){
        subscriber = new Observer<LoginResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(LoginResult loginResult) {
                Log.d(TAG, "onNext: 获取的数据：" + /*loginResult.getDataBean().getToken()*/  loginResult.getDataBean().getToken());
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



        LoginMethod.getLoginInstance().login(subscriber,"aaa","123456");
    }


}
