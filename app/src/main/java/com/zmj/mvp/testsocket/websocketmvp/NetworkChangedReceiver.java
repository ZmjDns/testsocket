package com.zmj.mvp.testsocket.websocketmvp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.RequiresPermission;
import android.util.Log;

/**
 * @author Zmj
 * @date 2018/11/28
 */
public class NetworkChangedReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkChangedReceiver.class.getSimpleName();

    private WebSocketService socketService;

    public NetworkChangedReceiver() {
    }

    public NetworkChangedReceiver(WebSocketService socketService) {
        this.socketService = socketService;
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            if (manager == null)return;
            NetworkInfo activeNetWork = manager.getActiveNetworkInfo();
            if (activeNetWork != null){
                if (activeNetWork.isConnected()){
                    if (activeNetWork.getType() == ConnectivityManager.TYPE_WIFI){
                        Log.d(TAG, "onReceive: 网络发送变化，当前WIFI连接可用，正在尝试重连");
                    }else if (activeNetWork.getSubtype() == ConnectivityManager.TYPE_MOBILE){
                        Log.d(TAG, "onReceive: 网络发送变化，当前移动网络连接可用，正在尝试重连");
                    }
                    if (socketService != null){
                        //重新连接
                        socketService.reconnnet();
                    }
                }else {
                    Log.d(TAG, "onReceive: 当前没有可用网络");
                }
            }
        }
    }
}
