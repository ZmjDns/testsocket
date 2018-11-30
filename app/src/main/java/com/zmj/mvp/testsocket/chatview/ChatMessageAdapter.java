package com.zmj.mvp.testsocket.chatview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.bean.WebSocketChatMessage;

import java.util.List;

/**
 * @author Zmj
 * @date 2018/11/29
 */
public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MsgViewHolder> {

    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;

    private List<WebSocketChatMessage> chatMessageList;

    private LayoutInflater  mLayoutInflater;

    private String userMe ;

    public ChatMessageAdapter(Context mContext, List<WebSocketChatMessage> chatMessageList,String userMe) {
        this.mContext = mContext;
        this.chatMessageList = chatMessageList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.userMe = userMe;
    }

    public List<WebSocketChatMessage> getChatMessageList(){
        return chatMessageList;
    }

    public void addMsgAndNotify(WebSocketChatMessage chatMessage){
        chatMessageList.add(chatMessage);

        notifyItemInserted(chatMessageList.size());
        Log.d(TAG, "addMsgAndNotify: 刷新数据：" + chatMessage.getContent());
    }

    @NonNull
    @Override
    public MsgViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.recycler_view_item,viewGroup,false);

        MsgViewHolder msgViewHolder = new MsgViewHolder(view);

        return msgViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MsgViewHolder msgViewHolder, int position) {
        WebSocketChatMessage chatMessage = chatMessageList.get(position);
        if (!chatMessage.getFromUser().equals(userMe)){//接收到的消息
            msgViewHolder.tv_nickName_receive.setText(chatMessage.getFromUser());
            msgViewHolder.tv_msg_receive.setText(chatMessage.getContent());
            msgViewHolder.rl_receive.setVisibility(View.VISIBLE);
            msgViewHolder.ll_send.setVisibility(View.GONE);
        }else {//发送的消息
            msgViewHolder.tv_nickName_send.setText(chatMessage.getFromUser());
            msgViewHolder.tv_msg_send.setText(chatMessage.getContent());
            msgViewHolder.rl_receive.setVisibility(View.GONE);
            msgViewHolder.ll_send.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }


    class MsgViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout rl_receive;
        LinearLayout ll_send;
        ImageView iv_avator_receive,iv_avator_send;
        TextView tv_nickName_receive,tv_msg_receive,tv_nickName_send,tv_msg_send;

        public MsgViewHolder(@NonNull View itemView) {
            super(itemView);
            rl_receive = itemView.findViewById(R.id.rl_receive);
            ll_send = itemView.findViewById(R.id.ll_send);

            iv_avator_receive = itemView.findViewById(R.id.iv_avator_receive);
            iv_avator_send = itemView.findViewById(R.id.iv_avator_send);

            tv_nickName_receive = itemView.findViewById(R.id.tv_nickName_receive);
            tv_msg_receive = itemView.findViewById(R.id.tv_msg_receive);
            tv_nickName_send = itemView.findViewById(R.id.tv_nickName_send);
            tv_msg_send = itemView.findViewById(R.id.tv_msg_send);
        }
    }
}
