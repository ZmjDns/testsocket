package com.zmj.mvp.testsocket.rxjavaandretrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.bean.LoginResult;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


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

        //loginAct();

        //loginByRetrofit();

        //loginByGetQueryMap();

        //loginByPostField();

        //loginByPostFieldMap();

        //loginByPostPart();

        uploadFileByPost();

        //uploadFileByPostPartMap();
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
                Toast.makeText(TestRxJandRetrofitAct1.this,loginResult.getDataBean().getToken(), Toast.LENGTH_SHORT).show();
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

    private void loginByRetrofit(){
        LoginMethod.getLoginInstance().loginByRetro("login","aaa","123456");
    }

    private void loginByGetQueryMap(){
        LoginMethod.getLoginInstance().LoginByGetAndQueryMap("login","aaa","123456");
    }

    private void loginByPostField(){
        LoginMethod.getLoginInstance().loginByPostField("login","aaa","123456");
    }

    private void loginByPostFieldMap(){
        LoginMethod.getLoginInstance().loginByPostFieldMap("login","aaa","123456");
    }

    private void loginByPostPart(){
        LoginMethod.getLoginInstance().loginByPostPart("login","aaa","123456");
    }

    private void uploadFileByPost(){
        LoginMethod.getLoginInstance().uploadFileByPostPart("pic");
    }

    private void uploadFileByPostPartMap(){
        LoginMethod.getLoginInstance().uploadFileByPostPartMap("pic","两个字段，两图片");
    }
}
