package com.zmj.mvp.testsocket.websocketmvp;

/**
 * @author Zmj
 * @date 2018/11/27
 */
public class TextResponse implements Response<String> {

    private String responseText;

    public TextResponse(String responseText) {
        this.responseText = responseText;
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
    public String getResonseEntity() {
        return null;
    }

    @Override
    public void setResponseEntity(String responseEntity) {

    }
}
