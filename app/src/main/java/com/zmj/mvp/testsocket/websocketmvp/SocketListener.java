package com.zmj.mvp.testsocket.websocketmvp;

/**
 * webSocket监听
 * @author Zmj
 * @date 2018/11/27
 */
public interface SocketListener {
    /**
     * 连接成功
     */
    void onConnected();

    /**
     * 连接发送错误
     * @param cause
     */
    void onConnectError(Throwable cause);

    /**
     * 断开连接
     */
    void onDisconnected();

    /**
     * 接收消息
     * @param message
     */
    void onMessageResponse(Response message);

    /**
     * 消息发送失败或收到错误消息
     */
    void onSendMessageError(ErrorResponse error);
}
