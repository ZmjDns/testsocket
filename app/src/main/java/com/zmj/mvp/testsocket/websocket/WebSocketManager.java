package com.zmj.mvp.testsocket.websocket;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketAdapter;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.util.Collection;

/**
 * @author Zmj
 * @date 2018/11/27
 */
public class WebSocketManager {
    private static WebSocketManager wsManager;

    private WebSocketClient webSocketClient;

    private WebSocketManager() {
        initWS();
    }

    public static WebSocketManager getInstance() {
        if (wsManager == null){
            synchronized (WebSocketManager.getInstance()){
                if (wsManager == null){
                    wsManager = new WebSocketManager();
                }
            }
        }
        return wsManager;
    }

    private void initWS(){
        String wsUrl = "ws://192.168.3.45:8080/websocketserver/websocketreconstruct";

    }


    /**
     * 监听WebSocket的所有状态
     */
    class WsListener extends WebSocketAdapter{

        @Override
        public void onWebsocketMessage(WebSocket conn, String message) {

        }

        @Override
        public void onWebsocketMessage(WebSocket conn, ByteBuffer blob) {

        }

        @Override
        public void onWebsocketOpen(WebSocket conn, Handshakedata d) {

        }

        @Override
        public void onWebsocketClose(WebSocket ws, int code, String reason, boolean remote) {

        }

        @Override
        public void onWebsocketClosing(WebSocket ws, int code, String reason, boolean remote) {

        }

        @Override
        public void onWebsocketCloseInitiated(WebSocket ws, int code, String reason) {

        }

        @Override
        public void onWebsocketError(WebSocket conn, Exception ex) {

        }

        @Override
        public void onWriteDemand(WebSocket conn) {

        }

        @Override
        public InetSocketAddress getLocalSocketAddress(WebSocket conn) {
            return null;
        }

        @Override
        public InetSocketAddress getRemoteSocketAddress(WebSocket conn) {
            return null;
        }
    }
}
