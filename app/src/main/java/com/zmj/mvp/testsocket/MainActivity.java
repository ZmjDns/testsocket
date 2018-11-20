package com.zmj.mvp.testsocket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zmj.mvp.testsocket.socketmvp.MySocketAct;
import com.zmj.mvp.testsocket.socketmvp.ObjInSocketAct;
import com.zmj.mvp.testsocket.socketmvp.TcpSocketAct;
import com.zmj.mvp.testsocket.socketmvp.TcpSocketAct1;
import com.zmj.mvp.testsocket.socketmvp.TcpSocketPlusAct;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_socket, btn_socket1,btn_socketPlus,btn_objInSocket,btn_mySocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_socket = findViewById(R.id.btn_socket1);
        btn_socket1 = findViewById(R.id.btn_socket);
        btn_socketPlus = findViewById(R.id.btn_socketPlus);
        btn_objInSocket = findViewById(R.id.btn_objInSocket);
        btn_mySocket = findViewById(R.id.btn_mySocket);

        btn_socket.setOnClickListener(this);
        btn_socket1.setOnClickListener(this);
        btn_socketPlus.setOnClickListener(this);
        btn_objInSocket.setOnClickListener(this);
        btn_mySocket.setOnClickListener(this);
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
        }
    }
}
