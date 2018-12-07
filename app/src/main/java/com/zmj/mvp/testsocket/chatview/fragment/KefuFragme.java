package com.zmj.mvp.testsocket.chatview.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.bean.User;
import com.zmj.mvp.testsocket.chatview.ChatMainAct;
import com.zmj.mvp.testsocket.chatview.KefuAdapter;
import com.zmj.mvp.testsocket.chatview.iviewipersent.IKefuView;
import com.zmj.mvp.testsocket.chatview.persenter.KefuPersenter;
import com.zmj.mvp.testsocket.widget.ClearEditText;
import com.zmj.mvp.testsocket.widget.SilderBar;

import java.util.List;

/**
 * @author Zmj
 * @date 2018/11/30
 */
public class KefuFragme extends Fragment implements IKefuView {

    private final String TAG = this.getClass().getSimpleName();
    private KefuPersenter kefuPersenter;

    private ClearEditText ce_cearch;
    private RecyclerView rl_kefu;
    private TextView title_layout_catalog,  //首字母
            tv_no_kefu_found;               //没有数据
    private TextView tv_dialog;     //显示所选字母
    private SilderBar sb_silderBar;//右侧字母

    private KefuAdapter kefuAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kefu,container,false);

        ce_cearch = view.findViewById(R.id.ce_cearch);
        rl_kefu = view.findViewById(R.id.rl_kefu);
        title_layout_catalog = view.findViewById(R.id.title_layout_catalog);
        tv_no_kefu_found = view.findViewById(R.id.tv_no_kefu_found);
        tv_dialog = view.findViewById(R.id.tv_dialog);
        sb_silderBar = view.findViewById(R.id.sb_silderBar);

        kefuPersenter = new KefuPersenter();
        kefuPersenter.attAchView(this);

        kefuPersenter.featchDat();
        return view;
    }

    @Override
    public void showLoading() {
        Toast.makeText(getActivity(),"正在加载",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showKefus(final List<User> users) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                rl_kefu.setLayoutManager(linearLayoutManager);
                kefuAdapter = new KefuAdapter(getActivity(),users);
                rl_kefu.setAdapter(kefuAdapter);
                initViewListener();
            }
        });
    }

    private void initViewListener(){
        kefuAdapter.setOnRecyclerItemClickListener(new KefuAdapter.OnRecyclerItemClickListener() {
            @Override
            public void getItem(User user) {
                Log.d(TAG, "getItem: " + user.getAccount());

                Intent chatIntent = new Intent(getActivity(), ChatMainAct.class);
                Bundle bundle = new Bundle();
                bundle.putString("account" ,user.getAccount());
                bundle.putString("nickName",user.getNickname());
                chatIntent.putExtras(bundle);
                startActivity(chatIntent);
            }
        });
    }

    @Override
    public void onDestroy() {
        kefuPersenter.dechView();
        super.onDestroy();
    }
}