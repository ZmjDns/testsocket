package com.zmj.mvp.testsocket.websocketmvp;

/**
 * @author Zmj
 * @date 2018/11/27
 */
public class WebSocketSetting {
    //消息分发接口
    private static IResponseDispatcher responseProcessDelivery;
    //设置改变网络状态是否重回新连接服务器
    private static boolean reconnectWithNetWorkChanged;

    /**
     * 获取当前已设置的消息分发器
     * @return
     */
    public static IResponseDispatcher getResponseProcessDelivery(){
        if (responseProcessDelivery == null){
            responseProcessDelivery = new DefaultResponseDispatcher();
        }
        return responseProcessDelivery;
    }

    /**
     * 设置消息分发器
     * @param responseProcessDelivery
     */
    public static void setResponseProcessDelivery(IResponseDispatcher responseProcessDelivery){
        WebSocketSetting.responseProcessDelivery = responseProcessDelivery;
    }

    /**
     * 获取网络变化后是否重连
     * @return
     */
    public static boolean isReconnectWithNetWorkChanged(){
        return  reconnectWithNetWorkChanged;
    }

    /**
     * 设置网络变化后是否重连
     * 如果为 true 需要注册广播{@link NetworkChangedReceiver}
     * 并添加ACCESS_NETWORK_STATE 权限
     * @param reconnectWithNetWorkChanged
     */
    public static void setReconnectWithNetWorkChanged(boolean reconnectWithNetWorkChanged){
        WebSocketSetting.reconnectWithNetWorkChanged = reconnectWithNetWorkChanged;
    }

}
