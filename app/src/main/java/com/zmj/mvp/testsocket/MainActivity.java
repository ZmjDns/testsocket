package com.zmj.mvp.testsocket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zmj.mvp.testsocket.chatview.LoginAct;
import com.zmj.mvp.testsocket.realmdb.TestRealmDbAct;
import com.zmj.mvp.testsocket.socketmvp.MySocketAct;
import com.zmj.mvp.testsocket.socketmvp.ObjInSocketAct;
import com.zmj.mvp.testsocket.socketmvp.TcpSocketAct;
import com.zmj.mvp.testsocket.socketmvp.TcpSocketAct1;
import com.zmj.mvp.testsocket.socketmvp.TcpSocketPlusAct;
import com.zmj.mvp.testsocket.websocket.MyWebSocketAct;
import com.zmj.mvp.testsocket.websocket.MyWebSocketPlusAct;
import com.zmj.mvp.testsocket.websocketmvp.MyMvpWebSocketAct;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_socket, btn_socket1,btn_socketPlus,btn_objInSocket,btn_mySocket,
            btn_websocket,btn_websockrtPlus,btn_webSOcketMvp,btn_ceshi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_socket = findViewById(R.id.btn_socket1);
        btn_socket1 = findViewById(R.id.btn_socket);
        btn_socketPlus = findViewById(R.id.btn_socketPlus);
        btn_objInSocket = findViewById(R.id.btn_objInSocket);
        btn_mySocket = findViewById(R.id.btn_mySocket);
        btn_websocket = findViewById(R.id.btn_websocket);
        btn_websockrtPlus = findViewById(R.id.btn_websockrtPlus);
        btn_webSOcketMvp = findViewById(R.id.btn_webSOcketMvp);
        findViewById(R.id.btn_ceshi).setOnClickListener(this);
        findViewById(R.id.btn_realm).setOnClickListener(this);

        btn_socket.setOnClickListener(this);
        btn_socket1.setOnClickListener(this);
        btn_socketPlus.setOnClickListener(this);
        btn_objInSocket.setOnClickListener(this);
        btn_mySocket.setOnClickListener(this);
        btn_websocket.setOnClickListener(this);
        btn_websockrtPlus.setOnClickListener(this);
        btn_webSOcketMvp.setOnClickListener(this);

        //写入用户
        initUser();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_socket:
                startActivity(new Intent(this, TcpSocketAct.class));
                break;
            case R.id.btn_socket1:
                startActivity(new Intent(this, TcpSocketAct1.class));
                break;
            case R.id.btn_socketPlus:
                startActivity(new Intent(this, TcpSocketPlusAct.class));
              break;
            case R.id.btn_objInSocket:
                startActivity(new Intent(this, ObjInSocketAct.class));
                break;
            case R.id.btn_mySocket:
                startActivity(new Intent(this, MySocketAct.class));
                break;
            case R.id.btn_websocket:
                startActivity(new Intent(this, MyWebSocketAct.class));
                break;
            case R.id.btn_websockrtPlus:
                startActivity(new Intent(this, MyWebSocketPlusAct.class));
                break;
            case R.id.btn_webSOcketMvp:
                startActivity(new Intent(this, MyMvpWebSocketAct.class));
                break;
            case R.id.btn_ceshi:
                startActivity(new Intent(this, LoginAct.class));
                break;
            case R.id.btn_realm:
                startActivity(new Intent(this, TestRealmDbAct.class));
                break;
        }
    }

    private void initUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("user",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("三星","18302451883");
        editor.putString("小米","15822009415");
        //editor.putString("小米","15822009415");
        editor.commit();
    }
}
