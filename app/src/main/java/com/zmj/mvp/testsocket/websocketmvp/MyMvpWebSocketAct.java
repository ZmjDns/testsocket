package com.zmj.mvp.testsocket.websocketmvp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.bean.WebSocketChatMessage;
import com.zmj.mvp.testsocket.utils.EncodeAndDecodeJson;

public class MyMvpWebSocketAct extends AbsWebSocketAct implements View.OnClickListener {

    private Button btn_sendMessage;

    private TextView tv_msgHisttory;
    private EditText ed_editMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mvp_web_socket);

        btn_sendMessage = findViewById(R.id.btn_sendMessage);

        tv_msgHisttory = findViewById(R.id.tv_msgHisttory);
        ed_editMessage = findViewById(R.id.ed_editMessage);

        btn_sendMessage.setOnClickListener(this);
    }


    private void sendMsg(){

        WebSocketChatMessage message = new WebSocketChatMessage(System.currentTimeMillis(),"15822009415","all",ed_editMessage.getText().toString(),1);
        sendText(EncodeAndDecodeJson.getSendMsg(message));
        ed_editMessage.setText("");
        if (message.getToUser() != "all"){
            tv_msgHisttory.append(message.getFromUser() + "对" + message.getToUser() + "说：" + message.getContent() + "\n");
        }
    }

    @Override
    public void onMessageResponse(Response message) {
        Log.d(TAG, "onMessageResponse: " + message);
        WebSocketChatMessage chatMessage = (WebSocketChatMessage) message.getResonseEntity();
        tv_msgHisttory.append(chatMessage.getFromUser() + "对" + chatMessage.getToUser() + "说：" + chatMessage.getContent() + "\n");
    }

    @Override
    public void onSendMessageError(ErrorResponse error) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sendMessage:
                sendMsg();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this,WebSocketService.class);
        stopService(intent);
        super.onDestroy();
    }
}
