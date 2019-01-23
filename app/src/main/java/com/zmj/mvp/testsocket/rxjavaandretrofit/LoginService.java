package com.zmj.mvp.testsocket.rxjavaandretrofit;

import com.zmj.mvp.testsocket.bean.LoginResult;

import java.util.Map;
import java.util.Observable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author Zmj
 * @date 2019/1/21
 */
public interface LoginService {

    @FormUrlEncoded
    @POST("?action=login")
    io.reactivex.Observable<LoginResult> login(@Field("name") String name,@Field("password") String password);

    @GET(".")   //没有数据就写"."或"/"，不能为空
    Call<LoginResult> login2(@Query("action")String action,@Query("name")String name ,@Query("password")String psd);

    @GET("?action=login")
    Call<LoginResult> login3(@QueryMap Map<String, String> params);


    @POST(".")  //没有就写".",不能写"/" ,否则报404
    @FormUrlEncoded
    Call<LoginResult> loginByPost(@Field("action")String action,@Field("name")String name,@Field("password")String psd);

    @POST(".")
    @FormUrlEncoded     //将数据按加入的顺序排列
    Call<LoginResult> loginByPostFieldMap(@FieldMap Map<String,String> params);

    @POST(".")
    @Multipart      //用Part提交数据
    Call<LoginResult> loginByPostPart(@Part("action")RequestBody action, @Part("name")RequestBody name, @Part("password")RequestBody psd,@Part MultipartBody.Part file);

    @Multipart
    @POST("?action=uploadfile")
    Call<String> uploadFileByPost(@Part("fileType")RequestBody fileType,@Part MultipartBody.Part filePart);
}
