package com.zmj.mvp.testsocket.fragmentLazyLoad;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zmj.mvp.testsocket.R;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/25
 * Description :
 */
public class EmptyFragment extends BaseLazyfragment {

    private final String TAG = this.getClass().getSimpleName();

    private String mTitle;

    public EmptyFragment() {
    }

    public static EmptyFragment newInstance(String title){
        EmptyFragment fragment = new EmptyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TITLE",title);
        fragment.setArguments(bundle);
        return fragment;
    }

    //一般在onCreate()方法中获取数据
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString("TITLE");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager_one,container,false);

        mIsPrepared = true;

        lazyLoad();

        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!mIsPrepared || !mIsVisible || mHasLoadOnce){
            return;
        }
        mHasLoadOnce = true;
        //UI 和业务逻辑
        Log.d(TAG, "lazyLoad: 当前的Fragment是：" + mTitle);
    }

    public String getTitle(){
        return mTitle;
    }
}
