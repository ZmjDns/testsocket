package com.zmj.mvp.testsocket.socketmvp;

import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zmj.mvp.testsocket.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpSocketAct1 extends AppCompatActivity implements Runnable,View.OnClickListener{

    private static final String TAG = "TcpSocket";
    private TextView tv_msg;
    private EditText ed_msg;
    private Button btn_send;

    private static final String HOST = "192.168.3.11";
    private static final int PORT = 8888;
    private Socket socket = null;
    private BufferedReader in = null;//获取输入信息
    private PrintWriter out = null;//输出信息

    //用handler来接收服务器发来的数据
    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_msg.append((CharSequence) msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_socket);

        tv_msg = findViewById(R.id.tv_msg);
        ed_msg = findViewById(R.id.ed_msg);
        btn_send = findViewById(R.id.btn_send);

        btn_send.setOnClickListener(this);

        //启动子线程，连接服务器
        new Thread(this).start();
    }

    @Override
    public void onClick(View v) {
        String msg = ed_msg.getText().toString();
        if (socket.isConnected() && msg.length() > 0){//socket没有关闭并且数据不为空
            if (!socket.isOutputShutdown()){        //socket的输出流没有关闭
                out.println(msg);   //发送消息
                ed_msg.setText("");
            }
        }

    }
    //连接服务器
    private void connection(){
        try{
            socket = new Socket(HOST,PORT);
            //接收消息
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
            in = new BufferedReader(inputStreamReader);
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            //发送消息
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
        }catch (Exception e){
            Log.d(TAG, "connection:连接服务器失败" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
       connection();
       try{
           while (true){
               if (!socket.isClosed()){ //socket没有关闭
                   if (socket.isConnected()){   //socket连接正常
                       if (!socket.isInputShutdown()){//输入流没有断开
                           String getLine;
                           if ((getLine = in.readLine()) != null){
                               getLine += "\n";
                               Message message = new Message();
                               message.obj = getLine;
                               mHandler.sendMessage(message);//Handler通知UI更新数据
                           }else {
                               Log.d(TAG, "run: 没有收到服务器数据");
                           }
                       }
                   }
               }
           }
       }catch (Exception e){
           Log.d(TAG, "run: socket出现异常" + e.getMessage());
           e.printStackTrace();
       }
    }

}
