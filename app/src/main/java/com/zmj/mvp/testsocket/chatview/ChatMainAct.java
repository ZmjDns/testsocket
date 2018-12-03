package com.zmj.mvp.testsocket.chatview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.bean.WebSocketChatMessage;
import com.zmj.mvp.testsocket.utils.EncodeAndDecodeJson;
import com.zmj.mvp.testsocket.websocketmvp.ErrorResponse;
import com.zmj.mvp.testsocket.websocketmvp.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zmj
 * @date 2018/11/29
 */
public class ChatMainAct extends BaseAct {

    private final String TAG = this.getClass().getSimpleName();

    private List<WebSocketChatMessage> chatMessageList = new ArrayList<>();
    private ChatMessageAdapter chatMessageAdapter;
    private LinearLayoutManager mLayoutManager;

    private LinearLayout root_layout;
    private TextView tv_chatTitle;
    private RecyclerView recycle_chatMsg;
    private EditText ed_editMsg;
    private Button btn_sendMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        initView();

        Bundle bundle = getIntent().getExtras();
        String account = bundle.getString("account");
        String nickName = bundle.getString("nickName");
        tv_chatTitle.setText(nickName);
    }

    private void initView(){
        root_layout = findViewById(R.id.root_layout);
        tv_chatTitle = findViewById(R.id.tv_chatTitle);
        recycle_chatMsg = findViewById(R.id.recycle_chatMsg);
        ed_editMsg = findViewById(R.id.ed_editMsg);
        btn_sendMsg = findViewById(R.id.btn_sendMsg);

        btn_sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送消息
                sendMessage();
                ed_editMsg.setText("");
            }
        });
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
//        mLayoutManager.setStackFromEnd(true);   //设置信息从底部弹出
        recycle_chatMsg.setLayoutManager(mLayoutManager);
        chatMessageAdapter = new ChatMessageAdapter(this,chatMessageList,getUser());
        recycle_chatMsg.setAdapter(chatMessageAdapter);

        root_layout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Log.d(TAG, "onLayoutChange: bottom:" + bottom + "oldbottom：" + oldBottom);
                if (oldBottom != -1 && oldBottom > bottom){
                    recycle_chatMsg.requestLayout();
                    recycle_chatMsg.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recycle_chatMsg.scrollToPosition(chatMessageAdapter.getChatMessageList().size() + 1);
                        }
                    }, 100);
                }
            }
        });
        //监听弹出键盘遮挡数据问题
        /*ed_editMsg.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom){
                    recycle_chatMsg.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recycle_chatMsg.scrollToPosition(chatMessageList.size() - 1);
                        }
                    },100);
                }
            }
        });*/
    }

    private void sendMessage(){
        if (ed_editMsg.getText().toString() == null || ed_editMsg.getText().toString().trim().length() < 1){
            Toast.makeText(this,"请填写发送信息",Toast.LENGTH_SHORT).show();
        }else {
            WebSocketChatMessage chatMessage = new WebSocketChatMessage(System.currentTimeMillis(),getUser(),"all",ed_editMsg.getText().toString());
            sendText(EncodeAndDecodeJson.getSendMsg(chatMessage));
        }
    }

    @Override
    public void onMessageResponse(Response message) {
        synchronized (message){
            WebSocketChatMessage chatMessage = (WebSocketChatMessage) message.getResonseEntity();
            if (chatMessage.getContent().equals("您已登陆成功")){
                Toast.makeText(this,chatMessage.getContent(),Toast.LENGTH_SHORT).show();
            }else {
                //处理聊天内容
                chatMessageAdapter.addMsgAndNotify(chatMessage);
                recycle_chatMsg.scrollToPosition(chatMessageList.size() - 1);
            }
        }
    }
    @Override
    public void onSendMessageError(ErrorResponse error) {
        Toast.makeText(this,"接收消息出错" + error.getCause(),Toast.LENGTH_SHORT).show();
    }

    private String getUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("testuser",0);
        String user = sharedPreferences.getString("me","");
        return user;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
