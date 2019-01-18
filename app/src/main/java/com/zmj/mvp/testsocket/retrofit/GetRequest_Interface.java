package com.zmj.mvp.testsocket.retrofit;

import com.zmj.mvp.testsocket.bean.ResponseBean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author Zmj
 * @date 2019/1/18
 * 1.用动态代理动态 将该接口的注解“翻译”成一个合同谈判请求，最后再执行HTTP请求
 * 2.接口中的每一个方法的参数都要使用注解标注
 */
public interface GetRequest_Interface {
    @GET("action=login")
    Call<ResponseBean> getCall();
    //getCall() 接收网络数据的方法
    //Call<*> 返回数据的类型
    //如果想直接获取ResponseBody中的内容，可以定义网络请求返回值为Call<ResponseBody>


    /**
     *method 网络请求方法 区分大小写
     * path 网络请求地址
     * hasBody 是否能有请求体
     * @return
     */
    @HTTP(method = "GET",path = "index.jsp{id}",hasBody = false)
    Call<ResponseBean> newGetCall();
    //{id}表示是一个变量
    //method的值 retrofit不会处理，要保证准确

    /**
     * 表名是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
     * @param name
     * @param password
     * @return
     */
    @POST("/form")
    @FormUrlEncoded
    Call<ResponseBody> testUrlFormEncoded1(@Field("username")String name,@Field("password")String password);

    /**
     * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link MultipartBody.Part}、任意类型
     * 除{@link MultipartBody.Part}以外，其他类型都必须带上表单字段（{@link MultipartBody.Part}中已经包含了表单字段信息）
     * @param name
     * @param age
     * @param file
     * @return
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);
}
