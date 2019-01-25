package com.zmj.mvp.testsocket.rxjavaandretrofit;

import android.util.Log;

import com.zmj.mvp.testsocket.bean.LoginResult;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    private static final int DEFAULT_TIME_OUT = 20;

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

    //RxJava + Retrofit
    public void login(Observer<LoginResult> subscriber,String name,String psd){
        Observable observable = mLoginService.login(name,psd);
        toSubscribe(observable,subscriber);
    }

    private<T> void toSubscribe(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Observer<? super T>)subscriber);
    }


    //单纯的用Retrofit
    public void loginByRetro(String action,String name,String psd){
        Call<LoginResult> call = mLoginService.login2(action,name,psd);

        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                Log.d(TAG, "onResponse: " + response.body().getDataBean().getToken());
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    //单纯的用Retrofit的GET  + QueryMap
    public void LoginByGetAndQueryMap(String action,String name,String psd){
        Map<String, String> params = new HashMap<>();

        /*MediaType text = MediaType.parse("text/plain");
        RequestBody rbAction = RequestBody.create(text,action);
        RequestBody rbName = RequestBody.create(text,name);
        RequestBody rbPsd = RequestBody.create(text,psd);*/

        //params.put("action",action);
        params.put("name",name);
        params.put("password",psd);

        Call<LoginResult> call = mLoginService.login3(params);

        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                Log.d(TAG, "onResponse: " + response.body().getDataBean().getToken());
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    //单纯的用Retrofit 的Post + Field
    public void loginByPostField(String action,String name,String psd){
        Call<LoginResult> call = mLoginService.loginByPost(action,name,psd);

        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                Log.d(TAG, "onResponse: " + response.body().getDataBean().getToken());
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    //单纯的用Retrofit的Post + FieldMap
    public void loginByPostFieldMap(String action,String name,String psd){
        Map<String,String> params = new HashMap<>();

        params.put("action",action);
        params.put("name",name);
        params.put("password",psd);

        Call<LoginResult> call = mLoginService.loginByPostFieldMap(params);

        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                Log.d(TAG, "onResponse: " + response.body().getDataBean().getToken());
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    //单纯的用Retrofit的Post + Part
    public void loginByPostPart(String action,String name,String psd){

        MediaType text = MediaType.parse("text/plain");

        RequestBody rbAction = RequestBody.create(text,action);
        RequestBody rbName = RequestBody.create(text,name);
        RequestBody rbPsd = RequestBody.create(text,psd);

        RequestBody rbFile = RequestBody.create(MediaType.parse("application/octet-stream"),"测试内容");

        MultipartBody.Part file = MultipartBody.Part.createFormData("file","test.txt",rbFile);

        Call<LoginResult> call =  mLoginService.loginByPostPart(rbAction,rbName,rbPsd,file);

        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                Log.d(TAG, "onResponse: " + response.body().getDataBean().getToken());
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    //Rretrofit + Post + Part 上传文件
    public void uploadFileByPostPart(String fileType){
        RequestBody rbFileDesc = RequestBody.create(MediaType.parse("text/plain"),fileType);
        //位置  /storage/emulated/0/file52.jpg
        File file = new File("/storage/emulated/0/file52.jpg");
        RequestBody rbFile = RequestBody.create(MediaType.parse("image/jpeg"),file);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file","test.jpg",rbFile);

        Call<String> call = mLoginService.uploadFileByPost(rbFileDesc,filePart);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    //Retrofit + Post + PartMap上传文件
    public void uploadFileByPostPartMap(String fileType,String fileDesc){

        Map<String,RequestBody> params = new HashMap<>();

        RequestBody rbFileType = RequestBody.create(MediaType.parse("text/pain"),fileType);
        RequestBody rbfileDesc = RequestBody.create(MediaType.parse("text/pain"),fileDesc);

        Map<String,MultipartBody.Part> partMapFiles = new HashMap<>();

        File file1 = new File("/storage/emulated/0/file52.jpg");
        RequestBody rbFile1 = RequestBody.create(MediaType.parse("image/*"),file1);

        File file2 = new File("/storage/emulated/0/file53.jpg");
        RequestBody rbFile2 = RequestBody.create(MediaType.parse("image/*"),file2);


        params.put("fileType",rbFileType);
        params.put("fileDesc",rbfileDesc);
        params.put("file\";filename=\"" + file1.getName() + "",rbFile1);
        params.put("file\";filename=\"" + file2.getName() + "",rbFile2);


        Call<String> call = mLoginService.uploadFileByPostPartMap(params);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    //Retrofot + Post + Part + List<MultipartBody.Part>
    public void uploadFileByPostPartLis(){
        List<MultipartBody.Part> fileLists = new ArrayList<>();

        //MultipartBody.Part file1 = MultipartBody.Part.createFormData()
    }

}
