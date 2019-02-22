package com.zmj.mvp.testsocket.viewpager.newverticalpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/22
 * Description :
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private final String TAG = this.getClass().getSimpleName();

    private List<String> stringList;

    public FragmentAdapter(FragmentManager fm,List<String> stringList) {
        super(fm);
        this.stringList = stringList;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem: 当前位置：" + position);
        if (position == 0){
            return new FragmentOne();
        }
        Fragment fragment = new FragmentTwo();
        Bundle bundle = new Bundle();
        bundle.putString("INFO",stringList.get(position));
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return 1000;
    }
}
