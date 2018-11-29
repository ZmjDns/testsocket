package com.zmj.mvp.testsocket.websocketmvp;

/**
 * 用于处理及分发接收到的消息的接口
 * 如果需要自定义事件的分发，实现这个接口并设置到{@link WebSocketSetting}中即可
 * @author Zmj
 * @date 2018/11/28
 */
public interface IResponseDispatcher {
    /**
     * 连接成功
     * @param delivery 消息传递
     */
    void onConnected(ResponseDelivery delivery);

    /**
     * 连接失败
     * @param cause 失败原因
     * @param delivery 消息传递
     */
    void onConnectError(Throwable cause,ResponseDelivery delivery);

    /**
     * 连接断开
     * @param delivery 传递消息
     */
    void onDisconnected(ResponseDelivery delivery);

    /**
     * 接收到消息
     * @param message   消息
     * @param delivery  传递消息
     */
    void onMessageResponse(Response message,ResponseDelivery delivery);

    /**
     * 消息发送失败或接收到错误消息
     * @param error  错误消息
     * @param delivery  传递消息
     */
    void onSendMessageError(ErrorResponse error,ResponseDelivery delivery);

}
