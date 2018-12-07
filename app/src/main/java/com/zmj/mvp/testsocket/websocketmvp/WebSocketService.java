package com.zmj.mvp.testsocket.websocketmvp;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

/**
 * @author Zmj
 * @date 2018/11/28
 */
public class WebSocketService extends Service implements SocketListener {

    //webSocket核心线程
    private WebSocketThread mWebSocketThread;
    //消息响应分发
    private ResponseDelivery mResponseDelivery = new ResponseDelivery();
    //处理消息接口
    private IResponseDispatcher responseDispatcher;

    //监听网络变化广播是否注册
    private boolean networkChangedReceiverRegist = false;
    //监听网络变化
    private NetworkChangedReceiver networkChangedReceiver;

    /**
     * 绑定Service
     */
    private WebSocketService.ServiceBinder serviceBinder = new WebSocketService.ServiceBinder();
    public class ServiceBinder extends Binder{
        public WebSocketService getService(){
            return WebSocketService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (serviceBinder == null){
            serviceBinder = new WebSocketService.ServiceBinder();
        }
        return serviceBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //创建核心线程，添加监听，启动线程
        mWebSocketThread = new WebSocketThread();
        mWebSocketThread.setmSocketListener(this);
        mWebSocketThread.start();

        //实例化消息分发接口
        responseDispatcher = WebSocketSetting.getResponseProcessDelivery();

        //绑定监听网络变化广播
        if (WebSocketSetting.isReconnectWithNetWorkChanged()){
            //动态注册广播
            networkChangedReceiver = new NetworkChangedReceiver(this);
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
            filter.addAction("android.net.wifi.STATE_CHANGE");
            registerReceiver(networkChangedReceiver,filter);
            networkChangedReceiverRegist = true;
        }
    }

    /**
     * 销毁资源
     */
    @Override
    public void onDestroy() {
        mWebSocketThread.getHandler().sendEmptyMessage(MessageType.QUIT);
        if (networkChangedReceiverRegist && networkChangedReceiver != null){
            unregisterReceiver(networkChangedReceiver);
        }
        super.onDestroy();
    }


    /**
     * 发送文本消息
     * @param text
     */
    public void sendText(String text){
        if (mWebSocketThread.getHandler() == null){
            ErrorResponse errorResponse = new ErrorResponse();

            errorResponse.setErrorCode(3);//3-WebSocket 初始化未完成
            errorResponse.setCause(new Throwable("WebSocket Does not initialiation!"));
            errorResponse.setResponseText(text);

            onSendMessageError(errorResponse);//发送错误的消息
        }else {//通过WebSocketThread的Haandler发送消息
            Message message = mWebSocketThread.getHandler().obtainMessage();
            message.what = MessageType.SEND_MSG;
            message.obj = text;
            mWebSocketThread.getHandler().sendMessage(message);
        }
    }

    /**
     * 添加一个WebSocket事件监听器
     * @param listener
     */
    public void addListener(SocketListener listener){
        mResponseDelivery.addListener(listener);
    }

    /**
     * 移除一个WebSocket事件监听
     * @param listener
     */
    public void removeListener(SocketListener listener){
        mResponseDelivery.removeListener(listener);
    }

    /**
     * 连接WebSocket
     */
    public void reconnnet(){
        if (mWebSocketThread == null){
            onConnectError(new Throwable("WebSocketThread does not ready"));
        }else {
            mWebSocketThread.getHandler().sendEmptyMessage(MessageType.CONNECT);
        }
    }

    @Override
    public void onConnected() {
        responseDispatcher.onConnected(mResponseDelivery);
    }

    @Override
    public void onConnectError(Throwable cause) {
        responseDispatcher.onConnectError(cause,mResponseDelivery);
    }

    @Override
    public void onDisconnected() {
        responseDispatcher.onDisconnected(mResponseDelivery);
    }

    @Override
    public void onMessageResponse(Response message) {
        responseDispatcher.onMessageResponse(message,mResponseDelivery);
    }

    @Override
    public void onSendMessageError(ErrorResponse error) {
        responseDispatcher.onSendMessageError(error,mResponseDelivery);
    }


}
