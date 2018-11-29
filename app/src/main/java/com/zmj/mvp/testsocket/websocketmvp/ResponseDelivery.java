package com.zmj.mvp.testsocket.websocketmvp;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息发射器
 * 内部维护一个{@link SocketListener} 的List，
 * 调用每一个方法都会通知List的中的每一个SocketListener，
 * 简化代码
 * @author Zmj
 * @date 2018/11/28
 */
public class ResponseDelivery implements SocketListener {

    private final List<SocketListener> mSocketListenerList = new ArrayList<>();

    public ResponseDelivery() {

    }

    /**
     * 添加SocketListener
     * @param listener
     */
    public void addListener(SocketListener listener){
        synchronized (mSocketListenerList){
            mSocketListenerList.add(listener);
        }
    }

    /**
     * 移除SocketListener
     * @param listener
     */
    public void removeListener(SocketListener listener){
        synchronized (mSocketListenerList){
            mSocketListenerList.remove(listener);
        }
    }


    @Override
    public void onConnected() {
        synchronized (mSocketListenerList){
            for (SocketListener listener : mSocketListenerList){
                listener.onConnected();
            }
        }
    }

    @Override
    public void onConnectError(Throwable cause) {
        synchronized (mSocketListenerList){
            for (SocketListener listener : mSocketListenerList){
                listener.onConnectError(cause);
            }
        }
    }

    @Override
    public void onDisconnected() {
        synchronized (mSocketListenerList){
            for (SocketListener listener : mSocketListenerList){
                listener.onDisconnected();
            }
        }
    }

    @Override
    public void onMessageResponse(Response message) {
        synchronized (mSocketListenerList){
            for (SocketListener listener : mSocketListenerList){
                listener.onMessageResponse(message);
            }
        }
    }

    @Override
    public void onSendMessageError(ErrorResponse message) {
        synchronized (mSocketListenerList){
            for (SocketListener listener : mSocketListenerList){
                listener.onSendMessageError(message);
            }
        }
    }
}
