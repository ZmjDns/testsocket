package com.zmj.mvp.testsocket.websocketmvp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

/**
 * 负责webSocketService的绑定、SocketListener监听等操作
 * @author Zmj
 * @date 2018/11/28
 */
public class WebSocketServiceConnectManager {
    private static final String TAG = WebSocketServiceConnectManager.class.getSimpleName();

    private Context context;
    //需要绑定WebSocketService的页面应该实现的接口
    private IWebSocketPage webSocketPage;
    //获取主线程
    private Handler mHamdler = new Handler(Looper.getMainLooper());

    /**
     * WebSocketService 服务是否绑定成功
     */
    private boolean webSocketServiceBindSussess = false;
    protected WebSocketService mWebSocketService;

    private int bindTime = 0;
    /**
     * 是否在绑定服务
     */
    private boolean binding = false;
    /**
     * 绑定Service
     */
    protected ServiceConnection mWebSocketServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            webSocketServiceBindSussess = true;
            binding = false;
            bindTime = 0;
            mWebSocketService = ((WebSocketService.ServiceBinder)service).getService();
            mWebSocketService.addListener(mSocketListener);
            webSocketPage.onServiceBindSuccess();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binding = false;
            webSocketServiceBindSussess = false;
            Log.d(TAG, "onServiceDisconnected: WebSocketService绑定失败" + name);
            if (bindTime < 5 && !binding){
                Log.d(TAG, String.format("WebSocketService 断开连接，开始第%s次重连",bindTime));
                bindService();
            }
        }
    };

    private SocketListener mSocketListener = new SocketListener() {
        @Override
        public void onConnected() {
            mHamdler.post(new Runnable() {
                @Override
                public void run() {
                    webSocketPage.onConnected();
                }
            });
        }

        @Override
        public void onConnectError(final Throwable cause) {
            mHamdler.post(new Runnable() {
                @Override
                public void run() {
                    webSocketPage.onConnectError(cause);
                }
            });
        }

        @Override
        public void onDisconnected() {
            mHamdler.post(new Runnable() {
                @Override
                public void run() {
                    webSocketPage.onDisconnected();
                }
            });
        }

        @Override
        public void onMessageResponse(final Response message) {
            mHamdler.post(new Runnable() {
                @Override
                public void run() {
                    webSocketPage.onMessageResponse(message);
                }
            });
        }

        @Override
        public void onSendMessageError(final ErrorResponse error) {
            mHamdler.post(new Runnable() {
                @Override
                public void run() {
                    webSocketPage.onSendMessageError(error);
                }
            });
        }
    };

    /**
     * 构造函数
     * @param context
     * @param webSocketPage
     */
    public WebSocketServiceConnectManager(Context context, IWebSocketPage webSocketPage) {
        this.context = context;
        this.webSocketPage = webSocketPage;
        webSocketServiceBindSussess = false;
    }

    /**
     * 初始化需要完成WebSocketService的绑定工作
     */
    public void onCreate(){
        bindService();
    }

    /**
     * 绑定WebSocketService
     */
    private void bindService(){
        binding = true;
        webSocketServiceBindSussess = false;
        Intent intent = new Intent(context,WebSocketService.class);
        context.bindService(intent,mWebSocketServiceConnection,Context.BIND_AUTO_CREATE);
        bindTime++;
    }

    /**
     * 发送文本消息
     * @param text
     */
    public void sendText(String text){
        if (webSocketServiceBindSussess && mWebSocketService != null){
            mWebSocketService.sendText(text);
        }else {
            ErrorResponse errorResponse = new ErrorResponse();

            errorResponse.setErrorCode(2);//2-WebSocketService 服务未绑定到当前 Activity/Fragment，或绑定失败
            errorResponse.setCause(new Throwable("WebSocketService does not bind!"));
            errorResponse.setRequestText(text);

            ResponseDelivery delivery = new ResponseDelivery();
            delivery.addListener(mSocketListener);
            WebSocketSetting.getResponseProcessDelivery().onSendMessageError(errorResponse,delivery);
            if (!binding){
                bindTime = 0;
                Log.d(TAG, String.format("WebSocketService 断开连接，正在进行第%s次重连",bindTime));
                bindService();
            }
        }
    }

    /**
     * 注意:
     * 如果WebSocketService已经绑定，就去连接WebSocket
     * 如果WebSocketService没有绑定就重新绑定WebSocketService,然后再WebSocketService里连接WebSocket
     */
    public void reconnect(){
        if (webSocketServiceBindSussess && mWebSocketService != null){
            mWebSocketService.reconnnet();//在此方法中重新连接WebSocket
        }else {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorCode(2);
            errorResponse.setCause(new Throwable("WebSocketService does not bind!"));

            ResponseDelivery delivery = new ResponseDelivery();
            WebSocketSetting.getResponseProcessDelivery().onSendMessageError(errorResponse,delivery);
            //重新绑定WebSocketService
            if (!binding){
                bindTime = 0;
                Log.d(TAG, String.format("WebSocketService 断开连接，正在进行第%s次重连",bindTime));
                bindService();
            }
        }
    }

    /**
     * 销毁资源
     */
    public void onDestory(){
        binding = false;
        bindTime = 0;
        context.unbindService(mWebSocketServiceConnection);
        Log.d(TAG, context.toString() + "已经解除WebSocketService的绑定");
        webSocketServiceBindSussess = false;
        mWebSocketService.removeListener(mSocketListener);
        //context 和mWebSocketService 不用销毁吗？
    }
}
