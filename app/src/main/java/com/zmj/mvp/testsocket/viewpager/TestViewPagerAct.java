package com.zmj.mvp.testsocket.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.zmj.mvp.testsocket.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/1/27
 * Description :
 */
public class TestViewPagerAct extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private ViewPagerAdapter viewPagerAdapter;

    private List<String> mData;

    private ViewPager vp_viewpager1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_view_pager_act_1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
        initView();
    }

    private void initView(){
        vp_viewpager1 = findViewById(R.id.vp_viewpager1);

        viewPagerAdapter = new ViewPagerAdapter(this,mData);

        vp_viewpager1.setAdapter(viewPagerAdapter);
    }


    private void initData(){
        mData = new ArrayList<>();
        for (int i = 1; i < 5; i++){
            mData.add("第" + i+ "个View" );
        }
    }
}
