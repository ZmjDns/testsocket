package com.zmj.mvp.testsocket.websocketmvp;

import com.zmj.mvp.testsocket.utils.Constant;

/**
 * @author Zmj
 * @date 2018/11/27
 */
public class MessageType {
    public static final int CONNECT = 0;    //连接WebSocket
    public static final int DISCONNECT = 1;//断开WebSocket，主动或被动关闭
    public static final int QUIT = 2;      //结束线程
    public static final int SEND_MSG = 3;   //通过webSocket连接发送消息
    public static final int RECEIVE_MSG = 4;//通过webSocket连接接收消息

    public static final String WebSocketUrl = Constant.WSHOST;
//    public static final String WebSocketUrl = "ws://192.168.3.45:8080/websocketserver/websocketreconstruct";
//    public static final String WebSocketUrl = "ws://140.143.11.231:8071/websocketserver/websocketreconstruct";
}
