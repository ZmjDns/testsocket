package com.zmj.mvp.testsocket.chatview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zmj.mvp.testsocket.bean.WebSocketChatMessage;
import com.zmj.mvp.testsocket.utils.EncodeAndDecodeJson;
import com.zmj.mvp.testsocket.websocketmvp.IWebSocketPage;
import com.zmj.mvp.testsocket.websocketmvp.Response;
import com.zmj.mvp.testsocket.websocketmvp.WebSocketService;
import com.zmj.mvp.testsocket.websocketmvp.WebSocketServiceConnectManager;
import com.zmj.mvp.testsocket.websocketmvp.WebSocketSetting;

/**
 * @author Zmj
 * @date 2018/11/29
 */
public abstract class BaseAct extends AppCompatActivity implements IWebSocketPage {

    private final String TAG = this.getClass().getSimpleName();

    private WebSocketServiceConnectManager connectManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置断开重连
        WebSocketSetting.setReconnectWithNetWorkChanged(true);
        //启动WebSocketService
        Intent servierIntent = new Intent(this,WebSocketService.class);
        startService(servierIntent);

        connectManager = new WebSocketServiceConnectManager(this,this);
        connectManager.onCreate();
    }

    @Override
    public void onServiceBindSuccess() {
        Log.d(TAG, "onServiceBindSuccess: WebSocketService 绑定成功");
    }

    @Override
    public void onConnected() {
        Log.d(TAG, "onConnected: WebSocket 连接成功");
        //连接成功进行登陆
        SharedPreferences sharedPreferences = getSharedPreferences("testuser",0);
        String loginUser = sharedPreferences.getString("me","");

        WebSocketChatMessage chatMessage = new WebSocketChatMessage(System.currentTimeMillis(),loginUser,"server","login",1);

        sendText(EncodeAndDecodeJson.getSendMsg(chatMessage));
    }

    @Override
    public void onConnectError(Throwable cause) {
        Log.d(TAG, "onConnectError: 连接出错：" + cause.getMessage());
        connectManager.reconnect();
    }

    @Override
    public void onDisconnected() {
        Log.d(TAG, "onDisconnected: WebSock 断开");
    }

    @Override
    public void sendText(String text) {
        connectManager.sendText(text);
    }

    @Override
    public void reconnect() {
        connectManager.reconnect();
    }

    @Override
    protected void onDestroy() {
        connectManager.onDestory();
        Intent intent = new Intent(this, WebSocketService.class);
        stopService(intent);
        Log.d(TAG, "onDestroy: WebSocketService已经关闭");
        super.onDestroy();
    }
}
