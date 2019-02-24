package com.zmj.mvp.testsocket.retrofit;

import android.provider.MediaStore;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Zmj
 * @date 2019/1/18
 */
public class UseRetrofit {

    private Retrofit retrofit;
    private GetRequest_Interface service;

    public UseRetrofit() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.254:8080/myssm/")
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())    //支持RxJava平台
                .build();
        this.service = retrofit.create(GetRequest_Interface.class);
    }

    public Call<ResponseBody> useGet(){
        Call<ResponseBody> bodyCall =  service.getCall();
        return bodyCall;

    }

    //基本使用
    public void multipartUpload(){
        //@FormUrlEncode
        service.testUrlFormEncoded1("aaa","12354");


        //@Multipart
        MediaType textType = MediaType.parse("text/plain");
        RequestBody name = RequestBody.create(textType,"aaa");
        RequestBody pass = RequestBody.create(textType,"12354");
        RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"),"这里是模拟文件内容");


        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file","text.txt",file);
        //发送请求之后获取结果
        Call<ResponseBody> call = service.testFileUpload(name,pass,filePart);
    }

    //FormUrlEncoded 中的map封装提交
    public void formUrlEmcoded(){
        //@Field
        Call<ResponseBody> call = service.testUrlFormEncoded2("aaaa","12354");

        //@FieldMap
        Map<String,Object> map = new HashMap<>();
        map.put("name","aaaa");
        map.put("password","12354");

        Call<ResponseBody> call1 = service.testUrlFormEncoded3(map);
    }

    //@part 和@PartMap的使用
    public void partAndpartMap(){
        MediaType textType = MediaType.parse("text/plain");
        RequestBody name = RequestBody.create(textType,"aaa");
        RequestBody pass = RequestBody.create(textType,"12345");
        RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"),"这里是测试内容");

        //@Part
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file","test.txt",file);
        Call<ResponseBody> call = service.testFileUpload1(name,pass,filePart);

        //@PartMap
        //实现与上面同样的效果
        Map<String,RequestBody> fileUpload2Args = new HashMap<>();
        fileUpload2Args.put("name",name);
        fileUpload2Args.put("password",pass);

        //这里并不会被当成文件，因为没有文件名（包含在Content-Disposition请求头中），但上面的filePart中有
        //fileUpload2Args.put("file",file);

        Call<ResponseBody> call1 = service.testFileUpload2(fileUpload2Args,filePart);//文件需要单独处理(MultipartBody.Part)


    }
}
