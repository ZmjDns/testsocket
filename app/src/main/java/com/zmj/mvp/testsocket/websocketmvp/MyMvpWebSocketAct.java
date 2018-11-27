package com.zmj.mvp.testsocket.websocketmvp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zmj.mvp.testsocket.R;

public class MyMvpWebSocketAct extends AppCompatActivity {

    private WebSocketThread mWebSocketThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mvp_web_socket);

        initWebThread();
    }

    private void  initWebThread(){
        mWebSocketThread = new WebSocketThread();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mWebSocketThread.start();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        Handler handler = mWebSocketThread.getHandler();
        handler.sendEmptyMessage(MessageType.QUIT);
        super.onDestroy();
    }
}
