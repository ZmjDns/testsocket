package com.zmj.mvp.testsocket.socketmvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.bean.MyUser;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MySocketAct extends AppCompatActivity implements Runnable,View.OnClickListener {
    private TextView tv_msg;
    private EditText ed_msg;
    private Button btn_send;
    private static final String HOST = "192.168.3.11";
    private static final int PORT = 8888;
    private Socket socket = null;

    ObjectOutputStream os = null;
    ObjectInputStream is = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_socket);

        tv_msg = findViewById(R.id.tv_msg);
        ed_msg = findViewById(R.id.ed_msg);
        btn_send = findViewById(R.id.btn_send);

        btn_send.setOnClickListener(this);

        new Thread(this).start();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void run() {
        useSocket();
    }

    private void useSocket(){
        try {
            socket = new Socket("192.168.3.11", 8888);

            os = new ObjectOutputStream(socket.getOutputStream());
            MyUser user = new MyUser("user_1" , "password_1");
            os.writeObject(user);
            os.flush();

            is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            Object obj = is.readObject();
            if (obj != null) {
                user = (MyUser)obj;
                System.out.println("user: " + user.getName() + "/" + user.getPassword());
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                is.close();
            } catch(Exception ex) {}
            try {
                os.close();
            } catch(Exception ex) {}
            try {
                socket.close();
            } catch(Exception ex) {}
        }
    }
}
