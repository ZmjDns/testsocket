package com.zmj.mvp.testsocket.rxjavaandretrofit.loginmvp;

import android.util.Log;

import com.zmj.mvp.testsocket.bean.LoginResult;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/1/24
 * Description :
 */
public class LoginPersenter<T extends IView> {

    private final String TAG = this.getClass().getSimpleName();

    //用于持有View的弱引用
    private WeakReference<T> weakReference;

    private LoginModel loginModel = new LoginModel();

    public LoginPersenter() {
    }

    public void onAttach(T view){
        weakReference = new WeakReference<>(view);
    }

    public void detach(){
        weakReference.clear();
    }

    /**
     * 此方法是关键，在View层调用此方法，就完成的数据在不同层之间的传递
     * @param name
     * @param psd
     */
    public void login(String name, String psd){
        if (weakReference.get() != null){
            weakReference.get().showLoading();//UI操作
            if (loginModel != null){
                //此处实现观察者的功能，用于处理网络返回数据
                Observer<LoginResult> observer = new Observer<LoginResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: INLOGINPRESENTER");
                    }
                    @Override
                    public void onNext(LoginResult result) {
                        //获取数据在UI操作
                        weakReference.get().showData(result);
                        Log.d(TAG, "onNext: INLOGINPRESENTER" + result.getDataBean().getToken());
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: INLOGINPRESENTER" + e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: INLOGINPRESENTER");
                    }
                };
                //获取Model层请求网络结果的被观察者对象
                Observable<LoginResult> observable = loginModel.login(name,psd,observer);

                //将observable（被观察者）与observer（观察者）关联
                observable.subscribe(observer);
            }
        }
    }
}
