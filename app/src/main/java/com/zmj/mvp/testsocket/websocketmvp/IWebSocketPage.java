package com.zmj.mvp.testsocket.websocketmvp;

/**
 * 需要使用WebSocketService 的页面应该实现的接口
 * @author Zmj
 * @date 2018/11/28
 */
public interface IWebSocketPage extends SocketListener {

    /**
     * WebSocketService绑定成功回调
     */
    void onServiceBindSuccess();

    /**
     * 通过webSocketService 发送数据
     * @param text 待发送的文本数据
     */
    void sendText(String text);
    /**
     * 重新连接，内部已经做了重连机制，一般不需要调这个
     */
    void reconnect();

}
