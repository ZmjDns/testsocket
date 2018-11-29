package com.zmj.mvp.testsocket.websocketmvp;

import com.zmj.mvp.testsocket.bean.WebSocketChatMessage;

/**
 * @author Zmj
 * @date 2018/11/29
 */
public class ObjResponse implements Response<WebSocketChatMessage> {


    private WebSocketChatMessage chatMessage ;

    public ObjResponse(WebSocketChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }

    @Override
    public String getResponseText() {
        return null;
    }

    @Override
    public void setresponseText(String responseText) {

    }

    @Override
    public WebSocketChatMessage getResonseEntity() {
        return chatMessage;
    }

    @Override
    public void setResponseEntity(WebSocketChatMessage responseEntity) {
        this.chatMessage = responseEntity;
    }
}
