package com.zmj.mvp.testsocket.netkuangjia;

import java.util.Map;

/**
 * @author Zmj
 * @date 2018/12/17
 */
public interface IHttpProcessor {
    //网络访问方法 POST、GET、DEL、UPDATE、PUT
    void post(String url, Map<String,Object> params,ICallBack callBack);

    void get(String url,Map<String,Object> params,ICallBack callBack);
}
