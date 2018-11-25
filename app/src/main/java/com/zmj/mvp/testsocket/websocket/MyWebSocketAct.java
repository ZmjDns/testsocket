package com.zmj.mvp.testsocket.websocket;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zmj.mvp.testsocket.R;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ServerHandshake;

public class MyWebSocketAct extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MyWebSocketAct";

    private TextView tv_wesMsg;
    private EditText ed_wesMsg;
    private Button btn_wesSend,btn_sendToOne;

    private MyWebSocketClient webSocketClient;
    private WebSocket.READYSTATE readystate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_web_socket);

        tv_wesMsg = findViewById(R.id.tv_wesMsg);
        ed_wesMsg = findViewById(R.id.ed_wesMsg);
        btn_wesSend = findViewById(R.id.btn_wesSend);
        btn_sendToOne = findViewById(R.id.btn_sendToOne);

        btn_wesSend.setOnClickListener(this);
        btn_sendToOne.setOnClickListener(this);

        webSocketClient = MyWebSocketClient.getInstance(this);

        initWebSockeClient();
    }

    private void initWebSockeClient(){
        readystate = webSocketClient.getReadyState();
        Log.d(TAG, "initWebSockeClient: websocket状态 " + readystate);
        //当WebSocket的状态是NOT_YET_CONNECTED时使用connect()方法进行初始化开启链接：
        if (readystate == WebSocket.READYSTATE.NOT_YET_CONNECTED){
            Log.d(TAG, "initWebSockeClient: ---初始化WebSocket客户端---");
            webSocketClient.connect();
        //当webSocket的状态为CLOSED状态时进行重新连接
        }else if (readystate == WebSocket.READYSTATE.CLOSED){
            webSocketClient.reconnect();
        }

        //连接成功时的监听
        webSocketClient.setOnOpenSuccessListener(new MyWebSocketClient.OpenSuccessListener() {
            @Override
            public void onOpenMsg(ServerHandshake serverHandshake) {
                //连接成功，发送登陆名称，给服务器记录
                webSocketClient.send("18302451883");
                getMessage();
            }
        });
    }



    private void getMessage(){
        readystate =webSocketClient.getReadyState();
        if (readystate != WebSocket.READYSTATE.NOT_YET_CONNECTED){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    webSocketClient.setOnGetMessgeListener(new MyWebSocketClient.GetMessgeListener() {
                        @Override
                        public void getMsg(final String msg) {
                            MyWebSocketAct.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_wesMsg.append("\n" + msg);
                                }
                            });
                        }
                    });
                }
            }).start();
        }else {
            initWebSockeClient();
        }
    }

    @Override
    public void onClick(View v) {
        String message = ed_wesMsg.getText().toString();
        switch (v.getId()){
            case R.id.btn_wesSend:
                if (!TextUtils.isEmpty(message)){
                    MyWebSocketClient.getInstance(this).send("all@" + message);
                    ed_wesMsg.setText("");
                }else{
                    Toast.makeText(this,"发送内容不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_sendToOne:
                if (!TextUtils.isEmpty(message)){
                    MyWebSocketClient.getInstance(this).send(getToUser() + "@" + message);
                    ed_wesMsg.setText("");
                }else{
                    Toast.makeText(this,"发送内容不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private String getToUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("user",0);
        return sharedPreferences.getString("小米","");
    }

    @Override
    protected void onDestroy() {
        //webSocketClient.onClose(88,"离线",false);
        webSocketClient.close();
        super.onDestroy();
    }
}
