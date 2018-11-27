package com.zmj.mvp.testsocket.websocket;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

/**
 * @author Zmj
 * @date 2018/11/21
 * 单例模式
 */
public class MyWebSocketClient extends WebSocketClient {
    
    private static final String TAG = "MyWebSocketClient";

    private Context mContext;

    private static MyWebSocketClient mInstance;

    //私有构造方法
    private MyWebSocketClient(Context context) {
        //super(URI.create("ws://192.168.3.45:8080/websocketserver/websocketservertest"));   ///websocketserver/websocketservertest
        //super(URI.create("ws://192.168.3.45:8080/websocketserver/websoketplus"));
        super(URI.create("ws://192.168.3.45:8080/websocketserver/websocketreconstruct"));
        this.mContext = context;
    }

    //对外公开的方法
    public static MyWebSocketClient getInstance(Context context){
        if (mInstance == null){
            synchronized (MyWebSocketClient.class){
                if (mInstance == null){
                    mInstance = new MyWebSocketClient(context);
                }
            }
        }
        return mInstance;
    }
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d(TAG, "onOpen: ...MyyWebSocketClient...onOpen..." + handshakedata.getHttpStatusMessage());
        if (mInstance.getReadyState() == READYSTATE.OPEN){
            openSuccessListener.onOpenMsg(handshakedata);
        }else {
            Log.d(TAG, "onOpen: WebSocket连接没有成功");
        }

    }

    @Override
    public void onMessage(String message) {
        mContext.getClass();
        Log.d(TAG, "onMessage: 获取到的消息" + message);
        if (!TextUtils.isEmpty(message)){
            getMessgeListener.getMsg(message);
        }
    }

    //传输ByteBuffer
    @Override
    public void onMessage(ByteBuffer bytes) {
        super.onMessage(bytes);
    }

    //长链接关闭
    //在各种因素导致WebSocket的长链接断开时会回调onClose()方法,
    // 所以可以在此发起重新连接;(客户端设备ID和用户UserID绑定失败时也需要发起重新连接然后再次进行绑定）
    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(TAG, "onClose: ...MyyWebSocketClient...onClose..." + "code:" + code + " reason" + reason + " remote:" + remote);
        if (code != 1000){
            mInstance.reconnect();
        }
    }

    @Override
    public void onError(Exception ex) {
        Log.d(TAG, "onError: 出错了" + ex.getMessage());
        ex.printStackTrace();
        mInstance.reconnect();
    }

    //手动断开连接
    public void clientClose(){
        mInstance.close();
        mInstance = null;
    }

    //连接成功的监听
    public OpenSuccessListener openSuccessListener;
    interface OpenSuccessListener{
        void onOpenMsg(ServerHandshake serverHandshake);
    }
    public void setOnOpenSuccessListener(OpenSuccessListener openSuccessListener) {
        this.openSuccessListener = openSuccessListener;
    }

    //获取消息的监听
    public GetMessgeListener getMessgeListener;
    interface GetMessgeListener {
        void getMsg(final String msg);
    }
    public void setOnGetMessgeListener(GetMessgeListener getMessgeListener) {
        this.getMessgeListener = getMessgeListener;
    }


}
