package com.zmj.mvp.testsocket.websocketmvp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zmj.mvp.testsocket.bean.WebSocketChatMessage;
import com.zmj.mvp.testsocket.utils.EncodeAndDecodeJson;


/**
 * @author Zmj
 * @date 2018/11/28
 */
public abstract class AbsWebSocketAct extends AppCompatActivity implements IWebSocketPage {
    protected final String TAG = this.getClass().getSimpleName();

    private WebSocketServiceConnectManager mConnectManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //设置断开重连
        WebSocketSetting.setReconnectWithNetWorkChanged(true);

        //启动WebSocketService
        startService(new Intent(this,WebSocketService.class));


        super.onCreate(savedInstanceState);
        mConnectManager = new WebSocketServiceConnectManager(this,this);
        mConnectManager.onCreate();


    }

    @Override
    public void sendText(String text) {
        mConnectManager.sendText(text);
    }

    @Override
    public void reconnect() {
        mConnectManager.reconnect();
    }

    /**
     * 绑定WebSocketService成功，再此进行数据初始化
     */
    @Override
    public void onServiceBindSuccess() {
        Log.d(TAG, "onServiceBindSuccess: 绑定WebSocketService成功");
    }

    /**
     * WebSocket连接成功回调
     */
    @Override
    public void onConnected() {
        Log.d(TAG, "onConnected: WebSocket连接成功");
        SharedPreferences sharedPreferences = getSharedPreferences("user",0);
        String loginUser = sharedPreferences.getString("小米","");
        WebSocketChatMessage chatMessage = new WebSocketChatMessage(System.currentTimeMillis(),loginUser,"server","login");
        sendText(EncodeAndDecodeJson.getSendMsg(chatMessage));
    }

    /**
     * WebSock 连接出错回调
     * @param cause  出错原因
     */
    @Override
    public void onConnectError(Throwable cause) {
        Log.d(TAG, "onConnectError: WebSock 连接出错" + cause.getMessage());
    }

    /**
     * WebSocket连接断开回调
     */
    @Override
    public void onDisconnected() {
        Log.d(TAG, "onDisconnected: WebSocket连接断开");
    }

    @Override
    protected void onDestroy() {
        mConnectManager.onDestory();
        super.onDestroy();
    }
}
