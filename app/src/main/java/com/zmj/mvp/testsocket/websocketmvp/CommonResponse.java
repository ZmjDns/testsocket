package com.zmj.mvp.testsocket.websocketmvp;

/**
 * @author Zmj
 * @date 2018/11/28
 */
public class CommonResponse implements Response<CommonResponseEntity> {

    private String responseText;
    private CommonResponseEntity responseEntity;

    public CommonResponse(String responseText, CommonResponseEntity responseEntity) {
        this.responseText = responseText;
        this.responseEntity = responseEntity;
    }

    @Override
    public String getResponseText() {
        return responseText;
    }

    @Override
    public void setresponseText(String responseText) {
        this.responseText = responseText;
    }

    @Override
    public CommonResponseEntity getResonseEntity() {
        return responseEntity;
    }

    @Override
    public void setResponseEntity(CommonResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }
}
