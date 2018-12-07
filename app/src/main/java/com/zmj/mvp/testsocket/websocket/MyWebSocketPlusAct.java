package com.zmj.mvp.testsocket.websocket;

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
import com.zmj.mvp.testsocket.bean.WebSocketChatMessage;
import com.zmj.mvp.testsocket.utils.EncodeAndDecodeJson;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class MyWebSocketPlusAct extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MyWebSocketPlusAct";

    private static final int PLUSMSG = 16;
    private static final int LOCAL_OBJ = 15;
    private static final int PLUSMSGERR = 14;

    private TextView tv_msgPlus;
    private EditText ed_msgPlus,ed_toUser;
    private Button btn_sendPlu,btn_siliao;

    private MyWebSocketClient webSocketClient;
    private WebSocketClient.READYSTATE readystate;

    private WebSocketPlusHandler webSocketPlusHandler;

    private static final String USER = "18302451883";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_web_socket_plus);

        tv_msgPlus = findViewById(R.id.tv_msgPlus);
        ed_msgPlus = findViewById(R.id.ed_msgPlus);
        ed_toUser = findViewById(R.id.ed_toUser);
        btn_sendPlu = findViewById(R.id.btn_sendPlu);
        btn_siliao = findViewById(R.id.btn_siliao);

        btn_sendPlu.setOnClickListener(this);
        btn_siliao.setOnClickListener(this);

        webSocketClient = MyWebSocketClient.getInstance(this);

        webSocketPlusHandler = new WebSocketPlusHandler(this);

        connectWebSocketServer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sendPlu:
                sendMessage();
                break;
            case R.id.btn_siliao:
//                personalChat();
                break;
            default:
                break;
        }
    }

    private void connectWebSocketServer(){
        readystate = webSocketClient.getReadyState();
        if (readystate == WebSocketClient.READYSTATE.NOT_YET_CONNECTED){
            webSocketClient.connect();
        }else if (readystate == WebSocketClient.READYSTATE.CLOSED){
            webSocketClient.reconnect();
        }

        //连接成功的监听
        webSocketClient.setOnOpenSuccessListener(new MyWebSocketClient.OpenSuccessListener() {
            @Override
            public void onOpenMsg(ServerHandshake serverHandshake) {
                WebSocketChatMessage message = new WebSocketChatMessage(System.currentTimeMillis(),USER,"server","login",1);
                String msg = EncodeAndDecodeJson.getSendMsg(message);

                webSocketClient.send(msg);
                //接收消息
                getMessage();
                //getMyMessage();
            }
        });
    }

    private void getMyMessage(){
        readystate = webSocketClient.getReadyState();
        if (readystate == WebSocketClient.READYSTATE.OPEN){
            while (true){
                webSocketClient.setOnGetMessgeListener(new MyWebSocketClient.GetMessgeListener() {
                    @Override
                    public void getMsg(String msg) {
                        Message chatMsg = new Message();
                        chatMsg.what = PLUSMSG;
                        chatMsg.obj = msg;
                        webSocketPlusHandler.sendMessage(chatMsg);
                    }
                });
            }
        }
    }

    private void getMessage(){
        readystate = webSocketClient.getReadyState();
        if (readystate == WebSocketClient.READYSTATE.OPEN){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    webSocketClient.setOnGetMessgeListener(new MyWebSocketClient.GetMessgeListener() {
                        @Override
                        public void getMsg(final String msg) {
                            Message chatMsg = new Message();
                            chatMsg.what = PLUSMSG;
                            chatMsg.obj = msg;
                            webSocketPlusHandler.sendMessage(chatMsg);
                            /*final WebSocketChatMessage webSocketChatMessage = EncodeAndDecodeJson.getWebSocketChatMessageObj(msg);
                            MyWebSocketPlusAct.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_msgPlus.append(webSocketChatMessage.getContent());
                                }
                            });*/
                        }
                    });
                }
            }).start();
        }
    }

    class WebSocketPlusHandler extends MyHandler{

        public WebSocketPlusHandler(Context context) {
            super(context);
        }

        @Override
        protected void handleMessage(Context context, Message msg) {
            switch (msg.what){
                case PLUSMSG:
                    WebSocketChatMessage message = EncodeAndDecodeJson.getWebSocketChatMessageObj(msg.obj.toString());
                    String ms = message.getContent();
                    String fromUser = message.getFromUser();
                    String toUser = message.getToUser();
                    tv_msgPlus.append(fromUser +  "对" + toUser + "说：" + ms + "\n");
//                    if (fromUser.equals(USER)){
//                        tv_msgPlus.append("你对" + toUser + "说：" + ms + "\n");
//                    }else {
//                        tv_msgPlus.append(fromUser + "你说：" + ms + "\n");
//                    }
                    break;
                case LOCAL_OBJ:
                    WebSocketChatMessage localmessage = (WebSocketChatMessage) msg.obj;
                    String mContent = localmessage.getContent();
                    String mFromUser = localmessage.getFromUser();
                    String mToUser = localmessage.getToUser();
                    tv_msgPlus.append(mFromUser +  "对" + mToUser + "说：" + mContent + "\n");
                    break;
                case PLUSMSGERR:
                    Toast.makeText(MyWebSocketPlusAct.this,"出错了" + msg.obj.toString(),Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    }

    //群聊
    private void sendMessage(){
        WebSocketChatMessage webSocketChatMessage = new WebSocketChatMessage(System.currentTimeMillis(),USER,ed_toUser.getText().toString().trim(),ed_msgPlus.getText().toString(),1);
        webSocketClient.send(EncodeAndDecodeJson.getSendMsg(webSocketChatMessage));
        Message message = new Message();
        message.what = LOCAL_OBJ;
        message.obj = webSocketChatMessage;
        webSocketPlusHandler.sendMessage(message);
        ed_msgPlus.setText("");
    }
    //私聊
    private void personalChat(){
        WebSocketChatMessage webSocketChatMessage = new WebSocketChatMessage(System.currentTimeMillis(),USER,"15822009415",ed_msgPlus.getText().toString(),1);
        webSocketClient.send(EncodeAndDecodeJson.getSendMsg(webSocketChatMessage));
        Message message = new Message();
        message.what = LOCAL_OBJ;
        message.obj = webSocketChatMessage;
        webSocketPlusHandler.sendMessage(message);
        ed_msgPlus.setText("");
    }

    @Override
    protected void onDestroy() {
        webSocketClient.clientClose();
        super.onDestroy();
    }
}
