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
import com.zmj.mvp.testsocket.bean.ChatMessage;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ObjInSocketAct extends AppCompatActivity implements Runnable,View.OnClickListener {

    private TextView tv_msg;
    private EditText ed_msg;
    private Button btn_send;
    private static final String HOST = "192.168.3.45";
    private static final int PORT = 8888;
    private Socket socket = null;

    //传输对象变量
    private OutputStream os;    //输出流
    private InputStream is;     //输入流
    private InputStreamReader isr;//读取输入流对象
    private BufferedReader br;  //



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
                            //构建获取的信息
                            ChatMessage chatMessage;
                            try {
                                //ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                                /*ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());*/
                                ObjectInputStream ois = new ObjectInputStream(is);
                                Object object = ois.readObject();
                                if (object != null){
                                    chatMessage =(ChatMessage)object;
                                    sendObjMsgToHandler(SOCKETMSG,chatMessage); //发送包含ChatMessage对象的信息到Handler
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
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
                String content = ed_msg.getText().toString();
                sendChatMessageToServer(content,"server");
                ed_msg.setText("");
                /*if (!socket.isOutputShutdown()){
                    //创建一条发送到服务器的信息
                    ChatMessage chatMessage = new ChatMessage("你好，测试Message对象","18302451883",null);
                    try{
                        //发送消息
                        ObjectOutputStream oos = new ObjectOutputStream(os);
                        oos.writeObject(chatMessage);
                        oos.flush();
                        ed_msg.setText("");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }*/
        }
    }

    private void connectServer(){
        try {
            socket = new Socket(HOST,PORT);

            //1.获取输出流，向服务器发送信息
            os = socket.getOutputStream();
            //获取服务端的消息
            is = socket.getInputStream();

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
    //创建包含ChatMessage对象的Message并通过Handler发送到UI线程
    private void sendObjMsgToHandler(int type,ChatMessage chatMessage){
        Message message = new Message();
        message.what = type;
        message.obj = chatMessage;
        socketPlusHandler.sendMessage(message);
    }



    class SocketPlusHandler extends MyHandler {

        public SocketPlusHandler(Context context) {
            super(context);
        }

        @Override
        protected void handleMessage(Context context, Message msg) {
            //获取Context进行操作UI
            switch (msg.what){
                case CRASHMSG:  //错误信息
                    Toast.makeText(context,"出错了" + msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                case SOCKETMSG: //socket传来的消息，更新UI
                    if (msg.obj instanceof Object){
                        ChatMessage chatMessage = (ChatMessage) msg.obj;

                        tv_msg.append(chatMessage.getContent() + chatMessage.getFromUser() + chatMessage.getToUser());
                    }else {
                        tv_msg.append("\n" + msg.obj + "\n");//
                    }
                    ed_msg.setText("");
                    break;
            }
        }
    }

    private void sendChatMessageToServer(String msg,String toUser){
        if (!socket.isOutputShutdown()){
            //创建一条发送到服务器的ChatMessage信息对象
            ChatMessage chatMessage = new ChatMessage(msg,"18302451883",toUser);
            try{
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(chatMessage);
                oos.flush();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
       //退出时发送退出指令
       sendChatMessageToServer("exit","server");
       try {
           is.close();
           os.close();
           socket.close();
       }catch (Exception e){
           e.printStackTrace();
       }
        super.onDestroy();
    }
}
