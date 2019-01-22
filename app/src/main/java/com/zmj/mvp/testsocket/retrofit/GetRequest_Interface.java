package com.zmj.mvp.testsocket.retrofit;

import com.zmj.mvp.testsocket.bean.ResponseBean;
import com.zmj.mvp.testsocket.bean.User;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @author Zmj
 * @date 2019/1/18
 * 1.用动态代理动态 将该接口的注解“翻译”成一个合同谈判请求，最后再执行HTTP请求
 * 2.接口中的每一个方法的参数都要使用注解标注
 * https://blog.csdn.net/carson_ho/article/details/73732076
 */
public interface GetRequest_Interface {
    @GET("?action=login")
    Call<ResponseBody> getCall();
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




    //详细说明
    /**
     * A @Header
     * 带请求头{@link Header} 和 不固定请求头{@link retrofit2.http.Headers}
     * 二者效果是一致的，区别在于使用场景和使用方式
     * @ Header用于添加不固定的请求头，@Headers用于添加固定的请求头
     * @ Header作用于方法的参数，@headers作用于方法
     */
    @GET("user")
    Call<User> getUser(@Header("Authorization") String authorzation);
    @GET("user")
    @Headers("Authrization:authrization")
    Call<User> getUser();

    /**
     * B @Body
     * 作用：以Post方式传递 自定义数据类型 给服务器
     * 特别注意：如果提交的是一个Map，那么作用相当于@Field
     * 此时Map需要经过{@link FromBody.Builder } 类处理成符合 OkHttp 格式的表单
     * FormBody.Builder builder = new FormBody.Builder();
     * builder.add("key","value");
     */

    /**
     * C @Field @FieldMap
     * 作用：发送Post请求时提交请求的表单字段
     * 具体使用：与{@link FormUrlEncoded} 注解配合使用
     */
    @POST("/form")
    @FormUrlEncoded
    Call<ResponseBody> testUrlFormEncoded2(@Field("username")String name,@Field("password")String password);
    /**
     * map的key 将作为表单的键  value作为键值
     * 具体使用：
     */
    @POST("/form")
    @FormUrlEncoded
    Call<ResponseBody> testUrlFormEncoded3(@FieldMap Map<String,Object> map);


    /**
     * D @Part  @PartMap
     * 作用：发送Post请求的时候  提交请求的表单字段
     * 与 @Filed的区别： 功能相同，但携带的参数更加丰富，包括数据流，所以使用于有文件上传的场景
     * 具体使用：与@Multipart注解配合使用
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload1(@Part("name") RequestBody name,@Part("password") RequestBody pass,@Part MultipartBody.Part file);
    /**
     * {@link retrofit2.http.PartMap} 注解支持一个map作为参数，支持{@link RequestBody}类型
     * 如果有其他类型，会被{@link retrofit2.Converter}转换  如（使用{@link com.google.gson.Gson}的{@link retrofit2.converter.gson.GsonRequestBodyConverter}）
     * 所以{@link MultipartBody.Part} 就不适用了，所以文件只能用 @Part MultipartBody.Part
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload2(@PartMap Map<String,RequestBody> args,@Part MultipartBody.Part file);

    @POST
    @Multipart
    Call<ResponseBody> testFileUpload3(@PartMap Map<String,RequestBody> args);


    /**
     * E @Query  @QueryMap
     * 作用：用于@GET方法的查询参数（Query = Url中 '?' 后面的 key-value）
     * 如：url = http://www.println.net/?cate=android，其中，Query = cate
     * 具体使用：配置时只需要只需要在接口方法中增加一个参数就行
     */
    @GET("/")
    Call<String> cate(@Query("cate") String cate);


    /**
     * F @Path
     * 作用：URL地址的缺省值
     * 具体使用：
     */
    @GET("users/{user}/repos")
    Call<RequestBody> getBlog(@Path("user") String user);
    //访问的API是：https：//api.github.com/users/{user}/repos
    //请求时，{user}会被替换为方法的第一个参数


    /**
     * G @URL
     * 作用：直接传入一个请求的URL变量 用于URL设置
     * 具体使用：
     */
    @GET
    Call<ResponseBody> testUrlAndQuery(@Url String url,@Query("showAll") boolean showAll);
    //当有URL注解时，@GET传入的URL就可以省略
    //当GET、POST....HTTP等方法中没有设置Url时，则必须使用{@link Url}提供



}
