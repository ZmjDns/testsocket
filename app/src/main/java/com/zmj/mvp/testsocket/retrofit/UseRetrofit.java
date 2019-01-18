package com.zmj.mvp.testsocket.retrofit;

import android.provider.MediaStore;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * @author Zmj
 * @date 2019/1/18
 */
public class UseRetrofit {

    private Retrofit retrofit;

    public UseRetrofit(Retrofit retrofit) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.254:8080/tadb/")
                .build();
    }

    //不带请求头的
    public void multipartUpload(){
        GetRequest_Interface service = retrofit.create(GetRequest_Interface.class);
        //@FormUrlEncode
        service.testUrlFormEncoded1("aaa","12354");


        //@Multipart
        MediaType textType = MediaType.parse("text/plain");
        RequestBody name = RequestBody.create(textType,"aaa");
        RequestBody pass = RequestBody.create(textType,"12354");
        RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"),"这里是模拟文件内容");

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file","text.txt",file);

        service.testFileUpload(name,pass,filePart);
    }



}
