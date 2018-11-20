package com.zmj.mvp.testsocket.socketmvp;

import android.content.Context;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.async.MyHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 实现简单的Socket通讯，服务端CSServer
 */
public class TcpSocketPlusAct extends AppCompatActivity implements Runnable ,View.OnClickListener{

    private TextView tv_msg;
    private EditText ed_msg;
    private Button btn_send;
    private static final String HOST = "192.168.3.11";
    private static final int PORT = 8888;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    private final static int CRASHMSG = 333;
    private final static int SOCKETMSG = 777;
    //获取SocketPlusHandler 的内部弱引用
    final SocketPlusHandler socketPlusHandler = new SocketPlusHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp);

        tv_msg = findViewById(R.id.tv_msg);
        ed_msg = findViewById(R.id.ed_msg);
        btn_send = findViewById(R.id.btn_send);

        btn_send.setOnClickListener(this);

        new Thread(this).start();
    }

    @Override
    public void run() {
        connectServer();    //连接到服务器
        try{
            while (true){   //死循环守候获取数据
                if (!socket.isClosed()){    //socket没有关闭
                    if (socket.isConnected()){  //连接正常
                        if (!socket.isInputShutdown()){  //输入流没有断开
                            String getLine;
                            if ((getLine = in.readLine()) != null){
                                getLine += "\n";    //获取服务器发来的数据
                                sendMsgToHandler(SOCKETMSG,getLine);   //将数据发送到handler并在UI中更新
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            //创建Message并通过Handler发送到UI线程
            sendMsgToHandler(CRASHMSG,e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                String msgToServer = ed_msg.getText().toString();
                if (!socket.isOutputShutdown()){
                    out.println(msgToServer);
//                    ChatMessage chatMessage = new ChatMessage("你好，测试Message对象","ZMJ","SERVER");
//                    out.println(chatMessage);
                    ed_msg.setText("");
                }
        }
    }

    private void connectServer(){
        try {
            socket = new Socket(HOST,PORT);
            //获取输入的数据
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            //获取输出的数据
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")),true);

        }catch (Exception e){
            //创建Message并通过Handler发送到UI线程
            sendMsgToHandler(CRASHMSG,e.getMessage());
            e.printStackTrace();
        }
    }

    //创建Message并通过Handler发送到UI线程
    private void sendMsgToHandler(int type,String msg){
        Message message = new Message();
        message.what = type;
        message.obj = msg;
        socketPlusHandler.sendMessage(message);
    }



    class SocketPlusHandler extends MyHandler{

        public SocketPlusHandler(Context context) {
            super(context);
        }

        @Override
        protected void handleMessage(Context context, Message msg) {
            //获取Context进行操作UI
            switch (msg.what){
                case CRASHMSG:  //错误信息
                    Toast.makeText(context,"出错了",Toast.LENGTH_SHORT).show();
                    break;
                case SOCKETMSG: //socket传来的消息，更新UI
                    tv_msg.append((CharSequence) msg.obj);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        out.println("exit");
        if (!socket.isClosed()){
            try{
                socket.shutdownInput();
                socket.shutdownOutput();
                socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
