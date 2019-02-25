package com.zmj.mvp.testsocket.viewpager.newverticalpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmj.mvp.testsocket.R;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/22
 * Description :
 */
public class FragmentTwo extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    private String myString ;

    private TextView tv_showData;

    private boolean isShowComments = false;
    private LinearLayout ll_operatorComm, ll_comment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myString = getArguments().getString("INFO");

        Log.d(TAG, "onCreate: 获取的数据：" + myString);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager_second,container,false);
        tv_showData = view.findViewById(R.id.tv_showData);
        tv_showData.setText(myString);
        ll_operatorComm = view.findViewById(R.id.ll_operatorComm);
        ll_comment = view.findViewById(R.id.ll_comment);

        ll_operatorComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrHideComm();
            }
        });
        return view;
    }

    public void showOrHideComm(){
        if (!isShowComments){
            ll_comment.setVisibility(View.VISIBLE);
            isShowComments = true;
        }else {
            ll_comment.setVisibility(View.GONE);
            isShowComments = false;
        }
    }
}
