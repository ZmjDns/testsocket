package com.zmj.mvp.testsocket.chatview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.bean.User;
import com.zmj.mvp.testsocket.utils.CharacterParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zmj
 * @date 2018/11/30
 */
public class KefuAdapter extends RecyclerView.Adapter<KefuAdapter.KefuViewHolder> implements SectionIndexer {

    private Context context;
    private List<User> userList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;

    public KefuAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public OnRecyclerItemClickListener onRecyclerItemClickListener;

    public interface OnRecyclerItemClickListener {
        void getItem(User user);
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener){
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @NonNull
    @Override
    public KefuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.recycler_kefu_item,viewGroup,false);
        KefuViewHolder kefuViewHolder = new KefuViewHolder(view);
        return kefuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KefuViewHolder kefuViewHolder, final int position) {
        kefuViewHolder.tv_kefu.setText(userList.get(position).getNickname());
        kefuViewHolder.tv_kefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerItemClickListener.getItem(userList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class KefuViewHolder extends RecyclerView.ViewHolder{

        TextView tv_kefu;
        public KefuViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_kefu = itemView.findViewById(R.id.tv_kefu);
        }
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0;i < getItemCount(); i++){
            //可能会报错
            // 原来是：
            // setNameLetters(characterParser.getSelling(userObj.optString("nickname")))
            // kefuUserList.get(i).getNameLetters();
            String sortStr = CharacterParser.getInstance().getSelling(userList.get(i).getNickname());
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex){
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }
}
