package com.zmj.mvp.testsocket.viewpager.newverticalpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    String myString ;

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
        return view;
    }
}
