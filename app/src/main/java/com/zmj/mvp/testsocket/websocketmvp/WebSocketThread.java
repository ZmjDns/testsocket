package com.zmj.mvp.testsocket.websocketmvp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.zmj.mvp.testsocket.bean.WebSocketChatMessage;
import com.zmj.mvp.testsocket.utils.EncodeAndDecodeJson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

/**
 * @author Zmj
 * @date 2018/11/27
 */
public class WebSocketThread extends Thread {

    private static final String TAG = WebSocketThread.class.getSimpleName();

    private String connectUrl;

    private WebSocketClient mWebSocket;     //webSocket对象

    private WebSocketHandler mHandler;

    private SocketListener mSocketListener; //状态监听

    private ReconnectManager mReconnectManager; //断开重连

    private boolean quit;

    /**
     * webSocket连接状态
     * 0  未连接
     * 1  正在连接
     * 2  连接成功
     */
    private int connectStatus = 0;

    WebSocketThread (){
        mReconnectManager = new ReconnectManager(this);
    }

    @Override
    public void run() {
        super.run();
        Looper.prepare();
        quit = false;
        mHandler = new WebSocketHandler();
        mHandler.sendEmptyMessage(MessageType.CONNECT);
        Looper.loop();
    }

    /**
     * 获取控制WebSocketThread的Handler
     */
    public Handler getHandler(){
        return mHandler;
    }

    /**
     * 获取WenSocketClient对象
     */
    public WebSocketClient getmWebSocket(){
        return mWebSocket;
    }

    /**
     * 设置WebSocket状态的监听
     */
    public void setmSocketListener(SocketListener socketListener){
        this.mSocketListener = socketListener;
    }

    /**
     * 获取连接状态
     */
    public int getConnectState(){
        return connectStatus;
    }

    /**
     * 重新连接
     */
    public void reConnect(){
        mReconnectManager.performReconnect();
    }

    private class WebSocketHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MessageType.CONNECT:
                    connect();
                    break;
                case MessageType.DISCONNECT:
                    disconnect();
                    break;
                case MessageType.QUIT:
                    quit();
                    break;
                case MessageType.SEND_MSG:
                    if (msg.obj instanceof String){
                        String message = (String) msg.obj;
                        if (mWebSocket != null && connectStatus == 2){
                           send(message);
                        }else if (mSocketListener != null){
                            ErrorResponse errorResponse = new ErrorResponse();
                            errorResponse.setErrorCode(1);
                            errorResponse.setCause(new Throwable("WebSoclket does not connected or closed"));
                            errorResponse.setRequestText(message);
                            mSocketListener.onSendMessageError(errorResponse);
                        }
                    }
                    break;
                case MessageType.RECEIVE_MSG:
                    if (mSocketListener != null && msg.obj.toString() instanceof String){
                        mSocketListener.onMessageResponse(new TextResponse(msg.obj.toString()));
                    }
                    break;
            }

        }

        //连接
        private void connect(){
            if (connectStatus == 0){    //没有连接
                connectStatus = 1;  //正在连接
                try{
                    if (mWebSocket == null){
                        mWebSocket = new WebSocketClient(new URI(MessageType.WebSocketUrl),new Draft_6455()) {
                            @Override
                            public void onOpen(ServerHandshake handshakedata) {
                                connectStatus = 2;//连接成功
                                Log.d(TAG, "onOpen: WebSocket连接成功");
                                if (mSocketListener != null){
                                    mSocketListener.onConnected();
                                }
                                Message msg = new Message();
                                msg.what = MessageType.SEND_MSG;
                                WebSocketChatMessage chatMessage = new WebSocketChatMessage(System.currentTimeMillis(),"18302451883","server","login");
                                msg.obj = EncodeAndDecodeJson.getSendMsg(chatMessage);
                                mHandler.sendMessage(msg);
                            }

                            @Override
                            public void onMessage(String message) {
                                connectStatus = 2;
                                Log.d(TAG, "onMessage: WebSocket 接收到String消息：" + message);
                                Message msg = new Message();
                                msg.what = MessageType.RECEIVE_MSG;
                                msg.obj = message;
                                mHandler.sendMessage(msg);
                            }

                            @Override   //接收到的ByteBuffer数据
                            public void onMessage(ByteBuffer bytes) {
                                connectStatus = 2;
                                Log.d(TAG, "onMessage: WebSocket 接收到ByteBuffer消息");
                            }

                            @Override
                            public void onClose(int code, String reason, boolean remote) {
                                connectStatus = 0;
                                Log.d(TAG, "onClose: WebSocket 断开连接 code:" + code + "reason:" + reason + remote );
                                if (mSocketListener != null){
                                    mSocketListener.onDisconnected();
                                }
                                if (code != 1000){ //非正常退出
                                    reConnect();
                                }
                            }

                            @Override
                            public void onError(Exception ex) {
                                connectStatus = 0;
                                Log.d(TAG, "onError: WebSocket 出错" + ex.getMessage());
                                //重新连接
                                reConnect();
                            }
                        };
                        Log.d(TAG, "connect: WebSocket 开始连接...");
                        mWebSocket.connect();
                    }else {
                        Log.d(TAG, "connect: WebSocket 开始重新连接......");
                        mWebSocket.reconnect();
                    }
                }catch (Exception e){
                    //发生错误走onError方法，在onError方法中重连
                    connectStatus = 0;
                    Log.d(TAG, "connect: WebSocket 连接失败");
                    if (mSocketListener != null){
                        mSocketListener.onConnectError(e);
                    }
                }
            }
        }
        //断开连接
        private void disconnect(){
            if (connectStatus == 2){
                Log.d(TAG, "disconnect: WebSocket 正在关闭连接");
                if (mWebSocket != null){
                    mWebSocket.close();
                }
                connectStatus = 0;
                Log.d(TAG, "disconnect: WebSocket 已经关闭连接");
            }
        }

        private void send(String text){
            if (mWebSocket != null && connectStatus == 2){
                try {
                    mWebSocket.send(text);
                    Log.d(TAG, "sendText: WebSocket 发生数据成功" + text);
                }catch (WebsocketNotConnectedException e){
                    connectStatus = 0;
                    Log.e(TAG, "sendText: 连接已断开" + text , e);
                    if (mSocketListener != null){
                        mSocketListener.onDisconnected();

                        ErrorResponse errorResponse = new ErrorResponse();
                        errorResponse.setErrorCode(1);
                        errorResponse.setCause(new Throwable("WebSoclket does not connected or closed"));
                        errorResponse.setRequestText(text);
                        mSocketListener.onSendMessageError(errorResponse);
                    }
                    reConnect();
                }
            }
        }

        /**
         * 结束此线程 并销毁资源
         */
        private void quit(){
            disconnect();
            mWebSocket = null;
            mReconnectManager.destory();
            quit = true;
            connectStatus = 0;
            Looper looper = Looper.myLooper();
            if (looper != null){
                looper.quit();
            }
            WebSocketThread.this.interrupt();
        }
    }

}
