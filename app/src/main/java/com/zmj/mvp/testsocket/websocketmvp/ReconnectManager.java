package com.zmj.mvp.testsocket.websocketmvp;

import android.os.Handler;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 负责断开重连
 * @author Zmj
 * @date 2018/11/27
 */
public class ReconnectManager {

    private static final String TAG = ReconnectManager.class.getSimpleName();

    private WebSocketThread mWebSocketThread;

    /**
     * 是否正在重连
     */
    private volatile boolean retrying;
    private volatile boolean destoryed;
    private final ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

    public ReconnectManager(WebSocketThread mWebSocketThread) {
        this.mWebSocketThread = mWebSocketThread;
        retrying = false;
        destoryed = false;
    }

    /**
     * 开始重新连接，连接方式为每个500ms连接一次，持续十五次。
     */
    synchronized void performReconnect(){
        if (retrying){
            Log.d(TAG, "performReconnect: WebSocket 正在重连，请勿虫回复调用");
        }else {
            retry();
        }
    }

    /**
     * 开始重连
     */
    private synchronized void retry() {
        if (!retrying){
            retrying = true;
            synchronized (singleThreadPool){
                singleThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        retrying = true;
                        for (int i = 0;i < 20; i++){
                            if (destoryed){
                                retrying = false;
                                return;
                            }
                            Handler handler = mWebSocketThread.getHandler();
                            WebSocketClient websocket = mWebSocketThread.getmWebSocket();
                            if (handler != null && websocket != null){
                                if (mWebSocketThread.getConnectState() == 2){
                                    return;
                                }else if (mWebSocketThread.getConnectState() == 1){
                                    continue;
                                }else {
                                    handler.sendEmptyMessage(MessageType.CONNECT);//发送handler进行连接
                                }
                            }else {
                                Log.d(TAG, "run: WebSocket handler/websocketClient没有实例化");
                                break;
                            }
                            try {
                                Thread.sleep(500);
                            }catch (Exception e){
                                Log.d(TAG, "run: " + e.getMessage());
                                e.printStackTrace();
                                if (destoryed == true){
                                    retrying = false;
                                    return;
                                }else {
                                    Thread.currentThread().interrupt();
                                }
                            }
                        }
                        retrying = false;
                    }
                });
            }
        }
    }

    /**
     * 销毁资源并停止重连
     */
    public void destory(){
        destoryed = true;
        if (singleThreadPool != null){
            singleThreadPool.shutdown();
        }
        mWebSocketThread = null;
    }
}
