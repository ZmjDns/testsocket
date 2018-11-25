package com.zmj.mvp.testsocket.utils;

import com.zmj.mvp.testsocket.bean.WebSocketChatMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Zmj
 * @date 2018/11/22
 */
public class EncodeAndDecodeJson {
    /**
     * 将WebSocketChatMessage对象转成JSONString
     * @param message
     * @return
     */
    public static String getSendMsg(WebSocketChatMessage message){
        JSONObject msgJson = new JSONObject();
        try {
            msgJson.put("time",String.valueOf(message.getTime()));
            msgJson.put("fromUser",message.getFromUser());
            msgJson.put("toUser",message.getToUser());
            msgJson.put("content",message.getContent());

            return msgJson.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "UNKONWNMSG";
        }
    }

    /**
     * 将JsonString转化成WebSocketChatMessage对象
     * @param msg
     * @return
     */
    public static WebSocketChatMessage getWebSocketChatMessageObj(String msg){
        WebSocketChatMessage message = new WebSocketChatMessage();
        try {
            JSONObject msgJson = new JSONObject(msg);
            message.setTime(Long.parseLong(msgJson.optString("time")));
            message.setFromUser(msgJson.optString("fromUser"));
            message.setToUser(msgJson.optString("toUser"));
            message.setContent(msgJson.optString("content"));
            return message;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
