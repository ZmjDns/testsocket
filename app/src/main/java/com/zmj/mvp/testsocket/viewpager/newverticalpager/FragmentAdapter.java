package com.zmj.mvp.testsocket.viewpager.newverticalpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

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

    private FragmentManager fragmentManager;
    private FragmentTransaction mCurTransaction;

    private List<String> stringList;

    public FragmentAdapter(FragmentManager fm,List<String> stringList) {
        super(fm);
        this.fragmentManager = fm;
        this.stringList = stringList;
    }

    @Override
    public Fragment getItem(int position) {

        Log.d(TAG, "getItem: 当前位置：" + position);

        Fragment fragment = new FragmentTwo();
        Bundle bundle = new Bundle();
        if (position >= stringList.size()){
            bundle.putString("INFO",stringList.get(position % stringList.size()));
        }else {
            bundle.putString("INFO",stringList.get(position));
        }
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);

    }
}
