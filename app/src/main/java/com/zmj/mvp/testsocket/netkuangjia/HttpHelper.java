package com.zmj.mvp.testsocket.netkuangjia;

import java.util.HashMap;
import java.util.Map;

/**
 * 代理类
 * @author Zmj
 * @date 2018/12/17
 */
public class HttpHelper implements IHttpProcessor {
    private static IHttpProcessor mIHttpProcessor = null;

    private Map<String,Object> mParams;

    private static HttpHelper _instance;

    private HttpHelper (){
        mParams = new HashMap<>();
    }

    public static HttpHelper obtaion(){
        if (_instance == null){
            synchronized (IHttpProcessor.class){
                if (_instance == null){
                    _instance = new HttpHelper();
                }
            }
        }
        return _instance;
    }

    public static void init(IHttpProcessor httpProcessor){
        mIHttpProcessor = httpProcessor;
    }

    @Override
    public void post(String url, Map<String, Object> params, ICallBack callBack) {
        final String finalUrl = appendParams(url,params);
        mIHttpProcessor.post(finalUrl,params,callBack);
    }

    @Override
    public void get(String url, Map<String, Object> params, ICallBack callBack) {
        final String finalUrl = appendParams(url,params);
        mIHttpProcessor.get(finalUrl,params,callBack);
    }

    //url中拼接请求数据
    private String appendParams(String url, Map<String, Object> params) {

        return null;
    }
}
