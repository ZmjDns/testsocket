package com.zmj.mvp.testsocket.glide;

import com.bumptech.glide.load.model.GlideUrl;

import java.net.URL;

/**
 * 请求地址带有token的
 * @author Zmj
 * @date 2018/12/28
 */
public class MyGlideUrl extends GlideUrl {

    private String mUrl;

    public MyGlideUrl(String url) {
        super(url);
        mUrl = url;
    }

    @Override
    public String getCacheKey() {

        return mUrl.replace(findTokenParam(),"");
    }

    private String findTokenParam() {
        String tokenParam = "";
        int tokenkeyIndex = mUrl.indexOf("?token=") >= 0 ? mUrl.indexOf("?token=") : mUrl.indexOf("&token=");
        if (tokenkeyIndex != -1){
            int nextAndIndex = mUrl.indexOf("&",tokenkeyIndex + 1);
            if (nextAndIndex != -1){
                tokenParam = mUrl.substring(tokenkeyIndex + 1,nextAndIndex + 1);
            }else {
                tokenParam = mUrl.substring(tokenkeyIndex);
            }
        }

        return tokenParam;
    }
}
