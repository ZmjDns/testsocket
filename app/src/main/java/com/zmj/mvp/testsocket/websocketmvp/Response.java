package com.zmj.mvp.testsocket.websocketmvp;

/**
 * webSocket 响应数据接口
 * @author Zmj
 * @date 2018/11/27
 */
public interface Response<T> {
    /**
     * 获取响应的文本数据
     * @return
     */
    String getResponseText();

    /**
     * 设置响应的文本数据
     */
    void setresponseText(String responseText);

    /**
     * 获取该数据的实体，可能为空，具体看实现类
     */
    T getResonseEntity();
    /**
     * 设置数据实体
     */
    void setResponseEntity(T responseEntity);
}
